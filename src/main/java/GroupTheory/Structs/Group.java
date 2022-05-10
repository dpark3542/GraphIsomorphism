package GroupTheory.Structs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Group implements Iterable<Permutation> {
    private final int n;
    private final Permutation[] generators;

    public Group(List<Permutation> generators) {
        n = generators.size();
        this.generators = (Permutation[]) generators.toArray();
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
