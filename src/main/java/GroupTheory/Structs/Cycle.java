package GroupTheory.Structs;

import java.util.*;

public class Cycle implements Iterable<Integer> {
    private final int n;
    private final int[] cycle;

    public Cycle(List<Integer> cycle) {
        n = cycle.size();
        this.cycle = new int[n];
        int i = 0;
        for (int x : cycle) {
            this.cycle[i] = x;
            i++;
        }
        check();
    }

    public Cycle(int... cycle) {
        n = cycle.length;
        this.cycle = Arrays.copyOf(cycle, n);
        check();
    }

    private void check() {
        if (n <= 1) {
            throw new RuntimeException();
        }
        Set<Integer> s = new HashSet<>();
        for (int x : cycle) {
            if (s.contains(x)) {
                throw new RuntimeException();
            }
            s.add(x);
        }
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<>() {
            private int i = 0;
            private final int n = Cycle.this.n;
            private final int[] cycle = Cycle.this.cycle;

            @Override
            public boolean hasNext() {
                return i < n;
            }

            @Override
            public Integer next() {
                int x = cycle[i];
                i++;
                return x;
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('(');
        for (int i = 0; i < n - 1; i++) {
            sb.append(cycle[i]);
            sb.append(',');
        }
        sb.append(cycle[n - 1]);
        sb.append(')');
        return sb.toString();
    }
}
