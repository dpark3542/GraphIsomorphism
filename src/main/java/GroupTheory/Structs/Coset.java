package GroupTheory.Structs;

public record Coset(Group group, Permutation element) {
    @Override
    public String toString() {
        return "RightCoset(" + group.toString() + ',' + element.toString() + ')';
    }
}
