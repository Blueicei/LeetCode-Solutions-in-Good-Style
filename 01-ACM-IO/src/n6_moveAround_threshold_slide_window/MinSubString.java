package n6_moveAround_threshold_slide_window;

public class MinSubString {
    // LC76. 最小覆盖子串 给你一个字符串 s 、一个字符串 t 。返回 s 中涵盖 t 所有字符的最小子串。如果 s 中不存在涵盖 t 所有字符的子串，则返回空字符串 "" 。
    // 输入：s = "ADOBECODEBANC", t = "ABC"
    // 输出："BANC" 解释：最小覆盖子串 "BANC" 包含来自字符串 t 的 'A'、'B' 和 'C'。


    public String minWindow(String s, String t) {
        int sLen = s.length();
        int tLen = t.length();
        if (sLen == 0 || tLen == 0 || sLen < tLen) {
            return "";
        }

        char[] charArrayS = s.toCharArray();
        char[] charArrayT = t.toCharArray();

        // 窗口内部覆盖 T 还需要的各个字符的个数
        int[] tFreq = new int[128];
        for (char c : charArrayT) {
            tFreq[c]++;
        }

        // 滑动窗口内部还差多少 T 中的字符，对应字符频数超过不重复计算
        int distance = tLen;
        int minLen = sLen + 1;
        int begin = 0;

        int left = 0;
        int right = 0;
        // [left, right)
        while (right < sLen) {
            // 1
            char charRight = charArrayS[right];
            if (tFreq[charRight] > 0) {
                distance--;
            }
            tFreq[charRight]--;
            right++;

            while (distance == 0) {
                if (right - left < minLen) {
                    minLen = right - left;
                    begin = left;
                }

                // 2, 定量输出条件，注意left应该对应right逆过程
                char charLeft = charArrayS[left];
                if (tFreq[charLeft] == 0) {
                    distance++;
                }
                tFreq[charLeft]++;
                left++;
            }
        }

        if (minLen == sLen + 1) {
            return "";
        }
        return s.substring(begin, begin + minLen);
    }
}
