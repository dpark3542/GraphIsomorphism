package GraphTheory.Structs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class AdjacencyListGraph implements Graph {
    private int n, m;
    private final List<List<Integer>> g;

    public AdjacencyListGraph(List<List<Integer>> g) {
        n = g.size();
        this.g = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            this.g.add(List.copyOf(g.get(i)));
            m += g.get(i).size();
        }
        m /= 2;
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
    public int getNumEdges() {
        return m;
    }

    @Override
    public boolean isAdjacent(int u, int v) {
        return g.get(u).contains(v);
    }

    @Override
    public List<Integer> getNeighbors(int v) {
        return Collections.unmodifiableList(g.get(v));
    }

    @Override
    public Graph getSubgraph(Collection<Integer> subset) {
        int[] map = new int[getNumVertices()];
        int k = 0, m = subset.size();
        for (int j : subset) {
            map[k] = j;
            k++;
        }
        List<List<Integer>> h = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            h.add(new ArrayList<>());
        }
        for (int i : subset) {
            for (int j : subset) {
                if (i < j && isAdjacent(i, j)) {
                    h.get(map[i]).add(map[j]);
                    h.get(map[j]).add(map[i]);
                }
            }
        }
        return new AdjacencyListGraph(h);
    }
}
