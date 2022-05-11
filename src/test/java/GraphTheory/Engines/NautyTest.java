package GraphTheory.Engines;

import GraphTheory.Engines.Nauty;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static GraphTheory.Utilities.GraphGenerator.completeGraph;
import static GraphTheory.Utilities.GraphGenerator.cycleGraph;
import static org.junit.jupiter.api.Assertions.*;

class NautyTest {
    @Test
    void isIsomorphic() throws IOException {
        Nauty nauty = new Nauty();
        assertTrue(nauty.isIsomorphic(completeGraph(3), completeGraph(3)));
        assertFalse(nauty.isIsomorphic(completeGraph(4), cycleGraph(4)));
    }
}