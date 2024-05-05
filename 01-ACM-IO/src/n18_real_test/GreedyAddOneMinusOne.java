package n18_real_test;

import java.util.Arrays;
import java.util.Scanner;

public class GreedyAddOneMinusOne {
    // 4.游游的元素修改
    //游游拿到了一个数组，她每次操作可以使得一个元素加1，另一个元素减1。
    //游游希望最终数组的每个元素大小都在[l,r]范围内，她想知道自己最少多少次操作可以达成目标？
    //输入描述：第一行输入一个正整数t，代表用例的组数。
    //对于每组用例：
    //第一行输入三个正整数n,l,r。
    //第二行输入n个正整数a_i，代表游游拿到的数组。
    // 输出描述：
    //输出t行，每行一个整数，含义如下：
    //如果无法用有限次数的操作次数使得每个元素大小都在[l,r]范围内，请输出-1。
    //否则输出一个整数，代表最少的操作次数。
    // 输入例子：
    //2
    //2 3 5
    //1 2
    //3 4 6
    //3 6 5
    //输出例子：
    //-1
    //1

    static final int MAXN = 1010;
    static long[] res;
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();
        res = new long[t];
        Arrays.fill(res, -1);
        for(int i = 0; i < t; i++){
            int n = scanner.nextInt();
            int l = scanner.nextInt();
            int r = scanner.nextInt();
            // 比l大的有多少 比r小的有多少
            long lessThanR = 0, moreThanL = 0;
            // 超出左边界的有多少 超出右边界的有多少
            long needL = 0, needR = 0;
            for(int j = 0; j < n; j++){
                long x = scanner.nextLong();
                if(x > l)
                    moreThanL += (x - l);
                if(x < r)
                    lessThanR += (r - x);
                if(x < l)
                    needL += l - x;
                if(x > r)
                    needR += x - r;
            }
            if(needL >= needR && moreThanL >= needL){
                res[i] = needL;
            }
            if(needL <= needR && lessThanR >= needR){
                res[i] = needR;
            }
        }
        for(long num: res){
            System.out.println(num);
        }
    }
}
