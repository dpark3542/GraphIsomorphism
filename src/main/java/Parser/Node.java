package Parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Node {
    private final String value;
    private final List<Node> children;

    Node(String value) {
        this.value = value;
        children = new ArrayList<>();
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
}
