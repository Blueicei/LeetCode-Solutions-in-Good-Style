package n18_real_test;

import java.util.Scanner;

public class ZeroOneBackPack {
    // 15.小红的分享日常
    // 小红很喜欢前往小红书分享她的日常生活。已知她生活中有i个事件，分享第i个事件需要她花费t_i的时间和h_i的精力来编辑文章，并能获得a_i的快乐值。
    // 小红想知道，在总花费时间不超过T且总花费精力不超过H的前提下，小红最多可以获得多少快乐值？
    // 输入描述:第一行输入一个正整数n，代表事件的数量。
    // 第二行输入两个正整数T和H，代表时间限制和精力限制。
    // 接下来的n行，每行输入三个正整数ti，hi,ai，代表分享第i个事件需要花费t的时间、h的精力，收获ai的快乐值。
    // 输出描述：一个整数，代表小红最多的快乐值。

    public static void main(String[] args) {
        final long MOD = (long) (1e9 + 7);
        Scanner scanner = new Scanner(System.in);
        int n  = scanner.nextInt();
        int T = scanner.nextInt(), H = scanner.nextInt();
        // 前i个事件在时间剩余t精力剩余h的最大快乐值
        long[][][] dp = new long[n + 1][T + 1][H + 1];
        int[][] event = new int[n][3];
        for(int i = 0; i < n; i++){
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            int z = scanner.nextInt();
            event[i][0] = x;
            event[i][1] = y;
            event[i][2] = z;
        }

        for(int i = 1; i <=n; i++){
            for(int t = 0; t <= T; t++){
                for(int h = 0; h <= H; h++){
                    //剩余时间
                    int leftT = t - event[i - 1][0];
                    //剩余精力
                    int leftH = h - event[i - 1][1];

                    //不分享事件i
                    dp[i][t][h] = dp[i - 1][t][h];

                    //能够分享事件i 则取最大值
                    if(leftT >= 0 && leftH >= 0)
                        dp[i][t][h] = Math.max(dp[i][t][h], dp[i - 1][leftT][leftH] + event[i - 1][2]);
                }
            }
        }
        System.out.println(dp[n][T][H]);
    }
}
