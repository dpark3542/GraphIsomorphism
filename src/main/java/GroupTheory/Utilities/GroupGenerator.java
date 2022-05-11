package GroupTheory.Utilities;

import GroupTheory.Structs.Cycle;
import GroupTheory.Structs.Group;
import GroupTheory.Structs.Permutation;

import java.util.ArrayList;
import java.util.List;

public class GroupGenerator {
    public static Group symmetricGroup(int n) {
        List<Integer> a = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            a.add(i);
        }
        return new Group(new Permutation(new Cycle(1, 2)), new Permutation(new Cycle(a)));
    }
}
