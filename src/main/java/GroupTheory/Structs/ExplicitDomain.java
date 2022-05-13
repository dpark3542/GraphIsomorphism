package GroupTheory.Structs;

import java.util.*;

public class ExplicitDomain implements Domain {
    private final int n;
    private final List<Tuple> domain;

    public ExplicitDomain(Collection<Tuple> domain) {
        this.n = domain.size();
        List<Tuple> a = new ArrayList<>(domain);
        Collections.sort(a);
        this.domain = Collections.unmodifiableList(a);
    }

    public ExplicitDomain(Tuple... domain) {
        this.n = domain.length;
        List<Tuple> a = Arrays.asList(domain);
        Collections.sort(a);
        this.domain = Collections.unmodifiableList(a);
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
    public int size() {
        return n;
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
