package whling.knowledge.alogrithm.sort;

import whling.knowledge.datastructure.Node;

/**
 * 链表的归并排序实现
 */
public class LinkedListMergeSort {

    public static void main(String[] args) {


        Node<Integer> head = buildNode();
        head.toPrint();

        splitLinkedList(head).toPrint();

    }

    private static Node<Integer> buildNode() {
        Node<Integer> n1 = new Node<>(4);
        Node<Integer> n2 = new Node<>(2);
        Node<Integer> n3 = new Node<>(3);

        n1.next = n2;
        n2.next = n3;

        return n1;
    }

    private static Node<Integer> splitLinkedList(Node<Integer> head) {

        if (head.next == null) {
            return head;
        }

        Node fast = head, slow = head, splitNode;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        splitNode = slow.next;
        slow.next = null;

        return mergeSort(splitLinkedList(head), splitLinkedList(splitNode));
    }

    private static Node<Integer> mergeSort(Node<Integer> head, Node<Integer> other) {
        Node<Integer> newNode = null, dummyHead = new Node<>(-1);

        while (head != null && other != null) {
            if (head.data > other.data) {
                dummyHead.next = other;
                other = other.next;
            } else {
                dummyHead.next = head;
                head = head.next;
            }

            if (newNode == null) {
                newNode = dummyHead.next;
            }
            dummyHead = dummyHead.next;
        }

        dummyHead.next = head != null ? head : other;

        return newNode;
    }

}
