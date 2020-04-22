package whling.knowledge.alogrithm.tree;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class MirrorTree {


    public static void main(String[] args) {


        TreeNode<Integer> root = buildTree();
        /**
         * 判断一颗是否对称
         */
        System.out.println(isSiminar(root, getMirrorTree(root)));

        System.out.println("===========");

        System.out.println(isSiminar2(root.left, root.right));

        mirrorTree(root);

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        TreeNode temp;

        List<TreeNode> list = new LinkedList<>();

        while (!stack.isEmpty()) {
            temp = stack.pop();
            list.add(temp);
            if (temp.right != null) {
                stack.push(temp.right);
            }
            if (temp.left != null) {
                stack.push(temp.left);
            }
        }
        list.forEach(treeNode -> System.out.println(treeNode.data));
        System.out.println("===========");

        System.out.println("root:"+root.data);
        list = new LinkedList<>();
        stack.clear();
//        stack.push(root);
        /**
         * 树的遍历，中序
         */
        TreeNode p = root;
        while (p != null || !stack.isEmpty()) {

            if (p != null) {
                stack.push(p);
                p = p.left;
            } else {
                p = stack.pop();
//                list.add(p);
                System.out.println(p.data);
                p = p.right;
            }
        }

        list.forEach(treeNode -> System.out.println(treeNode.data));
    }

    private static TreeNode<Integer> buildTree() {

        TreeNode<Integer> t1 = new TreeNode<>(2);
        TreeNode<Integer> t2 = new TreeNode<>(3);
        TreeNode<Integer> t3 = new TreeNode<>(3);
        TreeNode<Integer> t4 = new TreeNode<>(4);
        TreeNode<Integer> t5 = new TreeNode<>(4);

        t1.left = t2;
        t1.right = t3;
        t2.left = t4;
        t3.left = t5;

        return t1;
    }

    static boolean isSiminar(TreeNode node1, TreeNode node2) {
        if (node1 == null && node2 == null) {
            return true;
        }
        if (node1 == null || node2 == null) {
            return false;
        }

        if (node1.data == node2.data) {
            return isSiminar(node1.left, node2.left) && isSiminar(node1.right, node2.right);
        }
        return false;

    }

    static boolean isSiminar2(TreeNode node1, TreeNode node2) {
        if (node1 == null && node2 == null) {
            return true;
        }
        if (node1 == null || node2 == null) {
            return false;
        }

        if (node1.data == node2.data) {
            return isSiminar2(node1.left, node2.right) && isSiminar(node1.right, node2.left);
        }
        return false;

    }

    /**
     * 将一颗二叉树对称
     */
    static void mirrorTree(TreeNode root) {
        if (root == null) {
            return;
        }
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;

        mirrorTree(root.left);
        mirrorTree(root.right);
    }

    /**
     * 获取一颗对称二叉树
     */
    static TreeNode getMirrorTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode<Object> node = new TreeNode<>(root.data);
        node.left = getMirrorTree(root.right);
        node.right = getMirrorTree(root.left);

        return node;
    }
}
