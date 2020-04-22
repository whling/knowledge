package whling.knowledge;


import java.util.LinkedList;
import java.util.Queue;

public class T {


    static class Node {
        Node left;
        Node right;
        Object val;

        public Node(Object val) {
            this.val = val;
        }
    }

    public static void main(String[] args) {

        Node root = buildNode();

        Queue<Node> list = new LinkedList();

        LinkedList<Node> nodes = new LinkedList<>();

        ((LinkedList) list).push(root);

        while (!list.isEmpty()) {

            Node node = (Node) ((LinkedList) list).pop();

            nodes.add(node);
            if (node.left != null) {
                ((LinkedList<Node>) list).push(node.left);
            }

            if (node.right != null) {
                ((LinkedList<Node>) list).push(node.right);

            }
        }

        for (Node node : nodes) {
            System.out.println(node.val);
        }
    }

    private static Node buildNode() {

        Node n1 = new Node("1");
        Node n2 = new Node("2");
        Node n3 = new Node("3");


        n1.left = n2;
        n1.right = n3;

        return n1;

    }
}
