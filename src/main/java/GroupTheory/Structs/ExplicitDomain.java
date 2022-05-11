package GroupTheory.Structs;

import java.util.*;

public class ExplicitDomain implements Domain {
    private final int n;
    private final SortedSet<Tuple> domain;

    public ExplicitDomain(Collection<Tuple> domain) {
        this.n = domain.size();
        this.domain = new TreeSet<>(domain);
    }

    public ExplicitDomain(Tuple... domain) {
        this(List.of(domain));
    }

    @Override
    public Iterator<Tuple> iterator() {
        return domain.iterator();
    }

    @Override
    public boolean inDomain(Tuple tuple) {
        return domain.contains(tuple);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        int i = 0;
        for (Tuple tuple : domain) {
            sb.append(tuple.toString());
            i++;
            if (i < n) {
                sb.append(',');
            }
        }
        sb.append(']');
        return sb.toString();
    }
}
