package whling.knowledge;

import whling.knowledge.datastructure.DNode;

public class LRUCache {
    int size;
    int count = 0;

    DNode dummyHead = new DNode<>(), dummyTail = new DNode<>();

    public LRUCache(int size) {
        this.size = size;
    }

    public void put(Object obj) {

        DNode<Object> node = new DNode<>(obj);

        if (count == 0) {
            init(node);
            count++;
            return;
        }

        if (count >= size) {
            DNode tail = dummyTail.prev;
            tail.prev.next = dummyTail;
            dummyTail.prev = tail.prev;
            tail.next = null;
            tail.prev = null;
            count--;
        }
        DNode next = dummyHead.next;
        node.next = next;
        next.prev = node;
        node.prev = dummyHead;
        dummyHead.next = node;
        count++;
    }

    public void toPrint() {
        DNode temp = dummyHead.next;
        while (temp != null && temp.object != null) {
            System.out.print(temp.object + "\t");
            temp = temp.next;
        }
        System.out.println("");
    }

    private void init(DNode<Object> node) {
        dummyHead.next = node;
        dummyTail.prev = node;

        node.prev = dummyHead;
        node.next = dummyTail;
    }

    public static void main(String[] args) {
        LRUCache lruCache = new LRUCache(3);
        lruCache.put(1);
        lruCache.put(2);
        lruCache.put(3);
        lruCache.toPrint();
        lruCache.put(4);

        lruCache.toPrint();

    }
}
