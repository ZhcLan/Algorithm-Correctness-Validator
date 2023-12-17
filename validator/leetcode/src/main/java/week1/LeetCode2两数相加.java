package week1;

import controller.Active;
import model.config.ValidatorConfig;
import model.range.Range;
import model.sample.Argument;
import model.util.ListNode;
import model.util.type.IntegerPlus;


/**
 * <a href="https://leetcode.cn/problems/add-two-numbers/">LeetCode2两数相加</a>
 * <p>
 * 给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。
 * 请你将两个数相加，并以相同形式返回一个表示和的链表。
 * 你可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 * <p>
 * 每个链表中的节点数在范围 [1, 100] 内
 * <p>
 * 0 <= Node.val <= 9
 * <p>
 * 题目数据保证列表表示的数字不含前导零
 */
public class LeetCode2两数相加 {

    public ListNode<IntegerPlus> validator_addTwoNumbers(ListNode<IntegerPlus> l1, ListNode<IntegerPlus> l2) {
        // 由于 l1.size() ≥ 0 && l2.size ≥ 0,所以不用特殊判断
        ListNode<IntegerPlus> dummy = new ListNode<>(new IntegerPlus(-1));
        int t = 0;// 进位
        ListNode<IntegerPlus> tail = dummy;
        while (l1 != null || l2 != null || t != 0) {
            if (l1 != null) {
                t += l1.val.value;
                l1 = l1.next;
            }

            if (l2 != null) {
                t += l2.val.value;
                l2 = l2.next;
            }

            tail = tail.next = new ListNode<>(new IntegerPlus(t % 10));
            t /= 10;
        }

        return dummy.next;
    }

    public ListNode<IntegerPlus> compare_addTwoNumbers(ListNode<IntegerPlus> l1, ListNode<IntegerPlus> l2) {

        ListNode<IntegerPlus> pre = new ListNode<IntegerPlus>(new IntegerPlus(0));
        ListNode<IntegerPlus> cur = pre;
        int carry = 0;
        while (l1 != null || l2 != null) {
            int x = l1 == null ? 0 : l1.val.value;
            int y = l2 == null ? 0 : l2.val.value;
            int sum = x + y + carry;

            carry = sum / 10;
            sum = sum % 10;
            cur.next = new ListNode<IntegerPlus>(new IntegerPlus(sum));

            cur = cur.next;
            if (l1 != null)
                l1 = l1.next;
            if (l2 != null)
                l2 = l2.next;
        }
        if (carry == 1) {
            cur.next = new ListNode<IntegerPlus>(new IntegerPlus(carry));
        }

        return pre.next;
    }


    /**
     * 如果是链表的题目,那么最小节点的数量请加上1
     * 生产随机链表时,虚拟头结点要占一个长度
     */
    public static void main(String[] args) {
        ValidatorConfig config = new ValidatorConfig(10_0000, LeetCode2两数相加.class,
                "validator_addTwoNumbers", "compare_addTwoNumbers");

        Argument argument1 = new Argument(
                new Range[]{
                        new Range(2, 101),// + 1
                        new Range(-1, -1)
                },

                new Range[]{
                        new Range(-1, -1),
                        new Range(0, 9)
                }
        );

        Argument argument2 = new Argument(
                new Range[]{
                        new Range(2, 101),// + 1
                        new Range(-1, -1)
                },

                new Range[]{
                        new Range(-1, -1),
                        new Range(0, 9)
                }
        );

        new Active().active(config, argument1, argument2);
    }
}
