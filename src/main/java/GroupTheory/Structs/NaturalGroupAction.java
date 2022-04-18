package GroupTheory.Structs;

public class NaturalGroupAction implements GroupAction {
    private final Group group;
    private final Domain domain;

    public NaturalGroupAction(Group group, Domain domain) {
        this.group = group;
        this.domain = domain;
    }

    // TODO:
    @Override
    public Permutation act(Permutation p, Group g) {
        return null;
    }
}
