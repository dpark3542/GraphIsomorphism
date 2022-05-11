package GroupTheory.Engines;

import GroupTheory.Structs.Domain;
import GroupTheory.Structs.Group;
import GroupTheory.Structs.Permutation;

import java.util.List;

public interface GroupTheoryEngine {
    Permutation multiply(Permutation p, Permutation q);
    Permutation invert(Permutation p);
    int getOrder(Group g);
    boolean isMember(Permutation p, Group g);

    List<Permutation> getTransversal(Group g, Group h);

    boolean isTransitive(Group g, Domain d);
    List<Domain> getOrbits(Group g, Domain d);
    Group getPointwiseStabilizer(Group g, Domain d);
    Group getMinimalBlockSystemStabilizer(Group g, Domain d);

    void close();
}
