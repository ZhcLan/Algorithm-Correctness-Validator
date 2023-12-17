package leetcode.week1;

import controller.Active;
import model.config.ValidatorConfig;
import model.range.Range;
import model.sample.Argument;
import model.util.type.IntegerPlus;

import java.util.HashMap;
import java.util.Map;

/**
 * <a href="https://leetcode.cn/problems/two-sum/">LeetCode1两数之和</a>
 * <p>
 * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target  的那 两个 整数，并返回它们的数组下标。
 * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
 * 你可以按任意顺序返回答案。
 * <p>
 * 2 ≤ nums.length ≤ 104
 * <p>
 * -109 ≤ nums[i] ≤ 109
 * <p>
 * -109 ≤ target ≤ 109
 */
public class LeetCode1两数之和 {

    public IntegerPlus[] validator_twoSum(IntegerPlus[] nums, IntegerPlus target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums.length; j++) {
                if (i != j && nums[i].value == target.value - nums[j].value) {
                    return new IntegerPlus[]{new IntegerPlus(i), new IntegerPlus(j)};
                }
            }
        }
        return new IntegerPlus[]{new IntegerPlus(-1), new IntegerPlus(-1)};
    }

    public IntegerPlus[] compare_twoSum(IntegerPlus[] nums, IntegerPlus target) {
        // (value,index)
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target.value - nums[i].value)) {
                return new IntegerPlus[]{new IntegerPlus(map.get(target.value - nums[i].value)), new IntegerPlus(i)};
            } else {
                map.put(nums[i].value, i);
            }
        }
        return new IntegerPlus[]{new IntegerPlus(-1), new IntegerPlus(-1)};
    }

    /**
     * 此题数据量较大,跑的时间较长,跑10次~~
     *
     * @param args
     */
    public static void main(String[] args) {
        ValidatorConfig config = new ValidatorConfig(10, LeetCode1两数之和.class,
                "validator_twoSum", "compare_twoSum");

        Argument argument1 = new Argument(
                new Range[]{
                        new Range(2, 10_0000),
                        new Range(-1, -1)
                },
                new Range[]{
                        new Range(-1, -1),
                        new Range(Integer.MIN_VALUE, Integer.MAX_VALUE)
                }
        );

        Argument argument2 = new Argument(
                new Range[]{
                        new Range(1, 1),
                        new Range(-1, -1)
                },
                new Range[]{
                        new Range(-1, -1),
                        new Range(Integer.MIN_VALUE, Integer.MAX_VALUE)
                }
        );

        new Active().active(config, argument1, argument2);
    }
}
