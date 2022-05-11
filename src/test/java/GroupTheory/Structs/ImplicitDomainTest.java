package GroupTheory.Structs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ImplicitDomainTest {

    @Test
    void iterator() {
    }

    @Test
    void inDomain() {
    }

    @Test
    void size() {
        assertEquals(4, new ImplicitDomain(4, 1).size());
        assertEquals(6, new ImplicitDomain(4, 2).size());
        assertEquals(1, new ImplicitDomain(4, 4).size());
    }
}