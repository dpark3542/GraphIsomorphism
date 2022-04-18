package GraphIsomorphism;

import java.util.List;

public interface Graph {
    public boolean isAdjacent(int u, int v);
    public List<Integer> getNeighbors(int v);
}
