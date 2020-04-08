package whling.knowledge.alogrithm.tree;

import java.util.*;

/**
 * 树的广度优先和深度优先遍历
 */
public class traverse {

    static class Node<T> {
        T data;
        Node left = null;
        Node right = null;

        public Node(T data) {
            this.data = data;
        }
    }

    public static void main(String[] args) {
        Node<String> root = new Node<>("root");

        buildTree(root);

        List<String> list;
        if ((list = dfs1(root)) != null) {
            System.out.println("=====深度优先遍历，非递归======");
            list.forEach(System.out::println);
            System.out.println("==============");
            list.clear();
        }


        if ((list = dfs2(root)) != null) {
            System.out.println("=====深度优先遍历，递归======");
            list.forEach(System.out::println);
            System.out.println("==============");
            list.clear();
        }

        if ((list =  bfs(root)) != null) {
            System.out.println("=====广度优先遍历======");
            list.forEach(System.out::println);
            System.out.println("==============");
            list.clear();
        }

    }

    private static void buildTree(Node<String> root) {

        /**
         *                  root
         *          hello1           hello4
         *    hello3                        hello5
         *
         *         hello2
         *
         *
         */

        Node<String> n1 = new Node<>("hello1");
        Node<String> n2 = new Node<>("hello2");
        Node<String> n3 = new Node<>("hello3");
        Node<String> n4 = new Node<>("hello4");
        Node<String> n5 = new Node<>("hello5");

        root.left = n1;
        root.right = n4;

        n1.left = n3;

        n3.right = n2;

        n4.right = n5;
    }

    /**
     * breadth first search
     *
     * @param root
     * @return
     */
    private static LinkedList<String> bfs(Node<String> root) {
        LinkedList<String> list = new LinkedList<>();
        if (root != null) {
            Queue<Node<String>> queue = new LinkedList<>();
            queue.add(root);

            while (!queue.isEmpty()) {
                Node<String> node = queue.poll();
                list.add(node.data);

                if (node.left != null) {
                    queue.add(node.left);
                }

                if (node.right != null) {
                    queue.add(node.right);
                }
            }
        }
        return list;
    }

    /**
     * 深度优先遍历，递归
     *
     * @param root
     * @return
     */
    private static LinkedList<String> dfs2(Node<String> root) {
        LinkedList<String> list = new LinkedList<>();
        recursion(root, list);
        return list;
    }

    private static void recursion(Node<String> node, List<String> list) {
        if (node != null) {
            list.add(node.data);
            recursion(node.left, list);
            recursion(node.right, list);
        }
    }

    /**
     * 深度优先遍历，非递归
     *
     * @param root
     */
    private static LinkedList<String> dfs1(Node<String> root) {

        LinkedList<String> list = new LinkedList<>();
        if (root == null) {
            return list;
        }

        Stack<Node<String>> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            Node<String> node = stack.pop();
            list.add(node.data);

            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }

        return list;
    }


}
