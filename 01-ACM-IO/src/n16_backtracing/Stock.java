package n16_backtracing;

public class Stock {
    // LC121. 买卖股票的最佳时机
    // 给定一个数组 prices ，它的第 i 个元素 prices[i] 表示一支给定股票第 i 天的价格。
    // 你只能选择 某一天 买入这只股票，并选择在 未来的某一个不同的日子 卖出该股票。设计一个算法来计算你所能获取的最大利润。
    // 返回你可以从这笔交易中获取的最大利润。如果你不能获取任何利润，返回 0 .
    // 输入：[7,1,5,3,6,4]
    // 输出：5
    // 解释：在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5 。
    //     注意利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格；同时，你不能在买入前卖出股票。

    public int maxProfit(int[] prices) {
        int len = prices.length;
        if (len < 2) {
            return 0;
        }

        int[][] dp = new int[2][2];
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        for (int i = 1; i < len; i++) {
            dp[i % 2][0] = Math.max(dp[(i - 1) % 2][0], dp[(i - 1) % 2][1] + prices[i]);
            // 不能购买第二次
            dp[i % 2][1] = Math.max(dp[(i - 1) % 2][1], -prices[i]);
        }
        return dp[(len - 1) & 1][0];
    }
    public int maxProfit2(int[] prices) {
        int len = prices.length;
        // 特殊判断
        if (len < 2) {
            return 0;
        }
        int[][] dp = new int[len][2];

        // dp[i][0] 下标为 i 这天结束的时候，不持股，手上拥有的现金数
        // dp[i][1] 下标为 i 这天结束的时候，持股，手上拥有的现金数

        // 初始化：不持股显然为 0，持股就需要减去第 1 天（下标为 0）的股价
        dp[0][0] = 0;
        dp[0][1] = -prices[0];

        // 从第 2 天开始遍历
        for (int i = 1; i < len; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], -prices[i]);
        }
        return dp[len - 1][0];
    }

    // LC122 你在任何时候 最多 只能持有 一股 股票。你也可以先购买，然后在 同一天 出售。
    private int rs = 0;
    public int maxProfit3(int[] prices) {
        int len = prices.length;
        int[][] state = new int[2][2];
        state[0][0] = 0;
        state[0][1] = -prices[0];
        for(int i=1;i<len;i++){
            state[1][0] = Math.max(state[0][0], state[0][1]+prices[i]);
            state[1][1] = Math.max(state[0][1], state[0][0]-prices[i]);
            state[0][0] = state[1][0];
            state[0][1] = state[1][1];
        }
        return state[1][0];
    }

    public void dfs(int[] prices, int begin, int len, boolean have, int money){
        if(begin == len){
            if(!have && money>0) rs = Math.max(rs, money);
            return;
        }
        dfs(prices, begin+1, len, have, money);
        if(have){
            have = false;
            money += prices[begin];
            dfs(prices, begin+1, len, have, money);
        }else{
            have = true;
            money -= prices[begin];
            dfs(prices, begin+1, len, have, money);
        }
    }

    // LC123. 买卖股票的最佳时机 III
    // 你最多可以完成 两笔 交易。注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。

    public int maxProfit4(int[] prices) {
        int len = prices.length;
        if (len < 2) {
            return 0;
        }

        int[][][] dp = new int[2][3][2];
        dp[0][1][1] = -prices[0];
        dp[0][2][1] = Integer.MIN_VALUE;
        for (int i = 1; i < len; i++) {
            dp[i % 2][1][1] = Math.max(dp[(i - 1) % 2][1][1], -prices[i]);
            dp[i % 2][1][0] = Math.max(dp[(i - 1) % 2][1][0], dp[(i - 1) % 2][1][1] + prices[i]);
            dp[i % 2][2][1] = Math.max(dp[(i - 1) % 2][2][1], dp[(i - 1) % 2][1][0] - prices[i]);
            dp[i % 2][2][0] = Math.max(dp[(i - 1) % 2][2][0], dp[(i - 1) % 2][2][1] + prices[i]);
        }
        return Math.max(dp[(len - 1) % 2][1][0], dp[(len - 1) % 2][2][0]);
    }
    public int maxProfit5(int[] prices) {
        int len = prices.length;
        if (len < 2) {
            return 0;
        }

        int[][] dp = new int[3][2];
        dp[1][1] = -prices[0];
        dp[2][1] = Integer.MIN_VALUE;
        for (int i = 1; i < len; i++) {
            dp[1][1] = Math.max(dp[1][1], -prices[i]);
            dp[1][0] = Math.max(dp[1][0], dp[1][1] + prices[i]);
            dp[2][1] = Math.max(dp[2][1], dp[1][0] - prices[i]);
            dp[2][0] = Math.max(dp[2][0], dp[2][1] + prices[i]);
        }
        return Math.max(dp[1][0], dp[2][0]);
    }
}
