package controller.usage.nowcoder_review_top101.linkedlist;

import controller.Active;
import model.config.ValidatorConfig;
import model.range.Range;
import model.sample.Argument;
import model.util.type.IntegerPlus;
import model.util.ListNode;

/**
 * @BelongsProject: validator
 * @BelongsPackage: usage.now coder_review_top101.linked list
 * @Author: zhc
 * @Description: <a href="https://www.nowcoder.com/practice/b58434e200a648c589ca2063f1faf58c?tpId=295&tqId=654&ru=/exam/oj&qru=/ta/format-top101/question-ranking&sourceUrl=%2Fexam%2Foj">...</a>
 * 将一个节点数为 size 链表 m 位置到 n 位置之间的区间反转
 * <p>
 * 数据范围：  0≤n≤1000
 * @Version: 1.0
 */
public class BM2_ReverseBetween {
    // 原地反转 validator 方法
    public ListNode<IntegerPlus> validator_reverseBetween(ListNode<IntegerPlus> head, IntegerPlus m, IntegerPlus n) {
        // 控制 0 < n <= m <= size
        if (n.value > m.value) {
            int t = m.value;
            m.value = n.value;
            n.value = t;
        }

        return null;
    }

    // 使用容器 compare 方法  -> 对于该方法,只需要保证大概率正确即可,无需考虑性能
    public ListNode<IntegerPlus> compare_reverseBetween(ListNode<IntegerPlus> head, IntegerPlus m, IntegerPlus n) {
        // 控制 0 < n <= m <= size
        if (n.value > m.value) {
            int t = m.value;
            m.value = n.value;
            n.value = t;
        }

        return null;
    }

    // If the progress bar stops, there's a good chance that your algorithm is in an endless loop
    public static void main(String[] args) {
        // 使用对数器 validator 进行验证

        // config
        ValidatorConfig config = new ValidatorConfig(1_0000, BM2_ReverseBetween.class,
                "validator_reverseBetween", "compare_reverseBetween");

        // argument
        Argument argument0 = new Argument(
                new Range[]{
                        // 这里的范围是闭区间,题目给的是(0,1000]
                        new Range(1, 1000),       // 按照题目要求确定范围(1,100]范围内产生节点数量
                        new Range(-1, -1)},        // 占位
                new Range[]{
                        new Range(-1, -1),        // 占位
                        // ∣val∣ ≤ 1000
                        new Range(-100, 100),     // 题目没有明确给出,自己确定
                        // 0 < m <= n <= size
                        // m 和 n 的大小关系并不好控制,我们可以在算法中进行限制!
                        new Range(1, 1000),       // m
                        new Range(1, 1000),       // n
                }
        );

        Argument argument1 = new Argument(
                new Range[]{
                        new Range(1, 1),          // 一个叶子节点
                        new Range(-1, -1)         // 占位
                },

                new Range[]{
                        new Range(1, 1000),       // m
                        new Range(-1, -1)         // 占位
                }
        );

        Argument argument2 = new Argument(
                new Range[]{
                        new Range(1, 1),          // 一个叶子节点
                        new Range(-1, -1)         // 占位
                },

                new Range[]{
                        new Range(1, 1000),       // n
                        new Range(-1, -1)         // 占位
                }
        );
        // 激活对数器
        new Active().active(config, argument0, argument1, argument2);
    }
}

