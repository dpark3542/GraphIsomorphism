package Isomorphism;

import GraphTheory.Structs.Graph;
import GroupTheory.Structs.FormalString;
import GroupTheory.Utilities.GroupGenerator;

import java.util.ArrayList;
import java.util.List;

public final class GraphIsomorphism {
    private static FormalString graphToString(Graph graph) {
        int n = graph.getSize();
        List<Integer> a = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (graph.isAdjacent(i + 1, j + 1)) {
                    a.add(2);
                }
                else {
                    a.add(1);
                }
            }
        }
        return new FormalString(a);
    }

    public static boolean isIsomorphic(Graph g, Graph h, Method method) {
        int n = g.getSize();
        if (h.getSize() != n) {
            return false;
        }

        if (method == Method.Naive) {
            return StringIsomorphism.isIsomorphic(graphToString(g), graphToString(h), GroupGenerator.symmetricGroup(n));
        }
        else if (method == Method.Nauty) {
            return false;
        }
        else {
            throw new UnsupportedOperationException();
        }
    }
}
