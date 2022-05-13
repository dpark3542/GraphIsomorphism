package GroupTheory.Utilities;

import GroupTheory.Engines.GAP;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GroupGeneratorTest {

    @Test
    void symmetricGroup() {
        GAP gap = new GAP();

        assertEquals(1, gap.getOrder(GroupGenerator.symmetricGroup(1)));
        assertEquals(2, gap.getOrder(GroupGenerator.symmetricGroup(2)));
        for (int i = 3; i <= 6; i++) {
            assertEquals("S" + i, gap.identifyGroup(GroupGenerator.symmetricGroup(i)));
        }

        gap.close();
    }
}