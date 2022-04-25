package GroupTheory.Structs;

import java.util.*;

/**
 * A tuple is a set of integers.
 * Implemented with a sorted array since the tuple is unlikely to be large enough to justify using a HashSet.
 */
public class Tuple {
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
        this.tuple = tuple;
        Arrays.sort(this.tuple);
        check();
    }

    private void check() {
        // TODO: check for duplicates since we are not using HashSet anymore
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
//        else if (n == 1 && (obj instanceof Integer)) {
//            return tuple.contains(obj);
//        }
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
    public int hashCode() {
        return Arrays.hashCode(tuple);
    }
}
