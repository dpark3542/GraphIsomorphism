package Isomorphism;

import GraphTheory.Engines.Nauty;
import GraphTheory.Structs.Graph;
import GroupTheory.Engines.GAP;
import GroupTheory.Structs.FormalString;
import GroupTheory.Structs.Group;
import GroupTheory.Utilities.GroupAction;
import GroupTheory.Utilities.GroupGenerator;

import java.util.ArrayList;
import java.util.List;

public final class GraphIsomorphism {
    private static FormalString graphToString(Graph graph) {
        int n = graph.getNumVertices();
        List<Integer> a = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (graph.isAdjacent(i, j)) {
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
        int n = g.getNumVertices();
        if (h.getNumVertices() != n) {
            return false;
        }

        if (n <= 1) {
            return true;
        }

        // TODO: allow choice of group theory engine
        if (method == Method.Naive) {
            GAP gap = new GAP();
            StringIsomorphism si = new StringIsomorphism(gap);
            Group action = GroupAction.inducedAction(gap, GroupGenerator.symmetricGroup(n), n, 2);
            boolean result = si.isIsomorphic(graphToString(g), graphToString(h), action);
            gap.close();
            return result;
        }
        else if (method == Method.Nauty) {
            Nauty nauty = new Nauty();
            return nauty.isIsomorphic(g, h);
        }
        else if (method == Method.Degree) {
            GAP gap = new GAP();
            DegreeGraphIsomorphism dgi = new DegreeGraphIsomorphism(gap);
            boolean result = dgi.isDegreeIsomorphic(g, h);
            gap.close();
            return result;
        }
        else {
            throw new UnsupportedOperationException();
        }
    }
}
