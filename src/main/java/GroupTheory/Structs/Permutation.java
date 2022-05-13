package GroupTheory.Structs;

import java.util.*;

public class Permutation implements Iterable<Cycle> {
    private final int n;
    private final Cycle[] cycles;

    public Permutation(Collection<Cycle> cycles) {
        n = cycles.size();
        this.cycles = new Cycle[n];
        int i = 0;
        for (Cycle cycle : cycles) {
            this.cycles[i] = cycle;
            i++;
        }
        check();
    }

    public Permutation(Cycle... cycles) {
        n = cycles.length;
        this.cycles = Arrays.copyOf(cycles, n);
        check();
    }

    private void check() {
        Set<Integer> s = new HashSet<>();
        for (Cycle cycle : cycles) {
            for (int x : cycle) {
                if (s.contains(x)) {
                    throw new RuntimeException();
                }
                s.add(x);
            }
        }
    }

    public boolean isIdentity() {
        return n == 0;
    }

    @Override
    public Iterator<Cycle> iterator() {
        return new Iterator<>() {
            private int i = 0;
            private final int n = Permutation.this.n;
            private final Cycle[] cycles = Permutation.this.cycles;

            @Override
            public boolean hasNext() {
                return i < n;
            }

            @Override
            public Cycle next() {
                Cycle cycle = cycles[i];
                i++;
                return cycle;
            }
        };
    }

    @Override
    public String toString() {
        if (isIdentity()) {
            return "()";
        }
        else {
            StringBuilder sb = new StringBuilder();
            for (Cycle cycle : cycles) {
                sb.append(cycle.toString());
            }
            return sb.toString();
        }
    }
}
