package GroupTheory.Engines;

import GroupTheory.Structs.Group;
import GroupTheory.Structs.GroupAction;
import GroupTheory.Structs.Permutation;

import java.util.List;

public interface GroupTheoryEngine {
    long getOrder(Group g);
    boolean isMember(Permutation p, Group g);
    // TODO:
//    List<Permutation> getBlockStabilizers(Group g);
//    GroupAction getMinimalBlockSystem(Group g);
}
