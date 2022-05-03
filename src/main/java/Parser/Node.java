package Parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Node {
    private String value;
    private final List<Node> children;

    Node() {
        this.value = null;
        children = new ArrayList<>();
    }

    public boolean isLeaf() {
        return children.isEmpty();
    }

    void setValue(String value) {
        this.value = value;
    }

    void addChild(Node child) {
        children.add(child);
    }

    public String getValue() {
        return value;
    }

    public Iterator<Node> getChildren() {
        return children.iterator();
    }

    @Override
    public String toString() {
        if (isLeaf()) {
            return "[" + value + "]";
        }
        else {
            StringBuilder sb = new StringBuilder();
            sb.append('[');
            int n = children.size();
            for (int i = 0; i < n - 1; i++) {
                sb.append(children.get(i).toString());
                sb.append(", ");
            }
            sb.append(children.get(n - 1));
            sb.append(']');
            return sb.toString();
        }
    }
}
