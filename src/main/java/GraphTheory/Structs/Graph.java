package GraphTheory.Structs;

import java.util.Collection;
import java.util.List;

public interface Graph {
    /**
     * Returns number of vertices.
     *
     * @return number of vertices.
     */
    public int getNumVertices();

    /**
     * Returns number of edges.
     *
     * @return number of edges
     */
    public int getNumEdges();
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
