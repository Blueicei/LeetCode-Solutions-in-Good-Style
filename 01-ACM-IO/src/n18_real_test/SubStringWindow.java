package n18_real_test;

import java.util.Scanner;
import java.util.Stack;

public class SubStringWindow {
    // 5.游游的好串
    //游游有一个只包含'0'和'1'的字符串，他想知道这个字符串有多少个好子串？
    //一个字符串如果是"好串"，那么该字符串的所有前缀，'0'的数量严格大于'1'的数量。
    // 输入描述：
    //输入一个只包含'0'和'1'的字符串，长度不超过100000。
    //输出描述：
    //输出一个整数，代表答案。
    //示例1
    //输入例子：
    //100
    //输出例子：
    //3

    static final int MAXN = (int) (1e5 + 10);
    static char[] chs = new char[MAXN];
    static long res = 0;
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String s = scanner.next();
        int n = s.length();
        for(int i = 1; i <= n; i++){
            chs[i] = s.charAt(i - 1);
        }
        int cnt1 = 0, cnt0 = 0;
        for(int l = 1, r = 1; r <= n; r++){
            if(chs[r] == '0')
                cnt0++;
            else
                cnt1++;

            while(cnt1 >= cnt0 && l <= r){
                if(chs[l] == '0')
                    cnt0--;
                else
                    cnt1--;
                l++;
            }
            res += (r - l + 1);
            // 不满足的 也就是1的个数乘2。固定r，严格大于就要留出2倍的空
            // 分为两种情况（注意我们依然讨论的是以r为结尾）
            //以 1 开头的子串， 也就是窗口内 1 的个数
            //第二种就是上面例子中 01 这样的 前面0的个数太少 这样的有多少个？同样是 1 的个数
            res -= 2L * cnt1;
        }
        System.out.println(res);
    }

    // 方法二 0替换成-1，转换成前缀和的问题
    public static void main1(String[] args) {
        Scanner sc = new Scanner(System.in);
        char[] arr = sc.next().toCharArray();
        int n = arr.length;
        int[] nums = new int[n+1];
        for(int i=1;i<=n;i++){
            if(arr[i-1]=='0') nums[i] = -1;
            else nums[i] = 1;
            nums[i] += nums[i-1];
        }
        long res = 0l;
        Stack<Integer> stack = new Stack<>();
        for(int i=n; i>=0; i--){
            // 有stack.push(i);保底，而且[i, stack.peek]之间保证有效，因此可以跳过该区间
            while(!stack.empty()&&nums[i]>nums[stack.peek()]){
                stack.pop();
            }
            res += stack.empty()?n-i:stack.peek()-i-1;
            stack.push(i);
        }
        System.out.println(res);
    }

    // 超时
    public static void main2(String[] args) {
        Scanner sc = new Scanner(System.in);
        char[] arr = sc.next().toCharArray();
        int n = arr.length;
        int res = 0;
        for(int l=0;l<n;l++){
            int cnt0 = 0;
            int cnt1 = 0;
            for(int r=l;r<n;r++){
                if(arr[r] == '0') cnt0++;
                else cnt1++;
                if(cnt0>cnt1) res++;
                else break;
            }
        }
        System.out.println(res);
    }
}
