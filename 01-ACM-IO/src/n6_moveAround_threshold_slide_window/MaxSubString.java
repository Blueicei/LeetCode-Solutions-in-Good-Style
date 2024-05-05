package n6_moveAround_threshold_slide_window;

import java.util.HashMap;
import java.util.Map;

public class MaxSubString {
    // 滑动窗口的思想是：先向右移动右指针、再向右移动左指针，这样左右指针交替执行（ 不回头 ），可以完成一些问题；
    // 滑动窗口是 暴力解法的优化 ，如何 根据目标函数把暴力解法的一系列解排除掉，是使用滑动窗口的前提 ，一定要分析清楚；
    // 「双指针」问题其实也是朴素算法的优化，一下子排序掉很多不符合题意的解，「滑动窗口」技巧也是这样的。依然是分析为什么可以使用双指针是更重要的。
    // 二分查找算法应用于查找下标也可以认为是双指针的解法。区别在于一个是从头开始，一个是从两头开始。不同于二分是在慢慢移动left和right而不是跳跃
    // 本题的优化在于Map存储重复元素上一个出现的位置，并将left跳到其后面，res存储着局部最优结果，可以放心纳入right

    // lc3 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串的长度。
    // 输入: s = "abcabcbb"
    // 输出: 3 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。

    // 哈希表，最优解
    public int lengthOfLongestSubstringWithoutRepeatingCharacters(String s) {
        int len = s.length();
        // 特判
        if (len < 2) {
            return len;
        }
        int res = 1;
        // key：数值，value：最新的下标
        Map<Character, Integer> map = new HashMap<>(len);
        char[] charArray = s.toCharArray();

        int left = 0;
        int right = 0;
        // [left, right) 没有重复元素
        while (right < len) {
            Character c = charArray[right];
            if (map.containsKey(c)) {
                left = Math.max(left, map.get(c) + 1); // 因为当前有重复所以，维持最长子串不重复，只有跳到上一个出现的位置之后
            }
            map.put(c, right);
            right++;

            res = Math.max(res, right - left);
        }
        return res;
    }
}
