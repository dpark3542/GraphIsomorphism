package GroupTheory.Engines;

import GroupTheory.Structs.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class GAPTest {
    private String location = "/home/dpark/gap-4.11.1/gap";
    private Group K4 = new Group(new Permutation(new Cycle(1, 2)), new Permutation(new Cycle(3, 4)));

    @Test
    void getOrder() {
        GroupTheoryEngine gap = new GAP(location, true);

        assertEquals(4, gap.getOrder(K4));

        gap.close();
    }

    @Test
    void isMember() {
        GroupTheoryEngine gap = new GAP(location, true);

        assertTrue(gap.isMember(new Permutation(new Cycle(1, 2), new Cycle(3, 4)), K4));
        assertFalse(gap.isMember(new Permutation(new Cycle(1, 3)), K4));

        gap.close();
    }

    @Test
    void getOrbits() {
        GroupTheoryEngine gap = new GAP(location, true);

        gap.getOrbits(K4, new ImplicitDomain(4, 2));

        gap.close();
    }

    @Test
    void getPointwiseStabilizer() {
    }

    @Test
    void getMinimalBlockSystem() {
    }
}