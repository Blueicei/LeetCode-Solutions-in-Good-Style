package n11_string_hash;

import java.util.*;

public class MinCostString {
    // LC3213最小代价构造字符串
    // 给你一个字符串 target、一个字符串数组 words 以及一个整数数组 costs，这两个数组长度相同。
    // 设想一个空字符串 s。
    // 你可以执行以下操作任意次数（包括零次）：
    // 选择一个在范围  [0, words.length - 1] 的索引 i。
    // 将 words[i] 追加到 s。
    // 该操作的成本是 costs[i]。
    // 返回使 s 等于 target 的 最小 成本。如果不可能，返回 -1。
    // 输入： target = "abcdef", words = ["abdef","abc","d","def","ef"], costs = [100,1,1,10,5]
    // 输出： 7

    public int minimumCost(String target, String[] words, int[] costs) {
        char[] t = target.toCharArray();
        int n = t.length;

        // 多项式字符串哈希（方便计算子串哈希值）
        // 哈希函数 hash(s) = s[0] * base^(n-1) + s[1] * base^(n-2) + ... + s[n-2] * base + s[n-1]
        final int MOD = 1_070_777_777;
        int BASE = (int) 8e8 + new Random().nextInt((int) 1e8); // 随机 base，防止 hack
        int[] powBase = new int[n + 1]; // powBase[i] = base^i
        int[] preHash = new int[n + 1]; // 前缀哈希值 preHash[i] = hash(target[0] 到 target[i-1])
        powBase[0] = 1;
        for (int i = 0; i < n; i++) {
            powBase[i + 1] = (int) ((long) powBase[i] * BASE % MOD);
            preHash[i + 1] = (int) (((long) preHash[i] * BASE + t[i]) % MOD); // 秦九韶算法计算多项式哈希
        }

        Map<Integer, Map<Integer, Integer>> minCost = new TreeMap<>(); // 长度 -> 哈希值 -> 最小成本
        for (int i = 0; i < words.length; i++) {
            long h = 0;
            for (char b : words[i].toCharArray()) {
                h = (h * BASE + b) % MOD;
            }
            minCost.computeIfAbsent(words[i].length(), k -> new HashMap<>()).
                    merge((int) h, costs[i], Integer::min);
        }

        int[] f = new int[n + 1];
        Arrays.fill(f, Integer.MAX_VALUE / 2);
        f[0] = 0;
        for (int i = 1; i <= n; i++) {
            for (Map.Entry<Integer, Map<Integer, Integer>> e : minCost.entrySet()) {
                int len = e.getKey();
                if (len > i) {
                    break;
                }
                // 计算子串 target[i-sz] 到 target[i-1] 的哈希值（计算方法类似前缀和）
                int subHash = (int) (((preHash[i] - (long) preHash[i - len] * powBase[len]) % MOD + MOD) % MOD);
                f[i] = Math.min(f[i], f[i - len] + e.getValue().getOrDefault(subHash, Integer.MAX_VALUE / 2));
            }
        }
        return f[n] == Integer.MAX_VALUE / 2 ? -1 : f[n];
    }

}
