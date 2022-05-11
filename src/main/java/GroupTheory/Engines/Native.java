package GroupTheory.Engines;

import GroupTheory.Structs.Domain;
import GroupTheory.Structs.FormalString;
import GroupTheory.Structs.Group;
import GroupTheory.Structs.Permutation;

import java.util.List;

// TODO:
public class Native implements GroupTheoryEngine {
    @Override
    public Permutation multiply(Permutation p, Permutation q) {
        return null;
    }

    @Override
    public Permutation invert(Permutation p) {
        return null;
    }

    @Override
    public FormalString permute(FormalString s, Permutation p) {
        return null;
    }

    @Override
    public int getOrder(Group g) {
        return 0;
    }

    @Override
    public boolean isMember(Permutation p, Group g) {
        return false;
    }

    @Override
    public boolean isTransitive(Group g, Domain d) {
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
    public Group getMinimalBlockSystemStabilizer(Group g, Domain d) {
        return null;
    }

    @Override
    public List<Permutation> getTransversal(Group g, Group h) {
        return null;
    }

    @Override
    public void close() {

    }
}
