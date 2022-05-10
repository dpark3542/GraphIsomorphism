package GroupTheory.Structs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ImplicitDomainTest {

    @Test
    void iterator() {
        ImplicitDomain domain = new ImplicitDomain(4, 2);
        // TODO:
        for (Tuple tuple : domain) {
            System.out.println(tuple);
        }
    }
}