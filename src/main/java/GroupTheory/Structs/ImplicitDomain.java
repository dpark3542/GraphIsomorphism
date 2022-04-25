package GroupTheory.Structs;

/**
 * Represents family of all k-subsets of [n].
 */
public class ImplicitDomain implements Domain {
    private final int n, k;

    public ImplicitDomain(int n, int k) {
        this.n = n;
        this.k = k;
    }

    public int getN() {
        return n;
    }

    public int getK() {
        return k;
    }

    @Override
    public boolean inDomain(int x) {
        return k == 1 && 1 <= x && x <= n;
    }

    @Override
    public boolean inDomain(Tuple t) {
        return false;
    }

    @Override
    public String toString() {
//        if (n == 1) {
//            return "[1.." + k;
//        }
//        else {
//            return "Combinations([1.." + n + "], " + k + ')';
//        }
        return "Combinations([1.." + n + "], " + k + ')';
    }
}
