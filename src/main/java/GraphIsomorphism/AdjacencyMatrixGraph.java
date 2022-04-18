package GraphIsomorphism;

import java.util.ArrayList;
import java.util.List;

public class AdjacencyMatrixGraph implements Graph {
    private final int n;
    private final boolean[][] g;

    public AdjacencyMatrixGraph(boolean[][] m) {
        n = m.length;
        if (n != m[0].length) {
            throw new IllegalArgumentException();
        }
        g = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                g[i][j] = m[i][j];
                if (m[i][j] != m[j][i]) {
                    throw new IllegalArgumentException();
                }
            }
        }
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
}
