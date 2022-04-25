package GraphTheory.Structs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdjacencyListGraph implements Graph {
    private final int n;
    private final List<List<Integer>> g;

    public AdjacencyListGraph(List<List<Integer>> g) {
        n = g.size();
        this.g = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            this.g.add(List.copyOf(g.get(i)));
        }
        check();
    }

    private void check() {
        for (int i = 0; i < n; i++) {
            for (int j : g.get(i)) {
                if (j == i) {
                    throw new IllegalArgumentException();
                }
                if (j < 0 || j >= n) {
                    throw new IllegalArgumentException();
                }
                if (!g.get(j).contains(i)) {
                    throw new IllegalArgumentException();
                }
            }
        }
    }

    @Override
    public int getNumVertices() {
        return n;
    }

    @Override
    public boolean isAdjacent(int u, int v) {
        return g.get(u).contains(v);
    }

    @Override
    public List<Integer> getNeighbors(int v) {
        return Collections.unmodifiableList(g.get(v));
    }
}
