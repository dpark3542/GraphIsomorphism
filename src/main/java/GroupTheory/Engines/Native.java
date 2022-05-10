package GroupTheory.Engines;

import GroupTheory.Structs.Domain;
import GroupTheory.Structs.Group;
import GroupTheory.Structs.Permutation;

import java.util.List;

// TODO:
public class Native implements GroupTheoryEngine {
    @Override
    public long getOrder(Group g) {
        return 0;
    }

    @Override
    public boolean isMember(Permutation p, Group g) {
        return false;
    }

    @Override
    public List<Domain> getOrbits(Group g, Domain d) {
        return null;
    }

    @Override
    public Group getPointwiseStabilizer(Group g, Domain d) {
        return null;
    }

    @Override
    public Domain getMinimalBlockSystem(Group g, Domain d) {
        return null;
    }

    @Override
    public void close() {

    }
}
