package GroupTheory.Structs;

import java.util.*;

/**
 * A tuple is a set of positive integers.
 * Implemented with a sorted array since the tuple is unlikely to be large enough to justify using a HashSet.
 */
public class Tuple implements Iterable<Integer>, Comparable<Tuple> {
    private final int n;
    private final int[] tuple;

    public Tuple(Collection<Integer> tuple) {
        n = tuple.size();
        this.tuple = new int[n];
        int i = 0;
        for (int x : tuple) {
            this.tuple[i] = x;
            i++;
        }
        Arrays.sort(this.tuple);
        check();
    }

    public Tuple(int... tuple) {
        n = tuple.length;
        this.tuple = Arrays.copyOf(tuple, n);
        Arrays.sort(this.tuple);
        check();
    }

    private void check() {
        if (tuple[0] < 1) {
            throw new RuntimeException();
        }
        for (int i = 0; i < n - 1; i++) {
            if (tuple[i] == tuple[i + 1]) {
                throw new RuntimeException();
            }
        }
    }

    public int size() {
        return n;
    }

    public int getMax() {
        return tuple[n - 1];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; i < n - 1; i++) {
            sb.append(tuple[i]);
            sb.append(',');
        }
        sb.append(tuple[n - 1]);
        sb.append(']');
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        else if (!(obj instanceof Tuple t)) {
            return false;
        }
        else {
            if (n != t.n) {
                return false;
            }
            for (int i = 0; i < n; i++) {
                if (tuple[i] != t.tuple[i]) {
                    return false;
                }
            }
            return true;
        }
    }

    @Override
    public int compareTo(Tuple t) {
        if (n < t.n) {
            return -1;
        }
        else if (n > t.n) {
            return 1;
        }
        else {
            for (int i = 0; i < n; i++) {
                if (tuple[i] < t.tuple[i]) {
                    return -1;
                }
                else if (tuple[i] > t.tuple[i]) {
                    return 1;
                }
            }
        }
        return 0;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<>() {
            private int i = 0;
            private final int n = Tuple.this.n;
            private final int[] tuple = Tuple.this.tuple;

            @Override
            public boolean hasNext() {
                return i < n;
            }

            @Override
            public Integer next() {
                int v = tuple[i];
                i++;
                return v;
            }
        };
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(tuple);
    }
}
