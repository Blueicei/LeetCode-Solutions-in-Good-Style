package n12_01_package;

import java.util.Arrays;

public class InfiniteCoin {

    // 322. 零钱兑换 完全背包（空间优化的写法）
    // 给你一个整数数组 coins ，表示不同面额的硬币；以及一个整数 amount ，表示总金额。
    // 计算并返回可以凑成总金额所需的 最少的硬币个数 。如果没有任何一种硬币组合能组成总金额，返回 -1 。
    // 你可以认为每种硬币的数量是无限的。
    // 输入：coins = [1, 2, 5], amount = 11
    // 输出：3
    // 解释：11 = 5 + 5 + 1

    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;

        for (int coin : coins) {
            for (int i = coin; i <= amount; i++) {
                // dp[i - coin] + 1 可能会发生整型溢出
                if (dp[i - coin] != amount + 1) {
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }

        if (dp[amount] == amount + 1) {
            dp[amount] = -1;
        }
        return dp[amount];
    }

}
