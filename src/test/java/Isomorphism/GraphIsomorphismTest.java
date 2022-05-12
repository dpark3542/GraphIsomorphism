package Isomorphism;

import GraphTheory.Utilities.GraphGenerator;
import GroupTheory.Engines.GAP;
import GroupTheory.Engines.GroupTheoryEngine;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GraphIsomorphismTest {

    @Test
    void testNaive() {
        for (int i = 2; i <= 6; i++) {
            assertTrue(GraphIsomorphism.isIsomorphic(GraphGenerator.completeGraph(i), GraphGenerator.completeGraph(i), Method.Naive));
        }

        assertFalse(GraphIsomorphism.isIsomorphic(GraphGenerator.completeGraph(4), GraphGenerator.cycleGraph(4), Method.Naive));
    }

    @Test
    void inducedAction() {
        GroupTheoryEngine gap = new GAP();
        int factorial = 2;

        for (int i = 3; i <= 6; i++) {
            factorial *= i;
            assertEquals(factorial, gap.getOrder(GraphIsomorphism.inducedAction(i)));
        }

        gap.close();
    }
}