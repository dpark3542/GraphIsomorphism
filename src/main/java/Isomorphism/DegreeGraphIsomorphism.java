package Isomorphism;

import GraphTheory.Structs.AdjacencyListGraph;
import GraphTheory.Structs.Graph;
import GroupTheory.Engines.GroupTheoryEngine;
import GroupTheory.Structs.*;
import GroupTheory.Utilities.GroupAction;
import GroupTheory.Utilities.GroupGenerator;

import java.util.*;

import static GraphTheory.Utilities.GraphConnectivity.breadthFirstSearch;
import static GraphTheory.Utilities.GraphConnectivity.isConnected;
import static GroupTheory.Utilities.GroupAction.binomial;
import static GroupTheory.Utilities.GroupAction.pullbackAction;

public class DegreeGraphIsomorphism {
    private final GroupTheoryEngine engine;
    private int d, n;

    public DegreeGraphIsomorphism(GroupTheoryEngine engine) {
        this.engine = engine;
    }

    private static void addEdge(List<List<Integer>> a, int u, int v) {
        a.get(u).add(v);
        a.get(v).add(u);
    }

    private static void removeEdge(List<List<Integer>> a, int u, int v) {
        a.get(u).remove((Integer) v);
        a.get(v).remove((Integer) u);
    }

