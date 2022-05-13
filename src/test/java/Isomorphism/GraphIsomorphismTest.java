package Isomorphism;

import GraphTheory.Structs.Graph;
import GraphTheory.Utilities.GraphGenerator;
import GraphTheory.Utilities.GraphParser;
import GroupTheory.Engines.GAP;
import GroupTheory.Utilities.GroupAction;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class GraphIsomorphismTest {
    private static Graph readTestGraph(String filename) throws IOException {
        return GraphParser.parseGraph(Files.readString(Path.of("src/test/resources/graphs/" + filename)));
    }

    private static void testMethod(Method method) {

        for (int i = 2; i <= 7; i++) {
            assertTrue(GraphIsomorphism.isIsomorphic(GraphGenerator.completeGraph(i), GraphGenerator.completeGraph(i), method));
            assertTrue(GraphIsomorphism.isIsomorphic(GraphGenerator.cycleGraph(i), GraphGenerator.cycleGraph(i), method));
        }

        for (int i = 4; i <= 7; i++) {
            assertFalse(GraphIsomorphism.isIsomorphic(GraphGenerator.completeGraph(4), GraphGenerator.cycleGraph(4), method));
        }

//        int n = 10;
//        Graph[] petersen = new Graph[n];
//        try {
//            for (int i = 1; i <= n; i++) {
//                petersen[i - 1] = readTestGraph("petersen_" + i + ".txt");
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        for (int i = 0; i < n; i++) {
//            for (int j = i + 1; j < n; j++) {
//                assertTrue(GraphIsomorphism.isIsomorphic(petersen[i], petersen[j], method));
//            }
//        }
    }

    @Test
    void testNaive() {
        testMethod(Method.Naive);
    }

    @Test
    void testNauty() {
        testMethod(Method.Nauty);
    }

    @Test
    void testDegree() {
        for (int i = 2; i <= 4; i++) {
            assertTrue(GraphIsomorphism.isIsomorphic(GraphGenerator.completeGraph(i), GraphGenerator.completeGraph(i), Method.Degree));
            assertTrue(GraphIsomorphism.isIsomorphic(GraphGenerator.cycleGraph(i), GraphGenerator.cycleGraph(i), Method.Degree));
        }

        assertFalse(GraphIsomorphism.isIsomorphic(GraphGenerator.completeGraph(4), GraphGenerator.cycleGraph(4), Method.Degree));
    }
}