package n18_real_test;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Scanner;

public class IterateAllSubsequence {
    // 3.多多的求和计算
    //多多路上从左到右有N棵树（编号1～N），其中第i个颗树有和谐值Ai。
    //多多鸡认为，如果一段连续的树，它们的和谐值之和可以被M整除，那么这个区间整体看起来就是和谐的。
    //现在多多鸡想请你帮忙计算一下，满足和谐条件的区间的数量。
    // 输入描述：
    //第一行，有2个整数N和M，表示树的数量以及计算和谐值的参数。
    //（ 1 <= N <= 100,000, 1 <= M <= 100  ）
    //第二行，有N个整数Ai, 分别表示第i个颗树的和谐值。
    //（ 0 <= Ai <= 1,000,000,000 ）
    // 输出描述：
    //共1行，每行1个整数，表示满足整体是和谐的区间的数量。
    // 输入例子：
    //5 2
    //1 2 3 4 5
    //输出例子：
    //6

    // 超时
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        long m = sc.nextLong();
        long[] preSum = new long[n+1];
        for(int i=1;i<=n;i++){
            preSum[i] = sc.nextLong();
            preSum[i] += preSum[i-1];
        }
        int res = 0;
        // 方法1
        // for(int i=0;i<=n;i++){
        //     for(int j=i+1;j<=n;j++){
        //         if((preSum[j]-preSum[i])%m==0) res++;
        //     }
        // }
        // 方法2
        for(int r=1;r<=n;r++){
            int l = r-1;
            while(l>=0){
                if((preSum[r]-preSum[l])%m==0) res++;
                l--;
            }
        }
        System.out.println(res);
    }

    // 相同余数的前缀区间任选两个所构成的中间区间一定和谐
    // （因为大的那个前缀区间求和减去小的前缀区间求和，刚好把那个多出来的余数减掉了，因此中间区间求和一定能被m整除）
    public static void main1(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] params = br.readLine().split(" ");
        int n = Integer.parseInt(params[0]);
        int m = Integer.parseInt(params[1]);
        int[] map = new int[m];       // 除以m的余数为0~m-1
        params = br.readLine().split(" ");
        int[] A = new int[n];
        map[0] = 1;
        long culSum = 0L;
        long count = 0L;
        for(int i = 0; i < n; i++) {
            A[i] = Integer.parseInt(params[i]);
            culSum += A[i];
            int remain = (int)(culSum % m);
            count += map[remain];
            map[remain] ++;
        }
        System.out.println(count);
    }
}
