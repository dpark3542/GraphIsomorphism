package GroupTheory.Utilities;

import GroupTheory.Engines.GAP;
import GroupTheory.Structs.Group;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GroupActionTest {

    @Test
    void inducedAction() {
        GAP gap = new GAP(true);

        Group g = GroupAction.inducedAction(gap, GroupGenerator.symmetricGroup(2), 2, 2);
        assertEquals(1, gap.getOrder(g));

        for (int i = 3; i <= 6; i++) {
            g = GroupAction.inducedAction(gap, GroupGenerator.symmetricGroup(i), i, 2);
            assertEquals("S" + i, gap.identifyGroup(g));
        }

        gap.close();
    }

    @Test
    void pullbackAction() {
        // TODO:
    }
}