package GraphTheory.Structs;

import java.util.Collection;
import java.util.List;

public interface Graph {
    public int getSize();
    public boolean isAdjacent(int u, int v);

    /**
     * Returns unmodifiable list of neighbors of vertex v.
     *
     * @param v vertex
     * @return neighbors of vertex v
     */
    public List<Integer> getNeighbors(int v);

    /**
     * Get induced subgraph.
     *
     * @param subset subset of vertices
     * @return induced subgraph
     */
    public Graph getSubgraph(Collection<Integer> subset);
}
