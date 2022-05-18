import GraphTheory.Structs.Graph;
import GraphTheory.Utilities.GraphParser;
import Isomorphism.GraphIsomorphism;
import Isomorphism.Method;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Benchmark {
    private static Graph getGraph(int n, int i) {
        try {
            return GraphParser.parseGraph(Files.readString(Path.of("src/test/resources/graphs/" + n + '_' + i + ".txt")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static long test(int n, int m, Method method) {
        long start = System.nanoTime();

        Graph[] graphs = new Graph[m];
        for (int i = 1; i <= m; i++) {
            graphs[i - 1] = getGraph(n, i);
        }

        for (int i = 0; i < m; i++) {
            if (!GraphIsomorphism.isIsomorphic(graphs[i], graphs[i], method)) {
                throw new RuntimeException();
            }
            for (int j = i + 1; j < m; j++) {
                if (GraphIsomorphism.isIsomorphic(graphs[i], graphs[j], method)) {
                    throw new RuntimeException();
                }
            }
        }

        long end = System.nanoTime();

        System.out.println(method.toString() + ' ' + n + ": " + (end - start) / 1000000000.0);

        return end - start;
    }

    public static void main(String[] args) {
        test(3, 2, Method.Nauty);
        test(3, 2, Method.Naive);
        test(3, 2, Method.Degree);

        test(4, 6, Method.Nauty);
        test(4, 6, Method.Naive);
        test(4, 6, Method.Degree);

        test(5, 21, Method.Nauty);
        test(5, 21, Method.Naive);
        test(5, 21, Method.Degree);
    }
}
