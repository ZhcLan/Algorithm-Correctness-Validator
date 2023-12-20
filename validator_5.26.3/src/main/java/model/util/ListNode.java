package model.util;

import java.util.*;

/**
 * In order to accommodate the pairing number and the algorithm, the trade-off is made here.
 * <p>
 * In the beginning, the logarithm gets a singly linked list in the form of a virtual head node
 * <p>
 * The virtual head node will be discarded at the right time
 * <p>
 * It is important to note that your algorithm should not use or rely on this collection
 *
 * @param <E> The type of the linked list node element
 */
public class ListNode<E> implements Collection<E> {
    public E val;
    public ListNode<E> next;

    public ListNode() {
        // If the value of the node is empty, then the node is the virtual head node of the entire linked list
        this.val = null;
        this.next = null;
    }

    public ListNode(E val, ListNode<E> next) {
        this.val = val;
        this.next = next;
    }

    public ListNode(E val) {
        this.val = val;
    }

    @Override
    public int size() {
        int size = 0;
        ListNode<E> x = this;
        // 虚拟头结点不计入节点总数
        if (x.val == null) {
            x = x.next;
        }

        while (x != null) {
            size++;
            x = x.next;
        }

        return size;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public boolean contains(Object o) {
        // Null cannot be passed
        if (o == null) {
            throw new IllegalArgumentException("Do not attempt to pass in any null pointers to the ListNode");
        }
        ListNode<E> x = this;
        // 来到真实头节点
        if (x.val == null) {
            x = x.next;
        }

        while (x != null) {
            if (x.val.equals(o)) {
                return true;
            }
            x = x.next;
        }
        return false;
    }

    /**
     * Please do not use this method
     *
     * @return Invalid return value
     */
    @Override
    public Iterator iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        // Arrays hold elements, not nodes
        Object[] ret = new Object[this.size()];
        int cur = 0;
        ListNode<E> x = this;
        if (x.val == null) {
            x = x.next;
        }
        while (x != null) {
            ret[cur++] = x.val;
            x = x.next;
        }
        return ret;
    }

    /*Unchecked*/
    @Override
    public boolean add(Object o) {
        if (o == null) {
            throw new IllegalArgumentException("Do not attempt to pass in any null pointers to the ListNode");
        }

        ListNode<E> x = this;
        while (x.next != null) {
            x = x.next;
        }
        if (this.val == null && size() > 1) {
            this.val = this.next.val;
            this.next = this.next.next;
        }

        x.next = new ListNode<>((E) o, null);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (o == null) {
            throw new IllegalArgumentException("Do not attempt to pass in any null pointers to the ListNode");
        }
        ListNode<E> x = this;
        while (x.next != null) {
            if (x.next.val.equals(o)) {
                break;
            }
            x = x.next;
        }
        assert x.next != null;
        if (x.next.val.equals(o)) {
            x.next = x.next.next;
            return true;
        }
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E e : c) {
            add(e);
        }
        // At the right time,discard virtual head nodes!
        if (this.val == null && size() > 1) {
            this.val = this.next.val;
            this.next = this.next.next;
        }
        return true;
    }

    @Override
    public void clear() {
        this.next = null;
    }

    /**
     * Please do not use this method
     *
     * @param c collection containing elements to be retained in this collection
     * @return Invalid return value
     */
    @Override
    public boolean retainAll(Collection c) {
        return false;
    }


    @Override
    public boolean removeAll(Collection c) {
        for (Object o : c) {
            remove(o);
        }
        return false;
    }

    /**
     * Please do not use this method
     *
     * @param c collection to be checked for containment in this collection
     * @return 无效返回值
     */
    @Override
    public boolean containsAll(Collection c) {
        return false;
    }

    /**
     * unchecked!
     *
     * @param a the array into which the elements of this collection are to be
     *          stored, if it is big enough; otherwise, a new array of the same
     *          runtime type is allocated for this purpose.
     * @return a Array
     */
    @Override
    public Object[] toArray(Object[] a) {
        if (a.length < this.size())
            a = (E[]) java.lang.reflect.Array.newInstance(
                    a.getClass().getComponentType(), this.size());
        int i = 0;
        Object[] result = a;
        ListNode<E> x = this;
        if (this.val == null) {
            x = x.next;
        }

        while (x != null) {
            result[i++] = x.val;
            x = x.next;
        }

        if (a.length > this.size())
            a[this.size()] = null;
        return a;
    }

    @Override
    public String toString() {
        StringBuilder appender = new StringBuilder();
        ListNode<E> x = this;
        if (x.val == null) {
            x = x.next;
        }

        while (x != null) {
            appender.append(x.val);
            if (x.next != null) {
                appender.append(",");
            }
            x = x.next;
        }
        return appender.toString();
    }
}