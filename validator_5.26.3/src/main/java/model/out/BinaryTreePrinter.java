package model.out;


import model.util.TreeNode;

import java.util.*;

/**
 * 来自于 gitee 的作品,作者 <a href="https://gitee.com/myd123">...</a>
 * <a href="https://gitee.com/myd123/data-struct/blob/master/balanceTree/src/main/java/R20220707/RBT.java">...</a>
 *
 * @author myd
 * @date 2022/7/10  15:13
 */

public class BinaryTreePrinter {
    private static List<TreeNode> mid = new ArrayList<>();

    private static void midOrder(TreeNode node) {
        if (node == null) return;
        midOrder(node.left);
        mid.add(node);
        midOrder(node.right);
    }

    private static Map<TreeNode, Integer> map = new HashMap<>();

    private static void init(TreeNode root) {
        if (root == null) return;
        midOrder(root);
        for (int i = 0; i < mid.size(); i++) {
            map.put(mid.get(i), i);
        }
    }


    private static void printLevel(List<TreeNode> nodes) {
        StringBuilder VLine = new StringBuilder();
        StringBuilder dataLine = new StringBuilder();
        StringBuilder line = new StringBuilder();
        int lastNodeIndex = 0;
        int lastRightIndex = 0;
        for (TreeNode node : nodes) {
            int x = map.get(node);
            String addEmpty = getEmpty(x - lastNodeIndex);
            lastNodeIndex = x;
            VLine.append(addEmpty).append("|");//竖线拼接
            //数字拼接
            dataLine.append(addEmpty).append(node.val);

            TreeNode left = node.left;
            TreeNode right = node.right;
            String leftLine = null;
            String rightLine = null;
            int leftIndex = -1;
            int rightIndex = -1;
            if (left != null) {
                leftIndex = map.get(left);
                leftLine = getLineToSon(x - leftIndex);
            }
            if (right != null) {
                rightIndex = map.get(right);
                rightLine = getLineToSon(rightIndex - x);
            }
            String curLine = (leftLine == null ? "" : leftLine) + "|" + (rightLine == null ? "" : rightLine);
            if (leftLine == null && rightLine == null) curLine = "";
            //线段之间的间隔
            int dif = (leftIndex == -1 ? x : leftIndex) - lastRightIndex;
            String difEmpty = getEmpty(dif);
            line.append(difEmpty).append(curLine);//拼接线段
            lastRightIndex = rightIndex == -1 ? x : rightIndex;
        }
        System.out.println(VLine + "\n" + dataLine + "\n" + line);
    }


    private static String getEmpty(int x) {
        StringBuilder empty = new StringBuilder();
        for (int i = 0; i < x; i++) {
            empty.append("\t");
        }
        return empty.toString();
    }

    private static void treePrint(List<TreeNode> nodes) {
        if (nodes.isEmpty()) return;
        printLevel(nodes);
        List<TreeNode> children = new ArrayList<>();
        for (TreeNode node : nodes) {
            if (node.left != null) children.add(node.left);
            if (node.right != null) children.add(node.right);
        }
        treePrint(children);
    }

    public static void treePrint(TreeNode root) {
        init(root);
        List<TreeNode> nodes = new ArrayList<>();
        nodes.add(root);
        System.out.println();
        treePrint(nodes);
        map = new HashMap<>();
        mid = new ArrayList<>();
    }

    private static String getLineToSon(int end) {
        StringBuilder line = new StringBuilder();
        if (end == 0) return line.toString();
        for (int i = 0; i < end; i++) {
            line.append("____");
        }
        return line.toString();
    }
}