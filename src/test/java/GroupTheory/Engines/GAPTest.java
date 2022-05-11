package GroupTheory.Engines;

import GroupTheory.Structs.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GAPTest {
    private static final String location = System.getenv("GAP_HOME");
    private static final Group K4 = new Group(new Permutation(new Cycle(1, 2)), new Permutation(new Cycle(3, 4)));
    private static final Group D8 = new Group(new Permutation(new Cycle(1, 2), new Cycle(3, 4)), new Permutation(new Cycle(2, 4)));
    private static final ExplicitDomain edges;

    static {
        List<Tuple> tuples = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            tuples.add(new Tuple(i, (i % 4) + 1));
        }
        edges = new ExplicitDomain(tuples);
    }

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
    void isTransitive() {
        GroupTheoryEngine gap = new GAP(location, true);

        assertFalse(gap.isTransitive(K4, new ImplicitDomain(4, 1)));
        assertTrue(gap.isTransitive(D8, new ImplicitDomain(4, 1)));
        assertFalse(gap.isTransitive(D8, new ImplicitDomain(4, 2)));
        assertTrue(gap.isTransitive(D8, edges));

        gap.close();
    }

    @Test
    void getOrbits() {
        // TODO:
    }

    @Test
    void getPointwiseStabilizer() {
        GroupTheoryEngine gap = new GAP(location, true);

        Group stabilizers = gap.getPointwiseStabilizer(K4, new ExplicitDomain(new Tuple(1), new Tuple(2)));
        assertEquals(stabilizers.toString(), "Group((3,4))");

        stabilizers = gap.getPointwiseStabilizer(K4, new ImplicitDomain(4, 2));
        assertEquals(stabilizers.toString(), "Group()");

        gap.close();
    }

    @Test
    void getMinimalBlockSystemStabilizer() {
        GroupTheoryEngine gap = new GAP(location, true);

        // TODO:
        gap.getMinimalBlockSystemStabilizer(D8, edges);

        gap.close();
    }
}