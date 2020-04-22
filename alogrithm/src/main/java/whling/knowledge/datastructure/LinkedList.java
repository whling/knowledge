package whling.knowledge.datastructure;

/**
 * 单链表
 */
public class LinkedList {
    static Node dummyHead;

    static class Node {
        Node next;
        Object data;

    }


    public static void main(String[] args) {

        /**
         * 链表反转
         * 1。递归
         * 2。迭代
         */

        Node head = new Node();
        Node n = new Node();
        head.data = "head";
        n.data = "n";
        dummyHead = head;
        head.next = n;
        n.next = null;



        /**
         * 插入
         */
        head = insertHead(new Node());
        System.out.println(head.data);
        head = insertTail(new Node());
        System.out.println(head.data);

        head = reverse1(head);
        System.out.println(head.data);
        head = reverse2(head);

        System.out.println(head.data);



    }

    private static Node insertTail(Node node) {


        if (dummyHead == null) {
            dummyHead = node;
        } else {
            Node cur = dummyHead;
            while (cur.next != null) {
                cur = cur.next;
            }
            cur.next = node;
            node.next = null;
        }
        return dummyHead;
    }

    private static Node insertHead(Node node) {
        if (dummyHead == null) {
            dummyHead = node;
        } else {
            node = dummyHead.next;
            dummyHead = node;
        }
        return dummyHead;
    }

    private static Node reverse2(Node head) {

        Node prev = null, next = null;
        while (head != null) {

            next = head.next;

            head.next = prev;
            prev = head;

            head = next;
        }
        return prev;


//        if (head == null) {
//            return null;
//        }
//
//        Node pre = head, cur = head.next, temp = null;
//        pre.next = null;
//        while (cur != null) {
//            temp = cur.next;
//            cur.next = pre;
//
//            pre = cur;
//            cur = temp;
//        }
//
//        return pre;
    }

    /**
     * 递归
     *
     * @param head
     * @return
     */
    private static Node reverse1(Node head) {
        if (head == null || head.next == null) {
            return head;
        }

        Node tempNode = reverse1(head.next);
        tempNode.next = head;
        head.next = null; //最终节点就是指向null

        return tempNode; //尾节点就是新的头节点
    }

}
