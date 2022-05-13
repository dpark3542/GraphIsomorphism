package GraphTheory.Utilities;

import GraphTheory.Structs.AdjacencyListGraph;
import GraphTheory.Structs.AdjacencyMatrixGraph;
import GraphTheory.Structs.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class GraphParser {
    private static final Pattern adjacencyListPattern = Pattern.compile("\\s*\\d+\\s+\\d+\\s*");

    private static class IntegerTokenizer {
        private final StringTokenizer st;

        IntegerTokenizer(String s) {
            st = new StringTokenizer(s);
        }

        int nextInt() {
            return Integer.parseInt(st.nextToken());
        }
    }

    public static Graph parseGraph(String s) {
        String line = s.substring(0, s.indexOf('\n'));
        Matcher matcher = adjacencyListPattern.matcher(line);
        if (matcher.matches()) {
            return parseAdjacencyListGraph(s);
        }
        else {
            return parseAdjacencyMatrixGraph(s);
        }
    }

    private static AdjacencyListGraph parseAdjacencyListGraph(String s) {
        IntegerTokenizer in = new IntegerTokenizer(s);
        int n = in.nextInt(), m = in.nextInt();

        List<List<Integer>> g = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            g.add(new ArrayList<>());
        }

        for (int i = 0; i < m; i++) {
            int u = in.nextInt(), v = in.nextInt();
            g.get(u).add(v);
            g.get(v).add(u);
        }

        return new AdjacencyListGraph(g);
    }

    private static AdjacencyMatrixGraph parseAdjacencyMatrixGraph(String s) {
        IntegerTokenizer in = new IntegerTokenizer(s);
        int n = in.nextInt();
        boolean[][] g = new boolean[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                g[i][j] = (in.nextInt() == 1);
            }
        }

        return new AdjacencyMatrixGraph(g);
    }
}
