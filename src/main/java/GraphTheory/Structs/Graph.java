package GraphTheory.Structs;

import java.util.List;

public interface Graph {
    public int getNumVertices();
    public boolean isAdjacent(int u, int v);

    /**
     * Returns unmodifiable list of neighbors of vertex v.
     *
     * @param v Vertex
     * @return Neighbors of vertex v.
     */
    public List<Integer> getNeighbors(int v);
}
