package GroupTheory.Engines;

import GroupTheory.Structs.*;

import java.util.List;

public interface GroupTheoryEngine {
    Permutation listToPermutation(List<Integer> list);
    Permutation multiply(Permutation p, Permutation q);
    Permutation invert(Permutation p);

    /**
     * Permutes string.
     * Important: does not invert permutation from Babai's notation.
     *
     * @param s string
     * @param p permutation
     * @return permuted string
     */
    FormalString permute(FormalString s, Permutation p);

    /**
     * Gives image of each number in list under permutation.
     * Important: different from permute.
     *
     * @param t list
     * @param p permutation
     * @return image
     */
    List<Integer> act(List<Integer> list, Permutation p);
    List<Tuple> act(ImplicitDomain domain, Permutation p);

    int getOrder(Group g);
    boolean isMember(Permutation p, Group g);

    List<Permutation> getTransversal(Group g, Group h);

    boolean isTransitive(Group g, Domain d);
    List<Domain> getOrbits(Group g, Domain d);
    Group getPointwiseStabilizer(Group g, Domain d);
    Group getMinimalBlockSystemStabilizer(Group g, Domain d);

    void close();
}
