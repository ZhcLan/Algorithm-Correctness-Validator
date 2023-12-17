package leetcode.week1;

import controller.Active;
import model.config.ValidatorConfig;
import model.range.Range;
import model.sample.Argument;
import model.util.type.IntegerPlus;
import model.util.type.StringPlus;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * <a href="https://leetcode.cn/problems/longest-substring-without-repeating-characters/description/">LeetCode3无重复字符的最长子串</a>
 * <p>
 * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
 * <p>
 * 0 ≤ s.length ≤ 5 * 104
 */
public class LeetCode3无重复字符的最长子串 {
    public IntegerPlus validator_lengthOfLongestSubstring(StringPlus s) {
        IntegerPlus res = new IntegerPlus(0);
        // <letter,times>
        Map<Character, Integer> map = new HashMap<>();
        // 扫描字符串
        // 当前维护区间 [i,j -  1]
        for (int i = 0, j = 0; j < s.value.length(); j++) {
            // 将 j 加入哈希表 , [i,j]
            if (map.containsKey(s.value.charAt(j))) {
                map.put(s.value.charAt(j), map.get(s.value.charAt(j)) + 1);
            } else {
                map.put(s.value.charAt(j), 1);
            }

            // 如果,维护区间出现重复了,说明 s[j]重复了,即[i ... j] 区间内有字母和s[j]重复
            // 重前往后枚举,每次记录最大值
            while (map.get(s.value.charAt(j)) > 1) {
                // 去掉 i 位置的字符,看看是否依然重复
                map.put(s.value.charAt(i), map.get(s.value.charAt(i)) - 1);
                i++;
            }
            res = new IntegerPlus(Math.max(res.value, j - i + 1));
        }
        return res;

    }

    // 来自 leetcode 题解
    public IntegerPlus compare_lengthOfLongestSubstring(StringPlus s) {
        // 哈希集合，记录每个字符是否出现过
        Set<Character> occ = new HashSet<Character>();
        int n = s.value.length();
        // 右指针，初始值为 -1，相当于我们在字符串的左边界的左侧，还没有开始移动
        IntegerPlus ans = new IntegerPlus(0);
        int rk = -1;
        for (int i = 0; i < n; ++i) {
            if (i != 0) {
                // 左指针向右移动一格，移除一个字符
                occ.remove(s.value.charAt(i - 1));
            }
            while (rk + 1 < n && !occ.contains(s.value.charAt(rk + 1))) {
                // 不断地移动右指针
                occ.add(s.value.charAt(rk + 1));
                ++rk;
            }
            // 第 i 到 rk 个字符是一个极长的无重复字符子串
            ans = new IntegerPlus(Math.max(ans.value, rk - i + 1));
        }
        return ans;
    }

    public static void main(String[] args) {
        ValidatorConfig config = new ValidatorConfig(100, LeetCode3无重复字符的最长子串.class,
                "validator_lengthOfLongestSubstring", "compare_lengthOfLongestSubstring");

        Argument argument = new Argument(
                new Range[]{
                        new Range(0, 5 * 10_0000),
                        new Range(-1, -1)
                },
                new Range[]{
                        // 字符串是叶子!
                        new Range(0, 127), // 字符串会自动生成
                        new Range(-1, -1)
                }
        );
        new Active().active(config, argument);
    }
}
