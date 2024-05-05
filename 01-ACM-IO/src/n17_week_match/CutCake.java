package n17_week_match;

import java.util.Arrays;

public class CutCake {
    // LC3218. 切蛋糕的最小总开销 I
    // 有一个 m x n 大小的矩形蛋糕，需要切成 1 x 1 的小块。给你整数 m ，n 和两个数组：
    //horizontalCut 的大小为 m - 1 ，其中 horizontalCut[i] 表示沿着水平线 i 切蛋糕的开销。
    //verticalCut 的大小为 n - 1 ，其中 verticalCut[j] 表示沿着垂直线 j 切蛋糕的开销。
    //一次操作中，你可以选择任意不是 1 x 1 大小的矩形蛋糕并执行以下操作之一：
    //沿着水平线 i 切开蛋糕，开销为 horizontalCut[i] 。
    //沿着垂直线 j 切开蛋糕，开销为 verticalCut[j] 。
    //每次操作后，这块蛋糕都被切成两个独立的小蛋糕。
    //每次操作的开销都为最开始对应切割线的开销，并且不会改变。
    //请你返回将蛋糕全部切成 1 x 1 的蛋糕块的 最小 总开销。
    // 输入：m = 3, n = 2, horizontalCut = [1,3], verticalCut = [5]
    // 输出：13

    public long minimumCost(int m, int n, int[] horizontalCut, int[] verticalCut) {
        Arrays.sort(horizontalCut); // 下面倒序遍历
        Arrays.sort(verticalCut);
        long ans = 0;
        int i = m - 2;
        int j = n - 2;
        int cntH = 1;
        int cntV = 1;
        while (i >= 0 || j >= 0) {
            if (j < 0 || i >= 0 && horizontalCut[i] > verticalCut[j]) {
                ans += horizontalCut[i--] * cntH; // 横切
                cntV++; // 需要竖切的蛋糕块增加
            } else {
                ans += verticalCut[j--] * cntV; // 竖切
                cntH++; // 需要横切的蛋糕块增加
            }
        }
        return ans;
    }

}
