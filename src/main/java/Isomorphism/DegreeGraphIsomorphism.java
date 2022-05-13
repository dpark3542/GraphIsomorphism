package Isomorphism;

import GraphTheory.Structs.AdjacencyListGraph;
import GraphTheory.Structs.Graph;
import GroupTheory.Engines.GroupTheoryEngine;
import GroupTheory.Structs.*;
import GroupTheory.Utilities.GroupAction;
import GroupTheory.Utilities.GroupGenerator;

import java.util.*;

import static GroupTheory.Utilities.GroupAction.*;

public class DegreeGraphIsomorphism {
    private final GroupTheoryEngine engine;
    private int d, n;

    public DegreeGraphIsomorphism(GroupTheoryEngine engine) {
        this.engine = engine;
    }

    // TODO: refactor
    private static List<List<Integer>> copyList(List<List<Integer>> a) {
        List<List<Integer>> b = new ArrayList<>();
        for (int i = 0; i < a.size(); i++) {
            b.add(new ArrayList<>());
            for (int j : a.get(i)) {
                b.get(i).add(j);
            }
        }
        return b;
    }

    // TODO: refactor
    private static void copyGraph(List<List<Integer>> a, Graph g, int offset) {
        int n = g.getSize();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (g.isAdjacent(i, j)) {
                    a.get(i + offset).add(j + offset);
                    a.get(j + offset).add(i + offset);
                }
            }
        }
    }

    private Group getAutomorphismsFixingEdge(Graph graph, int e1, int e2) {
        // TODO: refactor to graph util
        boolean[] mkd = new boolean[graph.getSize()];
        Deque<Integer> q = new ArrayDeque<>();
        q.add(e1);
        q.add(e2);
        List<List<Integer>> bfs = new ArrayList<>();
        int level = 0;
        while (!q.isEmpty()) {
            int n = q.size();
            bfs.add(new ArrayList<>());
            for (int i = 0; i < n; i++) {
                int v = q.pollFirst();
                if (mkd[v]) {
                    continue;
                }
                mkd[v] = true;
                bfs.get(level).add(v);
                for (int w : graph.getNeighbors(v)) {
                    q.addLast(w);
                }
            }
            level++;
        }
        bfs.remove(bfs.size() - 1);

        // TODO: make graphs 1-indexed!
        Group group = new Group(new Permutation(new Cycle(e1 + 1, e2 + 1)));

        for (level = 1; level < bfs.size(); level++) {
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
            for (int u : bfs.get(level)) {
                for (int v : bfs.get(level)) {
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
            for (int t = 1; t <= d; t++) {
                // TODO: use binomial(bfs.get(level - 1).size(), t) memory instead
                List<Integer> l = new ArrayList<>(binomial(2 * n + 2, t));
                for (Tuple tuple : new ImplicitDomain(2 * n + 2, t)) {
                    int offset = 0;
                    if (t == 2 && edges.contains(tuple)) {
                        offset = 2 * n + 2;
                    }

                    if (f.containsKey(tuple)) {
                        l.add(f.get(tuple).size() + 1 + offset);
                    }
                    else {
                        l.add(1 + offset);
                    }
                }

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
        level = bfs.size() - 1;
        for (int u : bfs.get(level)) {
            for (int v : bfs.get(level)) {
                if (u < v && graph.isAdjacent(u, v)) {
                    // TODO: make graphs 1-indexed!
                    edges.add(new Tuple(u + 1, v + 1));
                }
            }
        }

        for (int t = 1; t <= d; t++) {
            // TODO: use binomial(bfs.get(level - 1).size(), t) memory instead
            List<Integer> l = new ArrayList<>(binomial(2 * n + 2, t));
            for (Tuple tuple : new ImplicitDomain(2 * n + 2, t)) {
                int offset = 0;
                if (t == 2 && edges.contains(tuple)) {
                    offset = 2 * n + 2;
                }

                l.add(1 + offset);
            }

            FormalString s = new FormalString(l);
            StringIsomorphism si = new StringIsomorphism(engine);
            Coset coset = si.getIsomorphismCoset(s, s, GroupAction.inducedAction(engine, group, 2 * n + 2, t));

            if (coset == null || !coset.element().isIdentity()) {
                throw new RuntimeException();
            }

            group = pullbackAction(engine, coset.group(), 2 * n + 2, t);
        }

        return group;
    }

    public boolean isDegreeIsomorphic(Graph g, Graph h) {
        n = g.getSize();

        d = 0;
        for (int i = 0; i < n; i++) {
            d = Math.max(d, g.getNeighbors(i).size());
            d = Math.max(d, h.getNeighbors(i).size());
        }

        for (int u = 0; u < n; u++) {
            for (int v : g.getNeighbors(u)) {
                if (u > v) {
                    continue;
                }

                List<List<Integer>> a = new ArrayList<>();
                for (int i = 0; i < 2 * n + 2; i++) {
                    a.add(new ArrayList<>());
                }
                copyGraph(a, g, 0);
                // TODO: addEdge and removeEdge utility functions
                a.get(u).remove((Integer) v);
                a.get(v).remove((Integer) u);
                a.get(u).add(2 * n);
                a.get(2 * n).add(u);
                a.get(v).add(2 * n);
                a.get(2 * n).add(v);
                a.get(2 * n).add(2 * n + 1);
                a.get(2 * n + 1).add(2 * n);

                for (int w = 0; w < n; w++) {
                    for (int x : h.getNeighbors(w)) {
                        if (w > x) {
                            continue;
                        }
                        // find isomorphism mapping {u, v} to {w, x}

                        List<List<Integer>> b = copyList(a);
                        copyGraph(b, h, n);
                        b.get(w + n).remove((Integer) (x + n));
                        b.get(x + n).remove((Integer) (w + n));
                        b.get(w + n).add(2 * n + 1);
                        b.get(2 * n + 1).add(w + n);
                        b.get(x + n).add(2 * n + 1);
                        b.get(2 * n + 1).add(x + n);

                        Group group = getAutomorphismsFixingEdge(new AdjacencyListGraph(b), 2 * n, 2 * n + 1);

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
                    }
                }
            }
        }

        return false;
    }
}
