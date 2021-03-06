package GroupTheory.Structs;

import java.util.Iterator;

/**
 * Represents family of all k-subsets of [n].
 */
public record ImplicitDomain(int n, int k) implements Domain {
    public ImplicitDomain(int n, int k) {
        this.n = n;
        this.k = k;
        if (1 > k || k > n) {
            throw new RuntimeException();
        }
    }

    @Override
    public Iterator<Tuple> iterator() {
        return new Iterator<>() {
            private final int n = ImplicitDomain.this.n, k = ImplicitDomain.this.k;
            private int[] a = null;

            @Override
            public boolean hasNext() {
                return a == null || !(a[0] == n && a[k - 1] == n - k + 1);
            }

            @Override
            public Tuple next() {
                if (a == null) {
                    a = new int[k];
                    for (int i = 0; i < k; i++) {
                        a[i] = k - i;
                    }
                }
                else {
                    for (int i = 0; i < k; i++) {
                        if (a[i] + i + 1 <= n) {
                            a[i]++;
                            for (int j = 0; j < i; j++) {
                                a[j] = a[i] + i - j;
                            }
                            break;
                        }
                    }
                }
                return new Tuple(a);
            }
        };
    }

    @Override
    public boolean inDomain(Tuple tuple) {
        return tuple.size() == k && tuple.get(k - 1) <= n;
    }

    @Override
    public int size() {
        int res = 1;
        for (int i = n - k + 1; i <= n; i++) {
            res *= i;
        }
        for (int i = 2; i <= k; i++) {
            res /= i;
        }
        return res;
    }

    @Override
    public String toString() {
        return "Combinations([1.." + n + "], " + k + ')';
    }
}
