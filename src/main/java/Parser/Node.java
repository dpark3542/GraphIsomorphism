package Parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Node implements Iterable<Node> {
    private long value;
    private final List<Node> children;

    Node() {
        this.value = 0;
        children = new ArrayList<>();
    }

    public boolean isLeaf() {
        return children.isEmpty();
    }

    void setValue(long value) {
        this.value = value;
    }

    void addChild(Node child) {
        children.add(child);
    }

    public long getValue() {
        return value;
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
