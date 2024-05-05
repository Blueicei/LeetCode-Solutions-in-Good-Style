package n18_real_test;
import java.util.Scanner;
public class SubStringDP {
    // 3.小美的01串翻转
    //小美定义一个 01 串的权值为：每次操作选择一位取反，使得相邻字符都不相等的最小操作次数。
    //例如，"10001"的权值是 1，因为只需要修改一次：对第三个字符取反即可。
    //现在小美拿到了一个 01 串，她希望你求出所有非空连续子串的权值之和，你能帮帮她吗？
    // 输入描述：
    //一个仅包含'0'和'1'的字符串，长度不超过 2000。
    // 输出描述：
    //所有非空子串的权值和
    // 输入例子：
    //10001
    //输出例子：
    //8
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        char[] arr = sc.next().toCharArray();
        int n = arr.length;
        int[][][] dp = new int[n][n][2];
        for(int i = 0; i < n; i++){
            dp[i][i][(arr[i]- '0' ) ^ 1] = 1; // 取反操作 +1
        }
        int res = 0;
        for(int i=0; i<n;i++){
            for(int j=i+1; j<n; j++){
                if(arr[j] == '0'){
                    dp[i][j][0] = dp[i][j-1][1];
                    dp[i][j][1] = dp[i][j-1][0] + 1;
                } else {
                    dp[i][j][1] = dp[i][j-1][0];
                    dp[i][j][0] = dp[i][j-1][1] + 1;
                }
                res += Math.min(dp[i][j][0], dp[i][j][1]);
            }
        }
        System.out.println(res);
    }
}
