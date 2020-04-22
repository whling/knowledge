package whling.knowledge.datastructure;


import java.util.Arrays;
import java.util.List;

/**
 * 单链表，奇数位升序，偶数位降序，使整个链表有序
 */
public class LinkedList01 {


    public static void main(String[] args) {

        Node<Integer> node = buildNode();

        if (node == null) {
            return;
        }

        Node[] nodeArr = splitLinkedList(node);

        Node rNode = reverse(nodeArr[1]);

        Node head = combine(nodeArr[0], rNode);


        print(head);
    }

    private static Node<Integer> buildNode() {

        List<Node<Integer>> nodes = Arrays.asList(

                new Node<>(1),
                new Node<>(9),
                new Node<>(5),
                new Node<>(8),
                new Node<>(6),
                new Node<>(4),
                new Node<>(7));

        for (int i = 0; i < nodes.size() - 1; i++) {
            nodes.get(i).next = nodes.get(i + 1);
        }

        return nodes.get(0);
    }

    private static void print(Node head) {
        while (head != null) {
            System.out.println(head.data);
            head = head.next;
        }
    }

    private static Node combine(Node<Integer> node, Node<Integer> rNode) {

        if (node == null) {
            return rNode;
        }
        if (rNode == null) {
            return node;
        }

        Node head = null;

        if (node.data < rNode.data) {
            head = node;
            head.next = combine(node.next, rNode);

        } else {
            head = rNode;
            head.next = combine(node, rNode.next);
        }

        return head;
    }

    private static Node reverse(Node node) {

        if (node == null) {
            return null;
        }

        Node pre = null, next = null;

        while (node != null) {
            next = node.next;
            node.next = pre;
            pre = node;
            node = next;
        }


        return pre;
    }

    /**
     * 奇偶数拆分
     *
     * @param node
     * @return
     */
    private static Node[] splitLinkedList(Node node) {
        Node odd = null, even = null;
        Node oddCur = null, evenCur = null;

        int count = 1;
        while (node != null) {
            if (count % 2 == 1) {
                if (odd == null) {
                    odd = oddCur = node;
                } else {
                    oddCur.next = node;
                    oddCur = oddCur.next;
                }
            } else {
                if (even == null) {
                    even = evenCur = node;
                } else {
                    evenCur.next = node;
                    evenCur = evenCur.next;
                }
            }
            node = node.next;
            count++;
        }

        oddCur.next = null;
        evenCur.next = null;

        return new Node[]{odd, even};
    }


}
