package model.util;

import java.util.Collection;
import java.util.Iterator;

public class TreeNode<E> implements Collection<E> {
    E val;
    TreeNode<E> left;
    TreeNode<E> right;

    TreeNode() {
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
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(E e) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }
}

