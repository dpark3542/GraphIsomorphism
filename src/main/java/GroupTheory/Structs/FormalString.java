package GroupTheory.Structs;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class FormalString implements Iterable<Integer> {
    private final int n;
    private final int[] map;

    public FormalString(List<Integer> map) {
        n = map.size();
        this.map = new int[n];
        int i = 0;
        for (int x : map) {
            this.map[i] = x;
            i++;
        }
        check();
    }

    public FormalString(int... map) {
        n = map.length;
        this.map = Arrays.copyOf(map, n);
        check();
    }

    private void check() {
        for (int i = 0; i < n; i++) {
            if (map[i] < 1) {
                throw new RuntimeException();
            }
        }
    }

    public int size() {
        return n;
    }

    public int get(int i) {
        return map[i - 1];
    }

    @Override
    public String toString() {
        if (n == 0) {
            return "[]";
        }
        else {
            StringBuilder sb = new StringBuilder();
            sb.append('[');
            for (int i = 0 ; i < n - 1; i++) {
                sb.append(map[i]);
                sb.append(',');
            }
            sb.append(map[n - 1]);
            sb.append(']');
            return sb.toString();
        }
    }

    @Override
    public Iterator<Integer> iterator() {
        return Arrays.stream(map).iterator();
    }
}
