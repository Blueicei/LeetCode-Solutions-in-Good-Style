package n18_real_test;
import java.util.*;

public class RemoveInterval {
    // 小美拿到了一个大小为n的数组，她希望删除一个区间后，使得剩余所有元素的乘积末尾至少有k个 0。小美想知道，一共有多少种不同的删除方案？
    // 输入描述
    //第一行输入两个正整数n，k。第二行输入n个正整数ai，代表小美拿到的数组。
    // 输出描述
    //一个整数，代表删除的方案数。
    // 输入
    //5 2
    //2 5 3 4 20
    //输出
    //4

    static final int N = 1000010;
    static int[] a2 = new int[N];
    static int[] a5 = new int[N];
    static int cnt2 = 0, cnt5 = 0;
    static int n, k, x;

    public static void cnt2Cnt5Count() {
        Scanner input = new Scanner(System.in);
        for (int i = 0; i < n; i++) {
            x = input.nextInt();
            while (x % 2 == 0) {
                a2[i]++;
                x /= 2;
                cnt2++;
            }
            while (x % 5 == 0) {
                a5[i]++;
                x /= 5;
                cnt5++;
            }
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        n = input.nextInt();
        k = input.nextInt();
        cnt2Cnt5Count();
        int l = 0;
        long res = 0;
        for (int r = 0; r < n; r++) {
            cnt2 -= a2[r];
            cnt5 -= a5[r];
            while (Math.min(cnt2, cnt5) < k && l <= r) {
                cnt2 += a2[l];
                cnt5 += a5[l];
                l++;
            }
            res += (long) (r - l + 1);
        }
        System.out.println(res);
    }

}
