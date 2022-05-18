import GraphTheory.Structs.Graph;
import GraphTheory.Utilities.GraphParser;
import Isomorphism.GraphIsomorphism;
import Isomorphism.Method;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Benchmark {
    private static long test(int n, int m, Method method) throws IOException {
        long start = System.nanoTime();

        Graph[] graphs = new Graph[m];
        for (int i = 1; i <= m; i++) {
            graphs[i - 1] = GraphParser.parseGraph(Files.readString(Path.of("src/test/resources/graphs/" + n + '_' + i + ".txt")));
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

    public static void main(String[] args) throws IOException {
        test(3, 2, Method.Nauty);
        test(3, 2, Method.Naive);
        test(3, 2, Method.Degree);

        test(4, 6, Method.Nauty);
        test(4, 6, Method.Naive);
        test(4, 6, Method.Degree);
    }
}
