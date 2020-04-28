package whling.knowledge;

public class LRUCache {

    int size;

    int count;

    public LRUCache(int size) {
        this.size = size;
    }

    static class Node {

        Object data;
        Node prev;
        Node next;

        public Node(Object data) {
            this.data = data;
        }
    }


    Node head = null;
    Node tail = null;

    public void put(Object data) {

        Node node = new Node(data);

        if (tail == null) {
            tail = node;
        }

        node.next = head;
        head = node;
        count++;

        if (count > size) {
            tail = tail.prev;
            tail.next = null;
            count--;
        }
    }


    public static void main(String[] args) {
        int n = 5;
        boolean b =( n & (n - 1) )== 0;
        System.out.println(b);
    }

}
