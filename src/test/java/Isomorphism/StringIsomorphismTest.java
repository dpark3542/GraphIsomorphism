package Isomorphism;

import GroupTheory.Engines.GAP;
import GroupTheory.Engines.GroupTheoryEngine;
import GroupTheory.Structs.*;
import GroupTheory.Utilities.GroupGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringIsomorphismTest {

    @Test
    void isIsomorphic() {
        GroupTheoryEngine gap = new GAP();
        StringIsomorphism si = new StringIsomorphism(gap);

        Group g = new Group(new Permutation(new Cycle(1, 2)));
        assertTrue(si.isIsomorphic(new FormalString(1, 2), new FormalString(2, 1), g));
        assertFalse(si.isIsomorphic(new FormalString(1, 2), new FormalString(3, 1), g));
        assertTrue(si.isIsomorphic(new FormalString(1, 1), new FormalString(1, 1), g));

        g = new Group(new Permutation(new Cycle(2, 3, 4)));
        assertTrue(si.isIsomorphic(new FormalString(1, 2, 3, 4, 5), new FormalString(1, 4, 2, 3, 5), g));
        assertFalse(si.isIsomorphic(new FormalString(1, 2, 3, 4, 5), new FormalString(2, 1, 3, 4, 5), g));

        g = new Group(new Permutation(new Cycle(3, 4)));
        assertTrue(si.isIsomorphic(new FormalString(1, 1, 1, 2, 2, 1), new FormalString(1, 1, 1, 2, 2, 1), g));
    }

    @Test
    void getIsomorphismCoset() {
        GroupTheoryEngine gap = new GAP();
        StringIsomorphism si = new StringIsomorphism(gap);

        Group g = new Group(new Permutation(new Cycle(3, 4)));
        Coset coset = si.getIsomorphismCoset(new FormalString(1, 1, 1, 2, 2, 1), new FormalString(1, 1, 1, 2, 2, 1), g);
        assertNotNull(coset.group());
        assertTrue(coset.group().isTrivial());
        assertTrue(coset.element().isIdentity());

        coset = si.getIsomorphismCoset(new FormalString(1, 1, 2, 2, 1, 1), new FormalString(1, 1, 2, 2, 1, 1), g);
        assertNotNull(coset.group());
        assertEquals("Group((3,4))", coset.group().toString());
        assertTrue(coset.element().isIdentity());

        g = new Group(new Permutation(new Cycle(1, 2)), new Permutation(new Cycle(3, 4)));
        coset = si.getIsomorphismCoset(new FormalString(1, 2, 1, 2), new FormalString(2, 1, 2, 1), g);
        assertTrue(coset.group().isTrivial());
        assertEquals(coset.element().toString(), "(1,2)(3,4)");

        g = GroupGenerator.symmetricGroup(5);
        coset = si.getIsomorphismCoset(new FormalString(1, 2, 3, 4, 5), new FormalString(5, 1, 2, 3, 4), g);
        assertTrue(coset.group().isTrivial());
        assertEquals(coset.element().toString(), "(1,2,3,4,5)");

        gap.close();
    }
}