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

    public static List<List<Integer>> breadthFirstSearch(Graph g, int e1, int e2) {
        boolean[] mkd = new boolean[g.getNumVertices()];
        Deque<Integer> q = new ArrayDeque<>();
        q.add(e1);
        q.add(e2);
        List<List<Integer>> bfs = new ArrayList<>();
        int level = 0;
        while (!q.isEmpty()) {
            int n = q.size();
            bfs.add(new ArrayList<>());
            for (int i = 0; i < n; i++) {
                int v = q.pollFirst();
                if (mkd[v]) {
                    continue;
                }
                mkd[v] = true;
                bfs.get(level).add(v);
                for (int w : g.getNeighbors(v)) {
                    q.addLast(w);
                }
            }
            level++;
        }
        bfs.remove(bfs.size() - 1);
        return bfs;
    }
}
