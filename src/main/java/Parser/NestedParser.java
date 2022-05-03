package Parser;

import java.util.ArrayDeque;
import java.util.Deque;

public final class NestedParser {
    public static Node parse(String s, String delimiters) {
        if (delimiters.length() != 2) {
            throw new IllegalArgumentException();
        }
        char left = delimiters.charAt(0), right = delimiters.charAt(1);
        Node root = new Node();
        Deque<Node> st = new ArrayDeque<>();
        st.addLast(root);
        StringBuilder value = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            Node node = st.peekLast();
            char c = s.charAt(i);
            if (c == left) {
                Node child = new Node();
                node.addChild(child);
                st.addLast(child);
                value = new StringBuilder();
            }
            else if (c == right) {
                if (node.isLeaf()) {
                    node.setValue(value.toString());
                    value = new StringBuilder();
                }
                st.pollLast();
            }
            else {
                value.append(c);
            }
        }
        return root;
    }
}
