package GroupTheory.Structs;

import java.util.ArrayList;
import java.util.List;

public class Permutation {
    private final int n;
    private final List<Cycle> cycles;

    public Permutation(List<Cycle> cycles) {
        n = cycles.size();
        this.cycles = List.copyOf(cycles);
        check();
    }

    public Permutation(Cycle... cycles) {
        n = cycles.length;
        this.cycles = new ArrayList<>(n);
        for (Cycle c : cycles) {
            this.cycles.add(c);
        }
        check();
    }

    private void check() {
        // TODO: check disjointness
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Cycle cycle : cycles) {
            sb.append(cycle.toString());
        }
        return sb.toString();
    }
}
