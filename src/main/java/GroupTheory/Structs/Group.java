package GroupTheory.Structs;

import java.util.*;

public class Group implements Iterable<Permutation> {
    private final int n;
    private final Permutation[] generators;

    public Group(Collection<Permutation> generators) {
        n = generators.size();
        this.generators = new Permutation[n];
        int i = 0;
        for (Permutation generator : generators) {
            this.generators[i] = generator;
            i++;
        }
    }

    public Group(Permutation... generators) {
        n = generators.length;
        this.generators = Arrays.copyOf(generators, n);
    }

    @Override
    public Iterator<Permutation> iterator() {
        return new Iterator<>() {
            private int i = 0;
            private final int n = Group.this.n;
            private final Permutation[] generators = Group.this.generators;

            @Override
            public boolean hasNext() {
                return i < n;
            }

            @Override
            public Permutation next() {
                Permutation generator = generators[i];
                i++;
                return generator;
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Group(");
        for (int i = 0; i < n - 1; i++) {
            sb.append(generators[i].toString());
            sb.append(',');
        }
        sb.append(generators[n - 1]);
        sb.append(')');
        return sb.toString();
    }
}
