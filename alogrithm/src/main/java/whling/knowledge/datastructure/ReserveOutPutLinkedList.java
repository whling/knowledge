package whling.knowledge.datastructure;

import java.util.ArrayList;
import java.util.Stack;

public class ReserveOutPutLinkedList {

    public static void main(String[] args) {

        ArrayList<String> list = new ArrayList<>();

        Node node = Node.buildLinkedList();

        stackResovle(node, list);

        list.forEach(System.out::println);


        list.clear();
        System.out.println("==========");


        cycle(node,list);
        list.forEach(System.out::println);

    }


    public static void cycle(Node node, ArrayList<String> list) {

        if (node != null) {

            cycle(node.next, list);
            list.add((String) node.data);

        }


    }


    /**
     * 栈实现
     * @param node
     * @param list
     */
    public static void stackResovle(Node node, ArrayList<String> list) {

        Stack<Node> stack = new Stack<>();

        while (node != null) {
            stack.push(node);
            node = node.next;
        }

        while (!stack.isEmpty()) {
            list.add((String) stack.pop().data);
        }
    }

}
