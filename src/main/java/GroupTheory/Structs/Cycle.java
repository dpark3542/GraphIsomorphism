package GroupTheory.Structs;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Cycle {
    private final int n;
    private final int[] cycle;

    public Cycle(List<Integer> cycle) {
        n = cycle.size();
        this.cycle = new int[n];
        for (int i = 0; i < n; i++) {
            this.cycle[i] = cycle.get(i);
        }
        check();
    }

    public Cycle(int... cycle) {
        n = cycle.length;
        this.cycle = Arrays.copyOf(cycle, n);
        check();
    }

    private void check() {
        Set<Integer> s = new HashSet<>();
        for (int x : cycle) {
            if (s.contains(x)) {
                throw new RuntimeException();
            }
            s.add(x);
        }
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
