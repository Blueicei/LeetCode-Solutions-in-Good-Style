package n18_real_test;

import java.util.Scanner;

public class SituSwap {
    // 21.树上交换节点
    //给定一棵树，每个节点有一个权值。现在每次可以交换任意两个节点的权值，请问最少多少次交换可以使得每个节点的权值等于它的编号？
    //保证给出的权值是一个排列，也就是说保证一定有解。
    // 输入描述：
    //第一行输入一个正整数n，代表树上的节点数量。
    //第二行输入n个正整数a_i，第i个正整数a_i是i号节点的权值，a_i互不相同。
    //接下来的n-1行，每行输入两个正整数u和v，代表u号节点和v号节点有一条边相连。
    // 输出描述：
    //一个整数，代表最小的交换次数。

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] nums = new int[n+1];
        for(int i=1;i<=n;i++){
            nums[i] = sc.nextInt();
        }
        int res = 0;
        for(int i=1;i<=n;i++){
            int tmp = i;
            while(nums[tmp] != i){
                // 注意这里的交换次序不能乱，或者直接另起一个函数值传递下标，也不会出行问题
                int t = nums[nums[tmp]];
                nums[nums[tmp]] = nums[tmp];
                nums[tmp] = t;
                res++;
            }
        }
        System.out.println(res);
    }
}
