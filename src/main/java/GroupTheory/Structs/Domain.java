package GroupTheory.Structs;

import java.util.Set;

public class Domain {
    private final Set<Tuple> domain;

    public Domain(Set<Tuple> domain) {
        this.domain = Set.copyOf(domain);
    }
}
