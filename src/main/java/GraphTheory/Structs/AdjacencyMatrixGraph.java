package GraphTheory.Structs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AdjacencyMatrixGraph implements Graph {
    private int n, m;
    private final boolean[][] g;

    public AdjacencyMatrixGraph(boolean[][] g) {
        n = g.length;
        if (n != g[0].length) {
            throw new IllegalArgumentException();
        }
        this.g = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.g[i][j] = g[i][j];
                if (g[i][j]) {
                    m++;
                }
            }
        }
        m /= 2;
        check();
    }

    private void check() {
        for (int i = 0; i < n; i++) {
            if (g[i][i]) {
                throw new IllegalArgumentException();
            }
            for (int j = i + 1; j < n; j++) {
                if (g[i][j] != g[j][i]) {
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
        return g[u][v];
    }

    @Override
    public List<Integer> getNeighbors(int v) {
        List<Integer> neighbors = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (g[i][v]) {
                neighbors.add(i);
            }
        }
        return neighbors;
    }

    @Override
    public Graph getSubgraph(Collection<Integer> subset) {
        int[] map = new int[getNumVertices()];
        int k = 0, m = subset.size();
        for (int j : subset) {
            map[k] = j;
            k++;
        }
        boolean[][] h = new boolean[m][m];
        for (int i : subset) {
            for (int j : subset) {
                if (i < j && isAdjacent(i, j)) {
                    h[map[i]][map[j]] = true;
                    h[map[j]][map[i]] = true;
                }
            }
        }
        return new AdjacencyMatrixGraph(h);
    }
}
