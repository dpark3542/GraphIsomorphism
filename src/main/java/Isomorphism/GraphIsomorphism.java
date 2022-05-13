package Isomorphism;

import GraphTheory.Engines.Nauty;
import GraphTheory.Structs.Graph;
import GroupTheory.Structs.Cycle;
import GroupTheory.Structs.FormalString;
import GroupTheory.Structs.Group;
import GroupTheory.Structs.Permutation;

import java.util.ArrayList;
import java.util.List;

public final class GraphIsomorphism {
    private static FormalString graphToString(Graph graph) {
        int n = graph.getSize();
        List<Integer> a = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (graph.isAdjacent(i, j)) {
                    a.add(2);
                }
                else {
                    a.add(1);
                }
            }
        }
        return new FormalString(a);
    }

    /**
     * Natural map from \binom{[n]}{2} to [\binom{n}{2}].
     *
     * @param i first element
     * @param j second element
     * @return image
     */
    private static int convert(int i, int j, int n) {
        if (i > j) {
            return convert(j, i, n);
        }
        return n * (i - 1) - i * (i + 1) / 2 + j;
    }

    /**
     * S_n naturally acts on the 2-element subsets of [n].
     * This function returns the associated permutation representation of that action.
     * Hence, we return a subgroup of S_{\binom{[n]}{2}} isomorphic to S_n where we identify S_{\binom{[n]}{2}} with S_{\binom{n}{2}} instead.
     * (Note there are multiple subgroups isomorphic to S_n. We are returning the one associated by this action.)
     *
     * @param n parameter
     * @return induced action of S_n on 2-element subsets of [n]
     */
    public static Group inducedAction(int n) {
        if (n == 2) {
            return new Group();
        }

        List<Cycle> a = new ArrayList<>();
        for (int i = 3; i <= n; i++) {
            a.add(new Cycle(convert(1, i, n), convert(2, i, n)));
        }

        List<Cycle> b = new ArrayList<>();
        for (int i = 1; i <= (n + 1) / 2 - 1; i++) {
            List<Integer> cycle = new ArrayList<>();
            for (int j = 1; j <= n; j++) {
                int k = (j + i) % n;
                if (k == 0) {
                    k = n;
                }
                cycle.add(convert(j, k, n));
            }
            b.add(new Cycle(cycle));
        }
        if (n % 2 == 0) {
            List<Integer> cycle = new ArrayList<>();
            for (int j = 1; j <= n / 2; j++) {
                cycle.add(convert(j, j + n / 2, n));
            }
            b.add(new Cycle(cycle));
        }

        return new Group(new Permutation(a), new Permutation(b));
    }

    private static boolean isDegreeIsomorphic(Graph g, Graph h) {
        // TODO:
        return false;
    }

    public static boolean isIsomorphic(Graph g, Graph h, Method method) {
        int n = g.getSize();
        if (h.getSize() != n) {
            return false;
        }

        if (n <= 1) {
            return true;
        }

        if (method == Method.Naive) {
            return StringIsomorphism.isIsomorphic(graphToString(g), graphToString(h), inducedAction(n));
        }
        else if (method == Method.Nauty) {
            Nauty nauty = new Nauty();
            return nauty.isIsomorphic(g, h);
        }
        else if (method == Method.Degree) {
            return isDegreeIsomorphic(g, h);
        }
        else {
            throw new UnsupportedOperationException();
        }
    }
}
