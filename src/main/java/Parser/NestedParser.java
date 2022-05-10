package Parser;

import java.util.ArrayDeque;
import java.util.Deque;

public final class NestedParser {
    public static Node parse(String s) {
        return parse(s, '[', ']', ',');
    }

    public static Node parse(String s, char left, char right, char split) {
        Node root = new Node();
        Deque<Node> st = new ArrayDeque<>();
        st.addLast(root);
        StringBuilder value = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            Node node = st.peekLast();
            char c = s.charAt(i);
            if (c == left) {
                st.addLast(new Node());
                value = new StringBuilder();
            }
            else if (c == right) {
                if (node.isLeaf()) {
                    node.setValue(Integer.parseInt(value.toString().trim()));
                    value = new StringBuilder();
                }
                st.pollLast();
                st.peekLast().addChild(node);
            }
            else if (c == split) {
                if (node.isLeaf()) {
                    node.setValue(Integer.parseInt(value.toString().trim()));
                    value = new StringBuilder();
                }
                st.pollLast();
                st.peekLast().addChild(node);
                st.addLast(new Node());
            }
            else {
                value.append(c);
            }
        }
        return root;
    }
}
