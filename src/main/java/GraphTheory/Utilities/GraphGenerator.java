package GraphTheory.Utilities;

import GraphTheory.Structs.AdjacencyListGraph;
import GraphTheory.Structs.AdjacencyMatrixGraph;
import GraphTheory.Structs.Graph;

import java.util.ArrayList;
import java.util.List;

public final class GraphGenerator {
    public static Graph completeGraph(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        boolean[][] m = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    m[i][j] = true;
                }
            }
        }
        return new AdjacencyMatrixGraph(m);
    }

    public static Graph cycleGraph(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        List<List<Integer>> g = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            List<Integer> a = new ArrayList<>();
            a.add(Math.floorMod(i - 1, n));
            a.add(Math.floorMod(i + 1, n));
            g.add(a);
        }
        return new AdjacencyListGraph(g);
    }
}
