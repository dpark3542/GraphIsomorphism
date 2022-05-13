package GroupTheory.Structs;

import java.util.*;

public class Group implements Iterable<Permutation> {
    private int n;
    private final List<Permutation> generators;

    public Group(Collection<Permutation> generators) {
        n = generators.size();
        this.generators = new ArrayList<>(n);
        for (Permutation generator : generators) {
            if (!generator.isIdentity()) {
                this.generators.add(generator);
            }
            else {
                n--;
            }
        }

        cleanUp();
    }

    public Group(Permutation... generators) {
        n = generators.length;
        this.generators = new ArrayList<>(n);
        for (Permutation generator : generators) {
            if (!generator.isIdentity()) {
                this.generators.add(generator);
            }
            else {
                n--;
            }
        }

        cleanUp();
    }

    private void cleanUp() {
        if (n == 0) {
            n = 1;
            generators.add(new Permutation());
        }
    }

    public boolean isTrivial() {
        return n == 1 && generators.get(0).isIdentity();
    }

    @Override
    public Iterator<Permutation> iterator() {
        return Collections.unmodifiableList(generators).iterator();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Group(");
        for (int i = 0; i < n - 1; i++) {
            sb.append(generators.get(i).toString());
            sb.append(',');
        }
        sb.append(generators.get(n - 1).toString());
        sb.append(')');
        return sb.toString();
    }
}
