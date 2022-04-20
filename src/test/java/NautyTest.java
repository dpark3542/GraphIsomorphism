import org.junit.jupiter.api.Test;

import java.io.IOException;

import static GraphIsomorphism.GraphGenerator.completeGraph;
import static GraphIsomorphism.GraphGenerator.cycleGraph;
import static org.junit.jupiter.api.Assertions.*;

class NautyTest {
    private String location = "/home/dpark/nauty27r3";

    @Test
    void isIsomorphic() throws IOException {
        Nauty nauty = new Nauty(location);
        assertTrue(nauty.isIsomorphic(completeGraph(3), completeGraph(3)));
        assertFalse(nauty.isIsomorphic(completeGraph(4), cycleGraph(4)));
    }
}