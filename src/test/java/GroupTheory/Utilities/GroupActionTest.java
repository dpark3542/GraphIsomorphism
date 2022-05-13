package GroupTheory.Utilities;

import GroupTheory.Engines.GAP;
import GroupTheory.Structs.Group;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GroupActionTest {

    @Test
    void inducedAction() {
        GAP gap = new GAP();

        for (int i = 3; i <= 6; i++) {
            Group g = GroupAction.inducedAction(gap, GroupGenerator.symmetricGroup(i), i, 2);
            assertEquals("S" + i, gap.identifyGroup(g));
        }

        gap.close();
    }

    @Test
    void pullbackAction() {
        GAP gap = new GAP(true);

        for (int i = 3; i <= 6; i++) {
            Group g = GroupAction.inducedAction(gap, GroupGenerator.symmetricGroup(i), i, 2);
            g = GroupAction.pullbackAction(gap, g, i, 2);
            assertEquals("S" + i, gap.identifyGroup(g));
        }

        gap.close();
    }
}