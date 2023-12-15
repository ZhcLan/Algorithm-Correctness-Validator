package controller.usage.nowcoder_review_top101.linkedlist;

import controller.Active;
import model.config.ValidatorConfig;
import model.range.Range;
import model.sample.Argument;
import model.util.type.IntegerPlus;
import model.util.ListNode;

import java.util.ArrayList;

/**
 * @BelongsProject: validator
 * @BelongsPackage: usage.now coder_review_top101.linked list
 * @Author: zhc
 * @Description: 传送门: <a href="https://www.nowcoder.com/practice/75e878df47f24fdc9dc3e400ec6058ca?tpId=295&tqId=23286&ru=/exam/oj&qru=/ta/format-top101/question-ranking&sourceUrl=%2Fexam%2Foj">...</a>
 * <p>
 * 给定一个单链表的头结点pHead(该头节点是有值的，比如在下图，它的val是1)，长度为n，反转该链表后，返回新链表的表头。
 * <p>
 * 数据范围：
 * 0≤n≤1000
 * @Version: 1.0
 */
public class BM1_ReverseLinkedList {
    // 原地反转 validator 方法
    public ListNode<IntegerPlus> validator_ReverseList(ListNode<IntegerPlus> head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode<IntegerPlus> last = null;
        while (head != null) {
            // 记录写一个位置
            ListNode<IntegerPlus> next = head.next;
            head.next = last;
            last = head;
            head = next;
        }

        return last;
    }

    // 使用容器 compare 方法  -> 对于该方法,只需要保证大概率正确即可,无需考虑性能
    public ListNode<IntegerPlus> compare_ReverseList(ListNode<IntegerPlus> head) {
        // 边界(空链表 or 只有一个节点的链表 不用反转直接返回)
        if (head == null || head.next == null) {
            return head;
        }

        // write code here
        ArrayList<ListNode<IntegerPlus>> list = new ArrayList<>();
        for (ListNode<IntegerPlus> p = head; p != null; p = p.next) {
            list.add(p);
        }

        for (int i = list.size() - 1; i > 0; i--) {
            list.get(i).next = list.get(i - 1);
        }

        list.get(0).next = null;
        return list.get(list.size() - 1);
    }

    // If the progress bar stops, there's a good chance that your algorithm is in an endless loop
    public static void main(String[] args) {
        // 使用对数器 validator 进行验证

        // config
        ValidatorConfig config = new ValidatorConfig(10_0000, BM1_ReverseLinkedList.class,
                "validator_ReverseList", "compare_ReverseList");

        // argument
        Argument argument = new Argument(
                new Range[]{
                        new Range(0, 100),          // 按照题目要求确定范围[0,100]范围内产生节点数量
                        new Range(-1, -1)},         // 占位

                new Range[]{
                        new Range(-1, -1),          // 占位
                        new Range(-10000, 10000)    // 题目没有明确给出,自己确定
                }
        );
        // 激活对数器
        new Active().active(config, argument);
    }
}
