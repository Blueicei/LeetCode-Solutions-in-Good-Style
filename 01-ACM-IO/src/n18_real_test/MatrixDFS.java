package n18_real_test;
import java.util.*;

public class MatrixDFS {
    // 小美的字符串变换
    //小美拿到了一个长度为n的字符串，她希望将字符串从左到右平铺成一个矩阵
    // （先平铺第一行，然后是第二行，以此类推，矩阵有x行y列，必须保证x*y=n，即每y个字符换行，共x行）。
    // 该矩阵的权值定义为这个矩阵的连通块数量。小美希望最终矩阵的权值尽可能小，你能帮小美求出这个最小权值吗？
    //注：我们定义，上下左右四个方向相邻的相同字符是连通的。
    // 第一行输入一个正整数n，代表字符串的长度。
    //第二行输入一个长度为n的、仅由小写字母组成的字符串。
    // 输入例子：
    //9
    //aababbabb
    //输出例子：
    //2
    public static void main(String[] args) {
        final long MOD = (long) (1e9 + 7);
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        int res = n;// 初始答案设为n个连通块
        String s = scanner.nextLine();
        int[] v = new int[n]; // 访问标记
        for(int i = 1; i < n; i++){
            // 表示每行放i个
            if(n % i == 0){
                Arrays.fill(v, 0); // 标记置0
                int cnt = 0;// 连通数
                for(int j = 0; j < s.length(); j++){
                    if(v[j] == 0){
                        cnt++; // 连通数+1
                        dfs(j, i, n, v, s);// j开始寻找 每行放i个
                    }
                }
                res = Math.min(res, cnt);
            }
        }
        System.out.println(res);
    }

    private static void dfs(int cur, int numOfRow, int n, int[] v, String s){
        if(v[cur] == 1)
            return;;
        v[cur] = 1;
        int row = cur / numOfRow; //所在行数
        int startCol = numOfRow * row; // 行起始列（包含）
        int endCol = startCol + numOfRow;// 行结束列（不包含）
        // 上走
        if(cur - numOfRow >=0 && s.charAt(cur - numOfRow) == s.charAt(cur))
            dfs(cur - numOfRow, numOfRow, n, v, s);

        // 右走
        if(cur + 1 < n && cur + 1 < endCol && s.charAt(cur + 1) == s.charAt(cur))
            dfs(cur + 1, numOfRow, n, v, s);

        //下走
        if(cur + numOfRow < n && s.charAt(cur + numOfRow) == s.charAt(cur))
            dfs(cur + numOfRow, numOfRow, n, v, s);

        //左走
        if(cur - 1 >=0 && cur - 1 >= startCol && s.charAt(cur - 1) == s.charAt(cur))
            dfs(cur - 1, numOfRow, n, v, s);
    }
}
