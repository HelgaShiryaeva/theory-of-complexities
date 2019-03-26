class Node {
    int id_node;
    int degree;
    Node left;
    Node right;
    Node parent;
    Node child;
    boolean mark;
    int data;

    public Node() {
        this.data = Integer.MAX_VALUE;
        this.degree = 0;
        this.left = this;
        this.right = this;
        this.parent = null;
        this.child = null;
        this.mark = false;
        this.id_node = -1;
    }

    Node(int data) {
        this.id_node = data;
        this.data = Integer.MAX_VALUE;
        this.degree = 0;
        this.left = this;
        this.right = this;
        this.parent = null;
        this.child = null;
        this.mark = false;
    }
}