package Parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NestedParserTest {
    private static final String K4Orbit = "[ [ [ 1, 2 ] ], [ [ 1, 3 ], [ 2, 3 ], [ 1, 4 ], [ 2, 4 ] ], [ [ 3, 4 ] ] ]";

    private static String compress(String s) {
        return s.replaceAll("\s+", "");
    }

    @Test
    void parse() {
        Node node = NestedParser.parse(K4Orbit);
        assertEquals(compress(K4Orbit), node.toString());
    }
}