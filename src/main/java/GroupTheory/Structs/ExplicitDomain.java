package GroupTheory.Structs;

import java.util.Collection;
import java.util.Iterator;

public class ExplicitDomain implements Domain {
    private final int n, k, m;
    private final boolean[][] domain;

    public ExplicitDomain(int n, int k, Collection<Tuple> domain) {
        this.n = n;
        this.k = k;
        this.m = domain.size();
        this.domain = new boolean[m][n];
        int i = 0;
        for (Tuple tuple : domain) {
            for (int x : tuple) {
                this.domain[i][x - 1] = true;
            }
            i++;
        }
    }

    @Override
    public Iterator<Tuple> iterator() {
        return new Iterator<>() {
            private int i = 0;
            private final int m = ExplicitDomain.this.m;
            private final boolean[][] domain = ExplicitDomain.this.domain;

            @Override
            public boolean hasNext() {
                return i < m;
            }

            @Override
            public Tuple next() {
                Tuple tuple = Tuple.fromIndicator(domain[i]);
                i++;
                return tuple;
            }
        };
    }

    @Override
    public boolean inDomain(Tuple t) {
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        int i = 0;
        while (i < m) {
            sb.append('[');
            for (int x = 0, j = 0; x < n; x++) {
                if (domain[i][x]) {
                    sb.append(x + 1);
                    j++;
                    if (j < k) {
                        sb.append(", ");
                    }
                    else {
                        break;
                    }
                }
            }
            sb.append(']');
            i++;
            if (i < m) {
                sb.append(", ");
            }
        }
        sb.append(']');
        return sb.toString();
    }
}
