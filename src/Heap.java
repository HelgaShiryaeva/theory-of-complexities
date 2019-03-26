import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Heap {
    private Node min;
    private int no_nodes;
    private Hashtable<Integer, Node> backDoor = new Hashtable<>();

    void insertNode(Node x, int data) {
        x.data = data;
        if (min != null) {
            x.left = min;
            x.right = min.right;
            min.right = x;
            x.right.left = x;
            if (data < min.data) {
                min = x;
            }
        } else {
            min = x;
        }
        backDoor.put(x.id_node, x);
        no_nodes++;
    }

    private void link(Node x, Node y) {
        y.left.right = y.right;
        y.right.left = y.left;
        y.parent = x;
        if (x.child == null) {
            x.child = y;
            y.right = y;
            y.left = y;
        } else {
            y.left = x.child;
            y.right = x.child.right;
            x.child.right = y;
            y.right.left = y;
        }
        x.degree++;
        y.mark = false;
    }

    private void consolidate() {
        int maxDegree = ((int) Math.floor(Math.log(no_nodes)
                * (1.0 / Math.log((1.0 + Math.sqrt(5.0)) / 2.0)))) + 1;
        List<Node> array = new ArrayList<>(maxDegree);
        for (int i = 0; i < maxDegree; i++) {
            array.add(null);
        }
        int numRoots = 0;
        Node x = min;
        if (x != null) {
            numRoots++;
            x = x.right;
            while (x != min) {
                numRoots++;
                x = x.right;
            }
        }

        while (numRoots > 0) {
            int d = x.degree;
            Node next = x.right;
            for (;;) {
                Node y = array.get(d);
                if (y == null) {
                    break;
                }

                if (x.data > y.data) {
                    Node temp = y;
                    y = x;
                    x = temp;
                }
                link(x, y);
                array.set(d, null);
                d++;
            }
            array.set(d, x);
            x = next;
            numRoots--;
        }
        min = null;

        for (int i = 0; i < maxDegree; i++) {
            Node y = array.get(i);
            if (y == null) {
                continue;
            }
            if (min != null) {

                y.left.right = y.right;
                y.right.left = y.left;
                y.left = min;
                y.right = min.right;
                min.right = y;
                y.right.left = y;
                if (y.data < min.data) {
                    min = y;
                }
            } else {
                min = y;
            }
            int k = 0;
            for (Node n = min; k < no_nodes; k++) {
                n = n.right;
            }
        }
    }

    private void cut(Node x, Node y) {
        x.left.right = x.right;
        x.right.left = x.left;
        y.degree--;
        if (y.child == x) {
            y.child = x.right;
        }
        if (y.degree == 0) {
            y.child = null;
        }
        x.left = min;
        x.right = min.right;
        min.right = x;
        x.right.left = x;
        x.parent = null;
        x.mark = false;
    }

    private void cascadeCut(Node y) {
        Node x = y.parent;
        if (x != null) {
            if (!y.mark) {
                y.mark = true;
            } else {
                cut(y, x);
                cascadeCut(x);
            }
        }
    }

   Node removeMin() {
        Node m = min;
        if (m != null) {
            int numChild = m.degree;
            if (m.child != null) {
                Node v = m.child;
                Node RightPtr;
                while (numChild > 0) {
                    RightPtr = v.right;
                    v.left.right = v.right;
                    v.right.left = v.left;
                    v.left = min;
                    v.right = min.right;
                    min.right = v;
                    v.right.left = v;
                    v.parent = null;
                    v = RightPtr;
                    numChild--;
                }
            }
            m.left.right = m.right;
            m.right.left = m.left;
            if (m == m.right) {
                min = null;
            } else {
                min = m.right;
                consolidate();
            }
            no_nodes--;
        }
        if (m != null) {
            backDoor.remove(m.id_node);
        }
        return m;
    }
    void decreaseKey(Node x, int newKey) {
        if (newKey > x.data) {
            return;
        }
        x.data = newKey;
        Node y = x.parent;
        if ((y != null) && (x.data < y.data)) {
            cut(x, y);
            cascadeCut(y);
        }
        if (x.data < min.data) {
            min = x;
        }
    }

   boolean isPresent(int nodeId) {
        return backDoor.containsKey(nodeId);
    }

   Node getNode(int nodeId) {
        if (isPresent(nodeId)) {
            return backDoor.get(nodeId);
        }
        return null;
    }
}