package GroupTheory.Engines;

import GroupTheory.Structs.Cycle;
import GroupTheory.Structs.Group;
import GroupTheory.Structs.Permutation;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class GAPTest {
    private String location = "/home/dpark/gap-4.11.1/gap";

    @Test
    void getOrder() throws IOException {
        GAP gap = new GAP(location);

        // K_4
        Permutation p = new Permutation(new Cycle(1, 2)), q = new Permutation(new Cycle(3, 4));
        Group g = new Group(p, q);
        assertEquals(4, gap.getOrder(g));

        gap.close();
    }
}