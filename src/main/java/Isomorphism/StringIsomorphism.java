package Isomorphism;

import GroupTheory.Engines.GAP;
import GroupTheory.Engines.GroupTheoryEngine;
import GroupTheory.Structs.*;

import java.util.ArrayList;
import java.util.List;

public final class StringIsomorphism {
    // TODO: consider storing engine, s, t
    public static boolean isIsomorphic(FormalString s, FormalString t, Group g) {
        int n = s.size();
        if (t.size() != n) {
            return false;
        }

        GroupTheoryEngine gap = new GAP();
        Domain d = new ImplicitDomain(n, 1);
        Coset c = new Coset(g, new Permutation());

        Coset iso = luks(gap, c, d, s, t);

        gap.close();

        return iso != null;
    }

    private static Coset union(GroupTheoryEngine engine, Coset x, Coset y) {
        if (x == null) {
            return y;
        }
        if (y == null) {
            return x;
        }

        List<Permutation> generators = new ArrayList<>();
        x.group().iterator().forEachRemaining(generators::add);

        Permutation m = engine.multiply(y.element(), engine.invert(x.element()));
        for (Permutation p : y.group()) {
            generators.add(engine.multiply(p, m));
        }

        return new Coset(new Group(generators), x.element());
    }

    private static Coset luks(GroupTheoryEngine engine, Coset c, Domain d, FormalString s, FormalString t) {
        if (c == null) {
            return null;
        }

        if (!c.element().isIdentity()) {
            Coset tmp = luks(engine, new Coset(c.group(), new Permutation()), d, s, engine.permute(t, c.element()));
            if (tmp == null) {
                return null;
            }
            else {
                return new Coset(tmp.group(), engine.multiply(tmp.element(), c.element()));
            }
        }

        Group g = c.group();
        if (d.size() == 1) {
            int i = d.iterator().next().get(0);
            if (s.get(i) == t.get(i)) {
                return c;
            }
            else {
                return null;
            }
        }

        if (!engine.isTransitive(g, d)) {
            List<Domain> orbits = engine.getOrbits(g, d);
            for (Domain orbit : orbits) {
                Coset tmp = luks(engine, new Coset(g, new Permutation()), orbit, s, t);
                if (tmp == null) {
                    return null;
                }
                g = tmp.group();
            }
            return new Coset(g, new Permutation());
        }

        Group delta = engine.getMinimalBlockSystemStabilizer(g, d);
        List<Permutation> transversal = engine.getTransversal(g, delta);
        Coset ans = luks(engine, new Coset(delta, transversal.get(0)), d, s, t);
        for (int i = 1; i < transversal.size(); i++) {
            ans = union(engine, ans, luks(engine, new Coset(delta, transversal.get(i)), d, s, t));
        }

        return ans;
    }
}
