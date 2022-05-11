package Isomorphism;

import java.util.Arrays;

public class FormalString {
    private final int n;
    private final int[] map;

    public FormalString(int... map) {
        n = map.length;
        this.map = Arrays.copyOf(map, n);
        check();
    }

    private void check() {
        for (int i = 0; i < n; i++) {
            if (map[i] < 1) {
                throw new RuntimeException();
            }
        }
    }

    public int size() {
        return n;
    }

    public int get(int i) {
        return map[i - 1];
    }
}
