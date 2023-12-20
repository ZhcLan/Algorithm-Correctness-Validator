package model.util;

import controller.Active;
import model.config.ValidatorConfig;
import model.range.Range;
import model.sample.Argument;
import model.util.type.IntegerPlus;

import java.util.*;

public class TreeNode<E> implements Collection<E> {
    public E val;

    public TreeNode<E> left;

    public TreeNode<E> right;

    public TreeNode() {
    }

    TreeNode(E val) {
        this.val = val;
    }

    TreeNode(E val, TreeNode<E> left, TreeNode<E> right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    @Override
    public int size() {
        if (this.val == null) {
            return 0;
        }
        return size(this);
    }

    public int size(TreeNode<E> root) {
        if (root == null) {
            return 0;
        }
        return 1 + size(root.left) + size(root.right);
    }


    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean contains(Object o) {
        if (this == null) {
            return false;
        }
        if (val.equals(o)) {
            return true;
        } else {
            return left != null && left.contains(o) || right != null && right.contains(o);
        }
    }

    /**
     * Please do not use this invalid method
     */
    @Override
    public Iterator<E> iterator() {
        return null;
    }

    /**
     * 返回先序遍历的结果
     *
     * @return
     */
    @Override
    public Object[] toArray() {
        List<Object> list = new ArrayList<>();
        preOrderTraversal(this, list);
        return list.toArray();
    }

    private void preOrderTraversal(TreeNode<E> node, List<Object> list) {
        if (node == null) {
            return;
        }
        list.add(node.val);
        preOrderTraversal(node.left, list);
        preOrderTraversal(node.right, list);
    }


    @Override
    public Object[] toArray(Object[] a) {
        if (a.length < this.size())
            a = (E[]) java.lang.reflect.Array.newInstance(
                    a.getClass().getComponentType(), this.size());
        int i = 0;
        Object[] result = a;
        List<Object> list = new ArrayList<>();
        preOrderTraversal(this, list);
        for (int j = 0; j < list.size(); j++) {
            result[j] = list.get(j);
        }

        if (a.length > this.size())
            a[this.size()] = null;
        return a;
    }

    @Override
    public boolean add(E e) {
        if (e == null) {
            throw new IllegalArgumentException("请不要视图插入空值!");
        }
        insertRandomNode(this, e);
        return true;
    }

    /**
     * Please do not use this invalid method
     */
    @Override
    public boolean remove(Object o) {
        return false;
    }

    /**
     * Please do not use this invalid method
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }


    @Override
    public boolean addAll(Collection<? extends E> c) {
        TreeNode<E> root = this;
        for (E e : c) {
            if (root.val == null) {
                root.val = e;
            } else {
                root = insertRandomNode(root, e);
            }
        }
        return true;
    }

    /**
     * Please do not use this invalid method
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }


    /**
     * Please do not use this invalid method
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }


    @Override
    public void clear() {
        this.val = null;
        this.left = null;
        this.right = null;
    }

    public Object[] buildDoubleBinaryTree(List<Object> list1, List<Object> list2) {
        TreeNode<E>[] roots = new TreeNode[2];
        TreeNode tree1 = new TreeNode();
        TreeNode tree2 = new TreeNode();

        roots[0] = tree1;
        roots[1] = tree2;

        TreeNode ret1 = new TreeNode();
        TreeNode ret2 = new TreeNode();
        for (int i = 0; i < list1.size(); i++) {
            if (tree1.val == null) {
                tree1.val = list1.get(i);
                tree2.val = list2.get(i);
            } else {
                roots = insertDoubleRandomNode(roots, list1.get(i), list2.get(i));
            }
        }

        return roots;
    }

    public TreeNode<E>[] insertDoubleRandomNode(TreeNode<E>[] roots, Object value1, Object value2) {
        if (roots[0] == null) {
            return new TreeNode[]{new TreeNode<>(value1), new TreeNode<>(value2)};
        }

        double random = Math.random();
        if (random < 0.6) {
            TreeNode[] ret = insertDoubleRandomNode(new TreeNode[]{roots[0].left, roots[1].left}, value1, value2);
            roots[0].left = ret[0];
            roots[1].left = ret[1];
        } else {
            TreeNode[] ret = insertDoubleRandomNode(new TreeNode[]{roots[0].right, roots[1].right}, value1, value2);
            roots[0].right = ret[0];
            roots[1].right = ret[1];
        }
        return roots;
    }


    public TreeNode<E> insertRandomNode(TreeNode<E> root, E val) {
        if (root == null) {
            return new TreeNode<>(val);
        }

        double random = Math.random();
        if (random < 0.6) {
            root.left = insertRandomNode(root.left, val);
        } else {
            root.right = insertRandomNode(root.right, val);
        }
        return root;
    }

    public boolean validator_isSameTree(TreeNode<IntegerPlus> p, TreeNode<IntegerPlus> q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null) {
            return false;
        }
        if (!p.val.equals(q.val)) {
            return false;
        }

        return validator_isSameTree(p.left, q.left) && validator_isSameTree(p.right, q.right);
    }

    public boolean compare_isSameTree(TreeNode<IntegerPlus> p, TreeNode<IntegerPlus> q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null) {
            return false;
        }
        Queue<TreeNode<IntegerPlus>> queue1 = new LinkedList<>();
        Queue<TreeNode<IntegerPlus>> queue2 = new LinkedList<>();
        queue1.offer(p);
        queue2.offer(q);
        while (!queue1.isEmpty() && !queue2.isEmpty()) {
            TreeNode<IntegerPlus> node1 = queue1.poll();
            TreeNode<IntegerPlus> node2 = queue2.poll();
            if (!node1.val.equals(node2.val)) {
                return false;
            }
            if ((node1.left != null) ^ (node2.left != null)) {
                return false;
            }
            if ((node1.right != null) ^ (node2.right != null)) {
                return false;
            }
            if (node1.left != null) {
                queue1.offer(node1.left);
            }
            if (node1.right != null) {
                queue1.offer(node1.right);
            }
            if (node2.left != null) {
                queue2.offer(node2.left);
            }
            if (node2.right != null) {
                queue2.offer(node2.right);
            }
        }
        return queue1.isEmpty() && queue2.isEmpty();
    }

    /**
     * When using ObjectPlus, equals is to compare addresses and values
     * However, the equals of its subclasses only compare values,
     * you can directly use the two Plus types to compare equals
     */
    // for test!
    public static void main(String[] args) {
        ValidatorConfig config = new ValidatorConfig(10_0000, TreeNode.class,
                "validator_isSameTree", "compare_isSameTree");

        Argument argument1 = new Argument(
                new Range[]{
                        new Range(0, 10),
                        new Range(-1, -1)
                },
                new Range[]{
                        new Range(-1, -1),
                        new Range(-10, 10)
                }
        );

        Argument argument2 = new Argument(
                new Range[]{
                        new Range(0, 10),
                        new Range(-1, -1)
                },
                new Range[]{
                        new Range(-1, -1),
                        new Range(-10, 10)
                }
        );

        // 为了创造出出现相同的树的条件,将 节点的数值范围和节点数写的小一些
        new Active().active(config, argument1, argument2);
    }

}

