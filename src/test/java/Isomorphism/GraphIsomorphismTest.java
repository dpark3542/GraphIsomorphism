package Isomorphism;

import GraphTheory.Structs.Graph;
import GraphTheory.Utilities.GraphGenerator;
import GraphTheory.Utilities.GraphParser;
import GroupTheory.Engines.GAP;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class GraphIsomorphismTest {
    private Graph readTestGraph(String filename) throws IOException {
        return GraphParser.parseGraph(Files.readString(Path.of("src/test/resources/graphs/" + filename)));
    }

    private void testMethod(Method method) {

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
    void isIsomorphic() {
        for (Method method : Method.values()) {
            testMethod(method);
        }
    }

    @Test
    void inducedAction() {
        GAP gap = new GAP();

        for (int i = 3; i <= 6; i++) {
            assertEquals("S" + i, gap.identifyGroup(GraphIsomorphism.inducedAction(i)));
        }

        gap.close();
    }
}