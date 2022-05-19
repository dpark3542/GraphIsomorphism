import GraphTheory.Structs.Graph;
import GraphTheory.Utilities.GraphParser;
import Isomorphism.GraphIsomorphism;
import Isomorphism.Method;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Benchmark {
    private static Graph getGraph(String s, int i) {
        try {
            return GraphParser.parseGraph(Files.readString(Path.of("src/test/resources/graphs/" + s + '_' + i + ".txt")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static long benchmark(String s, int m, Method method) {
        long start = System.nanoTime();

        Graph[] graphs = new Graph[m];
        for (int i = 1; i <= m; i++) {
            graphs[i - 1] = getGraph(s, i);
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

        System.out.println(method.toString() + ' ' + s + ": " + (end - start) / 1000000000.0);

        return end - start;
    }

    private static long benchmark(int n, int m, Method method) {
        return benchmark(Integer.toString(n), m, method);
    }

    public static void main(String[] args) {
        benchmark(3, 2, Method.Nauty);
        benchmark(3, 2, Method.Naive);
        benchmark(3, 2, Method.Degree);

        benchmark(4, 6, Method.Nauty);
        benchmark(4, 6, Method.Naive);
        benchmark(4, 6, Method.Degree);

        benchmark(5, 21, Method.Nauty);
        benchmark(5, 21, Method.Naive);
        benchmark(5, 21, Method.Degree);

        for (int i = 6; i <= 9; i++) {
            benchmark(i, 3, Method.Nauty);
            benchmark(i, 3, Method.Naive);
            benchmark(i, 3, Method.Degree);
        }
    }
}
