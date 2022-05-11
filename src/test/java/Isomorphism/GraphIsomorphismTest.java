package Isomorphism;

import GraphTheory.Utilities.GraphGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GraphIsomorphismTest {

    @Test
    void testNaive() {
        assertTrue(GraphIsomorphism.isIsomorphic(GraphGenerator.completeGraph(3), GraphGenerator.cycleGraph(3), Method.Naive));
        assertFalse(GraphIsomorphism.isIsomorphic(GraphGenerator.completeGraph(4), GraphGenerator.cycleGraph(4), Method.Naive));
    }
}