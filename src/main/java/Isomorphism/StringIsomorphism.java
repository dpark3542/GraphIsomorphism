package Isomorphism;

import GroupTheory.Engines.GroupTheoryEngine;
import GroupTheory.Structs.*;

import java.util.ArrayList;
import java.util.List;

public class StringIsomorphism {
    private final GroupTheoryEngine engine;
    private FormalString s;

    public StringIsomorphism(GroupTheoryEngine engine) {
        this.engine = engine;
    }

    public boolean isIsomorphic(FormalString s, FormalString t, Group g) {
        return getIsomorphismCoset(s, t, g) != null;
    }

    public Coset getIsomorphismCoset(FormalString s, FormalString t, Group g) {
        int n = s.size();
        if (t.size() != n) {
            return null;
        }

        this.s = s;

        Domain d = new ImplicitDomain(n, 1);
        Coset c = new Coset(g, new Permutation());

        return luks(c, d, t);
    }

    /**
     * Returns closure of union of two cosets.
     * Does not actually give union of two cosets!
     *
     * @param x first coset
     * @param y second coset
     * @return closure of union of cosets
     */
    private Coset unionClosure(Coset x, Coset y) {
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

    private Coset luks(Coset c, Domain d, FormalString t) {
        if (c == null) {
            return null;
        }

        if (!c.element().isIdentity()) {
            Coset tmp = luks(new Coset(c.group(), new Permutation()), d, engine.permute(t, engine.invert(c.element())));
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
                c = luks(c, orbit, t);
            }
            return c;
        }

        Group delta = engine.getMinimalBlockSystemStabilizer(g, d);
        List<Permutation> transversal = engine.getTransversal(g, delta);
        Coset ans = luks(new Coset(delta, transversal.get(0)), d, t);
        for (int i = 1; i < transversal.size(); i++) {
            // closure of union is fine since result should be a group
            ans = unionClosure(ans, luks(new Coset(delta, transversal.get(i)), d, t));
        }

        return ans;
    }
}
