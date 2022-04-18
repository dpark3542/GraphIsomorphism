package GroupTheory.Structs;

import java.util.*;

public class Tuple {
    private final int n;
    private final Set<Integer> tuple;

    public Tuple(List<Integer> tuple) {
        n = tuple.size();
        this.tuple = new HashSet<>(tuple);
    }

    public Tuple(Set<Integer> tuple) {
        n = tuple.size();
        this.tuple = Set.copyOf(tuple);
    }

    public Tuple(int... tuple) {
        n = tuple.length;
        this.tuple = new HashSet<>(n);
        for (int x : tuple) {
            this.tuple.add(x);
        }
    }

    @Override
    public String toString() {
        List<Integer> list = new ArrayList<>(tuple);
        Collections.sort(list);

        StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (int i = 0; i < n - 1; i++) {
            sb.append(list.get(i));
            sb.append(',');
        }
        sb.append(list.get(n - 1));
        sb.append('}');
        return sb.toString();
    }
}
