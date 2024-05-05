package n16_backtracing;

public class MaxSumArray {
    // 53. 最大子数组和
    // 给你一个整数数组 nums ，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
    // 子数组是数组中的一个连续部分。
    // 输入：nums = [-2,1,-3,4,-1,2,1,-5,4]
    // 输出：6
    // 解释：连续子数组 [4,-1,2,1] 的和最大，为 6 。

    public int maxSubArray(int[] nums) {
        int len = nums.length;
        if (len == 0) {
            return 0;
        }
        int[] dp = new int[len];
        dp[0] = nums[0];

        for (int i = 1; i < len; i++) {
            if (dp[i - 1] >= 0) {
                dp[i] = dp[i - 1] + nums[i];
            } else {
                dp[i] = nums[i];
            }
        }

        // 最后不要忘记全部看一遍求最大值
        int res = dp[0];
        for (int i = 1; i < len; i++) {
            res = Math.max(res, dp[i]);
        }
        return res;
    }
    // 动态规划（空间优化）

    public int maxSubArray2(int[] nums) {
        int len = nums.length;
        if (len == 0) {
            return 0;
        }
        // 起名叫 pre 表示的意思是「上一个状态的值」
        int pre = nums[0];
        int res = pre;
        for (int i = 1; i < len; i++) {
            pre = Math.max(nums[i], pre + nums[i]);
            res = Math.max(res, pre);
        }
        return res;
    }

    // 前缀和，一次买卖股票（卖出减买入）
    public int maxSubArray3(int[] nums) {
        int ans = Integer.MIN_VALUE;
        int minPreSum = 0;
        int preSum = 0;
        for (int x : nums) {
            preSum += x; // 当前的前缀和
            ans = Math.max(ans, preSum - minPreSum); // 减去前缀和的最小值
            minPreSum = Math.min(minPreSum, preSum); // 维护前缀和的最小值
        }
        return ans;
    }

}
