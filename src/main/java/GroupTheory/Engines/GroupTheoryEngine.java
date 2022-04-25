package GroupTheory.Engines;

import GroupTheory.Structs.Domain;
import GroupTheory.Structs.Group;
import GroupTheory.Structs.Permutation;

import java.util.List;

public interface GroupTheoryEngine {
    long getOrder(Group g);
    boolean isMember(Permutation p, Group g);
    List<Domain> getOrbits(Group g, Domain d);
    Group getPointwiseStabilizer(Group g, Domain d);
    Domain getMinimalBlockSystem(Group g, Domain d);
    void close();
}
