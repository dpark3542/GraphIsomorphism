package GraphIsomorphism;

import java.util.ArrayDeque;
import java.util.Deque;

public final class GraphUtil {
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
}
