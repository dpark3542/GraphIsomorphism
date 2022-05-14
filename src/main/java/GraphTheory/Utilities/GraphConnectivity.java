package GraphTheory.Utilities;

import GraphTheory.Structs.Graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public final class GraphConnectivity {
    public static boolean isConnected(Graph g) {
        int n = g.getNumVertices();
        boolean[] mkd = new boolean[n];
        Deque<Integer> st = new ArrayDeque<>();
        st.addLast(0);
        while (!st.isEmpty()) {
            int v = st.pollLast();
            if (mkd[v]) {
                continue;
            }
            mkd[v] = true;
            for (int w : g.getNeighbors(v)) {
                st.addLast(w);
            }
        }

        for (int i = 0; i < n; i++) {
            if (!mkd[i]) {
                return false;
            }
        }
        return true;
    }

    public static List<Graph> getConnectedComponents(Graph g) {
        int n = g.getNumVertices();
        boolean[] mkd = new boolean[n];
        Deque<Integer> st = new ArrayDeque<>();
        List<Graph> components = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            if (mkd[i]) {
                continue;
            }
            Deque<Integer> subset = new ArrayDeque<>();
            st.addLast(i);
            while (!st.isEmpty()) {
                int v = st.pollLast();
                if (mkd[v]) {
                    continue;
                }
                mkd[v] = true;
                subset.addLast(v);
                for (int w : g.getNeighbors(v)) {
                    st.addLast(w);
                }
            }
            components.add(g.getSubgraph(subset));
        }

        return components;
    }
}
