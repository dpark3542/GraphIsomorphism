package Parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Node implements Iterable<Node> {
    private int value;
    private final List<Node> children;

    Node() {
        this.value = 0;
        children = new ArrayList<>();
    }

    public boolean isLeaf() {
        return children.isEmpty();
    }

    void setValue(int value) {
        this.value = value;
    }

    void addChild(Node child) {
        children.add(child);
    }

    public int getValue() {
        return value;
    }

    public int numChildren() {
        return children.size();
    }

    @Override
    public Iterator<Node> iterator() {
        return children.iterator();
    }

    @Override
    public String toString() {
        if (isLeaf()) {
            return Long.toString(value);
        }
        else {
            StringBuilder sb = new StringBuilder();
            sb.append('[');
            int n = children.size();
            for (int i = 0; i < n - 1; i++) {
                sb.append(children.get(i).toString());
                sb.append(',');
            }
            sb.append(children.get(n - 1));
            sb.append(']');
            return sb.toString();
        }
    }
}
