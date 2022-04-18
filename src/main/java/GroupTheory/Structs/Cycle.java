package GroupTheory.Structs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Cycle {
    private final int n;
    private final List<Integer> cycle;

    public Cycle(List<Integer> cycle) {
        n = cycle.size();
        this.cycle = List.copyOf(cycle);
        check();
    }

    public Cycle(int... cycle) {
        n = cycle.length;
        this.cycle = new ArrayList<>(n);
        for (int x : cycle) {
            this.cycle.add(x);
        }
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
            sb.append(cycle.get(i));
            sb.append(',');
        }
        sb.append(cycle.get(n - 1));
        sb.append(')');
        return sb.toString();
    }
}
