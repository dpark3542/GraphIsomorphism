package GroupTheory.Utilities;

import GroupTheory.Structs.Cycle;
import GroupTheory.Structs.Group;
import GroupTheory.Structs.Permutation;

import java.util.ArrayList;
import java.util.List;

public class GroupGenerator {
    public static Group symmetricGroup(List<Integer> a) {
        for (int i : a) {
            if (i < 1) {
                throw new IllegalArgumentException();
            }
        }
        if (a.size() == 0) {
            throw new IllegalArgumentException();
        }
        else if (a.size() == 1) {
            return new Group();
        }
        else if (a.size() == 2) {
            return new Group(new Permutation(new Cycle(a.get(0), a.get(1))));
        }
        else {
            return new Group(new Permutation(new Cycle(a.get(0), a.get(1))), new Permutation(new Cycle(a)));
        }
    }

    public static Group symmetricGroup(int n) {
        List<Integer> a = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            a.add(i);
        }
        return symmetricGroup(a);
    }
}
