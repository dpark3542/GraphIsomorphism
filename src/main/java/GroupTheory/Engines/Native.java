package GroupTheory.Engines;

import GroupTheory.Structs.Group;
import GroupTheory.Structs.Permutation;

public class Native implements GroupTheoryEngine {
    @Override
    public long getOrder(Group g) {
        return 0;
    }

    @Override
    public boolean isMember(Permutation p, Group g) {
        return false;
    }
}
