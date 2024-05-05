package n18_real_test;
import java.util.*;

public class AllPermutationAndEulerFilter {
    // 1.游游的排列统计
    //游游想知道，有多少个长度为n的排列满足任意两个相邻元素之和都不是素数。你能帮帮她吗？
    //我们定义，长度为n的排列值一个长度为n的数组，其中1到n每个元素恰好出现了一次。
    // 输入描述：一个正整数n。
    // 输出描述：满足条件的排列数量。
    // 输入例子：
    //5
    //输出例子：
    //4

    static final int MAX = 20;
    static boolean[] isNotPrime = new boolean[MAX];
    static boolean[] used = new boolean[11];
    static Deque<Integer> deque = new ArrayDeque<>();
    static int res = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        isNotPrime = new boolean[MAX];
        List<Integer> primes = new ArrayList<>();

        for (int i = 2; i < MAX; i++) {
            if (!isNotPrime[i])
                primes.add(i);
            for (int prime : primes) {
                if (i * prime >= MAX)
                    break;
                isNotPrime[i * prime] = true;
                if (i % prime == 0)
                    break;
            }
        }
        dp(n);
        System.out.println(res);
    }

    static void dp(int n){
        if(deque.size()==n){
            res++;
            return;
        }
        for(int i=1;i<=n;i++){
            if(deque.isEmpty() || (!used[i] && isNotPrime[i+deque.getLast()])){
                used[i] = true;
                deque.addLast(i);
                dp(n);
                used[i] = false;
                deque.removeLast();
            }
        }
    }

}
