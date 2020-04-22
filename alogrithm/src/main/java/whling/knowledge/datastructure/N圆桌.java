package whling.knowledge.datastructure;

public class N圆桌 {

    static class Node {
        String data;
        Node prev;
        Node next;

        public Node(String data) {
            this.data = data;
        }
    }

    public static void main(String[] args) {
        Node n = buildNode();
        Node node = p1(n);
        String data = node.data;
        System.out.println("=======");
        System.out.println(data);

        System.out.println("=======");
        System.out.println(p2(n).data);
    }


    /**
     * 递归
     *
     * @param temp
     * @return
     */
    public static Node p1(Node temp) {
        if (temp == temp.next) {
            return temp;
        }
        Node n = temp.next.next;
        temp.prev.next = temp.next;
        temp.next.prev = temp.prev;
        return p1(n);
    }

    /**
     * 迭代
     *
     * @param n
     * @return
     */
    public static Node p2(Node n) {
        Node temp = n;
        while (temp != temp.next) {
            Node nn = temp.next.next;
            temp.prev.next = temp.next;
            temp.next.prev = temp.prev;

            temp.next = null;
            temp.prev = null;

            temp = nn;

        }
        return temp;
    }


    private static Node buildNode() {
        Node n1 = new Node("1");
        Node n2 = new Node("2");
        Node n3 = new Node("3");
        Node n4 = new Node("4");
        Node n5 = new Node("5");
        Node n6 = new Node("6");
        Node n10 = new Node("10");

        n1.prev = n10;
        n1.next = n2;

        n2.prev = n1;
        n2.next = n3;

        n3.prev = n2;
        n3.next = n4;

        n4.prev = n3;
        n4.next = n5;

        n5.prev = n4;
        n5.next = n6;

        n6.prev = n5;
        n6.next = n10;

        n10.prev = n6;
        n10.next = n1;
        return n1;
    }

}
