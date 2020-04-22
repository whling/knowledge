package whling.knowledge.datastructure;

public class Node<T> {

    public Node next;
    public T data;

    public Node() {
    }

    public Node(T data) {
        this.data = data;
    }


    public static Node<String> buildLinkedList(){
        Node n1 = new Node("1");
        Node n2 = new Node("2");
        Node n3 = new Node("3");
        Node n4 = new Node("4");


        n1.next = n2;
        n2.next = n3;
        n3.next = n4;

        return n1;
    }
}
