package GroupTheory.Utilities;

import GroupTheory.Engines.GroupTheoryEngine;
import GroupTheory.Structs.Group;
import GroupTheory.Structs.ImplicitDomain;
import GroupTheory.Structs.Permutation;
import GroupTheory.Structs.Tuple;

import java.util.ArrayList;
import java.util.List;

public final class GroupAction {
    // https://cp-algorithms.com/combinatorics/binomial-coefficients.html#improved-implementation
    public static int binomial(int n, int k) {
        if (k > n - k) {
            k = n - k;
        }

        double res = 1;
        for (int i = 1; i <= k; i++) {
            res = res * (n - k + i) / i;
        }

        return (int) (res + 0.01);
    }

    /**
     * Natural (lexicographic) map from \binom{[n]}{k} to [\binom{n}{k}].
     *
     * @param tuple k-tuple
     * @param n domain size
     * @param k tuple size
     * @return image
     */
    public static int tupleToInt(Tuple tuple, int n, int k) {
        int ans = 1;

        for (int i = 0; i < k; i++) {
            int offset = 0;
            if (i > 0) {
                offset = tuple.get(i - 1);
            }
            for (int j = 1; j < tuple.get(i) - offset; j++) {
                ans += binomial(n - j - offset, k - i - 1);
            }
        }

        return ans;
    }

    /**
     * Natural (lexicographic) map from [\binom{n}{k}] to \binom{[n]}{k}.
     *
     * @param x integer
     * @param n domain size
     * @param k tuple size
     * @return image
     */
    public static Tuple intToTuple(int x, int n, int k) {
        List<Integer> tuple = new ArrayList<>(k);

        for (int i = 0; i < k; i++) {
            int prev = 0, cur = 0, j = 0, offset = 0;
            if (i > 0) {
                offset = tuple.get(tuple.size() - 1);
            }
            while (x > cur) {
                j++;
                prev = cur;
                cur += binomial(n - j - offset, k - i - 1);
            }

            tuple.add(j + offset);
            x -= prev;
        }

        return new Tuple(tuple);
    }

    /**
     * A subgroup G of S_n naturally acts on S_{\binom{[n]}{k}}, the k-element subsets of [n].
     * This function returns the associated permutation representation of that action.
     * S_{\binom{[n]}{k}} is identified with S_{\binom{n}{k}} lexicographically.
     *
     * Important: the action is faithful except when k = n.
     *
     * @param engine group theory engine
     * @param g group
     * @param n domain size
     * @param k subset size
     * @return induced action of G on k-element subsets of [n]
     */
    public static Group inducedAction(GroupTheoryEngine engine, Group g, int n, int k) {
        if (1 > k || k > n) {
            throw new IllegalArgumentException();
        }
        if (k == 1) {
            return g;
        }
        if (k == n) {
            throw new IllegalArgumentException();
        }

        List<Permutation> generators = new ArrayList<>();

        for (Permutation generator : g) {
            List<Tuple> image = engine.act(new ImplicitDomain(n, k), generator);
            List<Integer> a = new ArrayList<>();
            for (Tuple tuple : image) {
                a.add(tupleToInt(tuple, n, k));
            }
            generators.add(engine.listToPermutation(a));
        }

        return new Group(generators);
    }

    private static int modOffset(int x, int n) {
        int y = x % n;
        if (y == 0) {
            y = n;
        }
        return y;
    }

    public static Group pullbackAction(GroupTheoryEngine engine, Group g, int n, int k) {
        if (1 > k || k > n) {
            throw new IllegalArgumentException();
        }
        if (k == 1) {
            return g;
        }
        if (k == n) {
            throw new IllegalArgumentException();
        }

        List<Permutation> generators = new ArrayList<>();

        for (Permutation generator : g) {
            List<Integer> p = new ArrayList<>();
            for (int i = 1; i <= n; i++) {
                List<Integer> a = new ArrayList<>(), q = new ArrayList<>();
                for (int j = 1; j <= k - 1; j++) {
                    a.add(modOffset(i + j, n));
                }
                a.add(i);
                q.add(tupleToInt(new Tuple(a), n, k));
                a.set(k - 1, modOffset(i + k, n));
                q.add(tupleToInt(new Tuple(a), n, k));
                List<Integer> im = engine.act(q, generator);
                Tuple t = intToTuple(im.get(0), n, k), u = intToTuple(im.get(1), n, k);
                List<Integer> ul = new ArrayList<>();
                u.iterator().forEachRemaining(ul::add);
                for (int x : t) {
                    if (!ul.contains(x)) {
                        p.add(x);
                        break;
                    }
                }
            }
            generators.add(engine.listToPermutation(p));
        }

        return new Group(generators);
    }
}
