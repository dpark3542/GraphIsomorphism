package GroupTheory.Structs;

import java.util.Set;

public class ExplicitDomain implements Domain {
    private final Set<Tuple> domain;

    public ExplicitDomain(Set<Tuple> domain) {
        this.domain = Set.copyOf(domain);
    }

    @Override
    public boolean inDomain(int x) {
        return domain.contains(new Tuple(x));
    }

    @Override
    public boolean inDomain(Tuple t) {
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (Tuple t : domain) {
            sb.append(t.toString());
            sb.append(", ");
        }
        sb.append(']');
        return sb.toString();
    }
}
