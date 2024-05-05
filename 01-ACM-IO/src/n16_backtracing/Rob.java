package n16_backtracing;

public class Rob {
    // LC198. 打家劫舍.
    // 你是一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统，
    // 如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。
    // 给定一个代表每个房屋存放金额的非负整数数组，计算你 不触动警报装置的情况下 ，一夜之内能够偷窃到的最高金额。
    // 输入：[1,2,3,1]
    // 输出：4
    // 解释：偷窃 1 号房屋 (金额 = 1) ，然后偷窃 3 号房屋 (金额 = 3)。
    //     偷窃到的最高金额 = 1 + 3 = 4 。

    public int rob(int[] nums) {
        int pre = 0, cur = 0, tmp;
        for(int num : nums) {
            tmp = cur;
            cur = Math.max(pre + num, cur);
            pre = tmp;
        }
        return cur;
    }

    public int rob2(int[] nums) {
        int len = nums.length;
        // 0 表示不偷
        // 1 表示偷
        // 多加 1 天表示哨兵，相应地要做一些偏移

        // dp[i]：区间 [0, i] 偷取的最大价值
        int[][] dp = new int[len + 1][2];
        for (int i = 1; i <= len; i++) {
            // 不偷：昨天不偷，昨天偷转换而来
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1]);
            // 偷：只能由昨天不偷转换来
            // 注意：这里有下标偏移
            dp[i][1] = dp[i - 1][0] + nums[i - 1];
        }

        return Math.max(dp[len][0], dp[len][1]);
    }
}
