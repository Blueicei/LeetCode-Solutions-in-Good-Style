package n18_real_test;

import java.util.Scanner;

public class SubSetMinusSubSequence {
    // 2.小红的可爱串
    // 我们定义子序列为字符串中可以不连续的一段，而子串则必须连续。例如 rderd 包含子序列 "red”，且不包含子串"red”，因此该字符串为可爱串。
    //小红想知道，长度为 n（3 <= n <= 10 ^ 5）的、仅由 ‘r’‘e’‘d’ 三种字母组成的字符串中，有多少是可爱串? 答案请对 10 ^ 9 + 7 取模。
    // 输入例子：
    //4
    //输出例子：
    //3
    //例子说明：
    //"reed"、"rerd"、"rded"

    private static final long MOD = 1000000007L;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 读取字符串长度
        int n = sc.nextInt();

        // 计算可爱串的数量
        int result = kawaiiStrings(n);
        System.out.println(result);
    }

    public static int kawaiiStrings(int n) {
        if (n < 3) return 0; // 长度小于3时，不可能有子序列 "red"

        long[] f = new long[n + 1];
        long[] g = new long[n + 1];
        long[] h = new long[n + 1];

        // 计算g数组
        for (int i = 2; i <= n; i++) {
            g[i] = (g[i - 1] * 2 % MOD + (i - 1) * powMod(2, i - 2, MOD) % MOD) % MOD;
        }

        // 计算f数组
        for (int i = 2; i <= n; i++) {
            f[i] = (f[i - 1] * 3 % MOD + g[i - 1] % MOD) % MOD;
        }

        // 计算h数组
        for (int i = 3; i <= n; i++) {
            h[i] = (powMod(3, i - 3, MOD) + 3 * h[i - 1] % MOD + MOD - h[i - 3]) % MOD;
        }

        // 最终结果
        return (int) ((f[n] + MOD - h[n]) % MOD);
    }

    // 快速幂方法
    public static long powMod(long a, long b, long mod) {
        long res = 1;
        while (b > 0) {
            if ((b & 1) == 1) {
                res = res * a % mod;
            }
            a = a * a % mod;
            b >>= 1;
        }
        return res;
    }
}
