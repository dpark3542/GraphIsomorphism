package Isomorphism;

import GroupTheory.Structs.Cycle;
import GroupTheory.Structs.Group;
import GroupTheory.Structs.Permutation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringIsomorphismTest {

    @Test
    void isIsomorphic() {
        Group g = new Group(new Permutation(new Cycle(1, 2)));
        assertTrue(StringIsomorphism.isIsomorphic(new FormalString(1, 2), new FormalString(2, 1), g));
        assertFalse(StringIsomorphism.isIsomorphic(new FormalString(1, 2), new FormalString(3, 1), g));
    }
}