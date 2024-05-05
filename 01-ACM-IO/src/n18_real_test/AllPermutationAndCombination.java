package n18_real_test;

import java.util.Scanner;

public class AllPermutationAndCombination {
    // 1.多多的数字组合
    //多多君最近在研究某种数字组合：
    //定义为：每个数字的十进制表示中(0~9)，每个数位各不相同且各个数位之和等于N。
    //满足条件的数字可能很多，找到其中的最小值即可。
    //多多君还有很多研究课题，于是多多君找到了你--未来的计算机科学家寻求帮助。
    // 输入描述：共一行，一个正整数N，如题意所示，表示组合中数字不同数位之和。
    // 输出描述：
    //共一行，一个整数，表示该组合中的最小值。
    //如果组合中没有任何符合条件的数字，那么输出-1即可。

    static long res = Long.MAX_VALUE;
    static boolean[] used = new boolean[10];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        dp(n, 0);
        res = res==Long.MAX_VALUE? -1:res;
        System.out.println(res);
    }

    static void dp(int n, long tmp){
        if(n<10){
            if(used[n]) return;
            tmp = tmp*10 + n;
            res = Math.min(res, tmp);
            return;
        }
        for(int i=1;i<10;i++){
            if(used[i]) continue;
            long next = tmp*10 + i;
            if(next>=res) return;
            used[i] = true;
            dp(n-i, next);
            used[i] = false;
        }
    }

    //进阶：空间复杂度 O(1)  ，时间复杂度 O(n)
    public static void main1(String[] args){
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        //每个数位各不相同且各个数位之和等于N——1+2+3+4+5+6+7+8+9 = 45，如果大于45一定会重复
        if(N > 45){
            System.out.println(-1);
            return;
        }
        //如果N<10，可以直接返回数字本身
        if(N < 10){
            System.out.println(N);
            return;
        }
        //右侧数位越大，越能保证左侧数位越小，越能保证整个数最小
        int nums = 0;
        int digit = 0;
        for(int i = 9; i>0; i--){
            if(N != 0 && i <= N){
                N -= i;
                nums += (int)Math.pow(10,digit)*i;
                digit++;
            }
        }
        System.out.println(nums);
    }
}