    private static void copyGraph(List<List<Integer>> a, Graph g, int offset) {
        int n = g.getNumVertices();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (g.isAdjacent(i, j)) {
                    a.get(i + offset).add(j + offset);
                    a.get(j + offset).add(i + offset);
                }
            }
        }
    }

    private Group getAutomorphismsStabilizingEdge(Graph graph, int e1, int e2) {
        List<List<Integer>> bfs = breadthFirstSearch(graph, e1, e2);

        // TODO: make graphs 1-indexed!
        Group group = new Group(new Permutation(new Cycle(e1 + 1, e2 + 1)));

        for (int level = 1; level < bfs.size(); level++) {
            List<Permutation> generators = new ArrayList<>();

            Map<Tuple, List<Integer>> f = new HashMap<>(); // inverse of father map
            for (int u : bfs.get(level)) {
                Set<Integer> set = new HashSet<>(graph.getNeighbors(u));
                set.retainAll(bfs.get(level - 1));
                // TODO: make graphs 1-indexed!
                List<Integer> tmp = new ArrayList<>();
                for (int x : set) {
                    tmp.add(x + 1);
                }
                Tuple key = new Tuple(tmp);
                if (!f.containsKey(key)) {
                    f.put(key, new ArrayList<>());
                }
                // TODO: make graphs 1-indexed!
                f.get(key).add(u + 1);
            }

            Set<Tuple> edges = new HashSet<>();
            for (int u : bfs.get(level - 1)) {
                for (int v : bfs.get(level - 1)) {
                    if (u < v && graph.isAdjacent(u, v)) {
                        // TODO: make graphs 1-indexed!
                        edges.add(new Tuple(u + 1, v + 1));
                    }
                }
            }

            // kernel of pi
            for (List<Integer> preimage : f.values()) {
                for (Permutation permutation : GroupGenerator.symmetricGroup(preimage)) {
                    generators.add(permutation);
                }
            }

            // image of pi
            Group image = group;
            for (int t = 1; t <= d && t <= bfs.get(level - 1).size(); t++) {
                // TODO: use binomial(bfs.get(level - 1).size(), t) memory instead
                boolean flag = false;
                int last = 0;
                List<Integer> l = new ArrayList<>(binomial(2 * n + 2, t));
                for (Tuple tuple : new ImplicitDomain(2 * n + 2, t)) {
                    int offset = 0;
                    if (t == 2 && edges.contains(tuple)) {
                        offset = 2 * n + 2;
                    }

                    int x;
                    if (f.containsKey(tuple)) {
                        x = f.get(tuple).size() + 1 + offset;
                    }
                    else {
                        x = 1 + offset;
                    }
                    l.add(x);
                    if (last == 0) {
                        last = x;
                    }
                    else if (x != last) {
                        flag = true;
                    }
                }

                // TODO: add optimization parameter
                if (flag) {
                    FormalString s = new FormalString(l);
                    StringIsomorphism si = new StringIsomorphism(engine);
                    Coset coset = si.getIsomorphismCoset(s, s, GroupAction.inducedAction(engine, image, 2 * n + 2, t));

                    // Image cannot be empty.
                    // By (4.2), iso(s, t) is a coset of iso(s, s). We are calling it when s = t, so we should get a group.
                    // Image of a homomorphism is a group anyway.
                    if (coset == null || !coset.element().isIdentity()) {
                        throw new RuntimeException();
                    }

                    // pull back image which acts on S_{\binom{V_i}{t}} to act on V_i
                    image = pullbackAction(engine, coset.group(), 2 * n + 2, t);
                }
            }

            // pull back image which acts on V_i to act on V_{i+1}
            for (Permutation generator : image) {
                List<Cycle> pullback = new ArrayList<>();
                generator.iterator().forEachRemaining(pullback::add);

                Set<Tuple> s = new HashSet<>();
                for (Map.Entry<Tuple, List<Integer>> entry : f.entrySet()) {
                    Tuple key = entry.getKey();
                    List<Integer> value = entry.getValue();

                    if (s.contains(key)) {
                        continue;
                    }

                    List<Integer> list = new ArrayList<>();
                    key.iterator().forEachRemaining(list::add);
                    Tuple key2 = new Tuple(engine.act(list, generator));
                    if (key.equals(key2)) {
                        continue;
                    }
                    // permuted set of fathers should still be a valid set of fathers
                    if (!f.containsKey(key2)) {
                        throw new RuntimeException();
                    }
                    List<Integer> value2 = f.get(key2);
                    int m = value.size();
                    if (value2.size() != m) {
                        throw new RuntimeException();
                    }

                    s.add(key);
                    s.add(key2);

                    for (int i = 0; i < m; i++) {
                        pullback.add(new Cycle(value.get(i), value2.get(i)));
                    }
                }
                generators.add(new Permutation(pullback));
            }

            group = new Group(generators);
        }

        // stabilize edges in last bfs layer
        Set<Tuple> edges = new HashSet<>();
        int level = bfs.size() - 1;
        for (int u : bfs.get(level)) {
            for (int v : bfs.get(level)) {
                if (u < v && graph.isAdjacent(u, v)) {
                    // TODO: make graphs 1-indexed!
                    edges.add(new Tuple(u + 1, v + 1));
                }
            }
        }

        boolean flag = false;
        List<Integer> l = new ArrayList<>(binomial(2 * n + 2, 2));
        for (Tuple tuple : new ImplicitDomain(2 * n + 2, 2)) {
            if (edges.contains(tuple)) {
                l.add(2);
                flag = true;
            }
            else {
                l.add(1);
            }
        }

        // TODO: add optimization parameter
        if (flag) {
            FormalString s = new FormalString(l);
            StringIsomorphism si = new StringIsomorphism(engine);
            Coset coset = si.getIsomorphismCoset(s, s, GroupAction.inducedAction(engine, group, 2 * n + 2, 2));

            if (coset == null || !coset.element().isIdentity()) {
                throw new RuntimeException();
            }

            group = pullbackAction(engine, coset.group(), 2 * n + 2, 2);
        }

        return group;
    }

    public boolean isDegreeIsomorphic(Graph g, Graph h) {
        n = g.getNumVertices();
        d = 0;
        for (int i = 0; i < n; i++) {
            d = Math.max(d, g.getNeighbors(i).size());
            d = Math.max(d, h.getNeighbors(i).size());
        }

        // TODO: add performance flag for edge check or add this logic to GraphIsomorphism
        // precondition checks
        if (g.getNumEdges() == 0) {
            return h.getNumEdges() == 0;
        }
        if (!isConnected(g) || !isConnected(h)) {
            throw new RuntimeException();
        }

        int u = 0, v = 0;
        for (int i = 0; i < n; i++) {
            if (!g.getNeighbors(i).isEmpty()) {
                u = i;
                v = g.getNeighbors(i).get(0);
                break;
            }
        }

        List<List<Integer>> a = new ArrayList<>();
        for (int i = 0; i < 2 * n + 2; i++) {
            a.add(new ArrayList<>());
        }
        copyGraph(a, g, 0);
        copyGraph(a, h, n);
        removeEdge(a, u, v);
        addEdge(a, u, 2 * n);
        addEdge(a, v, 2 * n);
        addEdge(a, 2 * n, 2 * n + 1);

        for (int w = 0; w < n; w++) {
            for (int x : h.getNeighbors(w)) {
                if (w > x) {
                    continue;
                }
                // find isomorphism mapping {u, v} to {w, x}
                removeEdge(a, w + n, x + n);
                addEdge(a, w + n, 2 * n + 1);
                addEdge(a, x + n, 2 * n + 1);

                Group group = getAutomorphismsStabilizingEdge(new AdjacencyListGraph(a), 2 * n, 2 * n + 1);

                for (Permutation generator : group) {
                    for (Cycle cycle : generator) {
                        boolean r = false, s = false;
                        for (int t : cycle) {
                            if (t <= n) {
                                r = true;
                            }
                            else if (t <= 2 * n) {
                                s = true;
                            }
                        }
                        if (r && s) {
                            return true;
                        }
                    }
                }

                addEdge(a, w + n, x + n);
                removeEdge(a, w + n, 2 * n + 1);
                removeEdge(a, x + n, 2 * n + 1);
            }
        }

        return false;
    }
}
