package Parser;

import GroupTheory.Structs.Domain;
import GroupTheory.Structs.ExplicitDomain;
import GroupTheory.Structs.Tuple;

import java.util.*;

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
                    node.setValue(Long.parseLong(value.toString().trim()));
                    value = new StringBuilder();
                }
                st.pollLast();
                st.peekLast().addChild(node);
            }
            else if (c == split) {
                if (node.isLeaf()) {
                    node.setValue(Long.parseLong(value.toString().trim()));
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

    public static Domain parseDomain(Node node) {
        List<Tuple> a = new ArrayList<>();

        for (Node child : node) {
            List<Integer> tuple = new ArrayList<>();
            for (Node x : child) {
                tuple.add((int) x.getValue());
            }
            a.add(new Tuple(tuple));
        }

        return new ExplicitDomain(a);
    }
}
