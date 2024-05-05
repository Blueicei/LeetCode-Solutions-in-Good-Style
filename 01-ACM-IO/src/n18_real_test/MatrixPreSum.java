package n18_real_test;

import java.util.Scanner;

public class MatrixPreSum {
    // 2.游游的you矩阵
    //游游拿到了一个字符矩阵，她想知道有多少个三角形满足以下条件：
    //1. 三角形的三个顶点分别是 y、o、u 字符。
    //2. 三角形为直角三角形，且两个直角边一个为水平、另一个为垂直。
    // 输入描述：
    //第一行输入两个正整数n,m，用空格隔开，代表矩阵的行数和列数。
    //接下来的n行，每行输入一个长度为m的字符串，代表游游拿到的矩阵。
    // 输出描述：
    //输出一个整数，代表满足条件的三角形个数。
    // 输入例子：
    //2 3
    //you
    //our
    //输出例子：
    //3

    // 枚举90度角的字符 因为这是唯一的。
    static final int MAXN = 1010;
    static char[][] chs = new char[MAXN][MAXN];
    static int[][][] y = new int[MAXN][MAXN][2];
    static int[][][] o = new int[MAXN][MAXN][2];
    static int[][][] u = new int[MAXN][MAXN][2];
    static long res = 0;
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        for(int i = 1; i <= n; i++){
            char[] charArray = scanner.next().toCharArray();
            for(int j = 0; j < m; j++){
                chs[i][j + 1] = charArray[j];
            }
        }
        // 0 为行 1为列
        // 行前缀和
        for(int i = 1; i <= n; i++){
            for(int j = 1; j <= m; j++){
                char ch = chs[i][j];
                y[i][j][0] = y[i][j - 1][0] + (ch == 'y'? 1: 0);
                o[i][j][0] = o[i][j - 1][0] + (ch == 'o'? 1: 0);
                u[i][j][0] = u[i][j - 1][0] + (ch == 'u'? 1: 0);
            }
        }

        // 列前缀和
        for(int i = 1; i <= n; i++){
            for(int j = 1; j <= m; j++){
                char ch = chs[i][j];
                y[i][j][1] = y[i - 1][j][1] + (ch == 'y'? 1: 0);
                o[i][j][1] = o[i - 1][j][1] + (ch == 'o'? 1: 0);
                u[i][j][1] = u[i - 1][j][1] + (ch == 'u'? 1: 0);
            }
        }

        for(int i = 1; i <= n; i++){
            for(int j = 1; j <= m; j++){
                char ch = chs[i][j];
                if(ch == 'y'){
                    // 上右  上面的o字符数量 乘以 右边的u字符数量  或者是 上面的u字符数量 乘以 右边的o字符数量
                    res = res + o[i][j][1] * (u[i][m][0] - u[i][j][0]);
                    res = res + u[i][j][1] * (o[i][m][0] - o[i][j][0]);
                    // 右下
                    res = res + (o[i][m][0] - o[i][j][0]) * (u[n][j][1] - u[i][j][1]);
                    res = res + (u[i][m][0] - u[i][j][0]) * (o[n][j][1] - o[i][j][1]);
                    // 下左
                    res = res + (o[n][j][1] - o[i][j][1]) * u[i][j][0];
                    res = res + (u[n][j][1] - u[i][j][1]) * o[i][j][0];
                    // 左上
                    res = res + o[i][j][0] * u[i][j][1];
                    res = res + u[i][j][0] * o[i][j][1];
                }else if(ch == 'o'){
                    // 上右
                    res = res + y[i][j][1] * (u[i][m][0] - u[i][j][0]);
                    res = res + u[i][j][1] * (y[i][m][0] - y[i][j][0]);
                    // 右下
                    res = res + (y[i][m][0] - y[i][j][0]) * (u[n][j][1] - u[i][j][1]);
                    res = res + (u[i][m][0] - u[i][j][0]) * (y[n][j][1] - y[i][j][1]);
                    // 下左
                    res = res + (y[n][j][1] - y[i][j][1]) * u[i][j][0];
                    res = res + (u[n][j][1] - u[i][j][1]) * y[i][j][0];
                    // 左上
                    res = res + y[i][j][0] * u[i][j][1];
                    res = res + u[i][j][0] * y[i][j][1];
                }else if(ch == 'u'){// u
                    // 上右
                    res = res + y[i][j][1] * (o[i][m][0] - o[i][j][0]);
                    res = res + o[i][j][1] * (y[i][m][0] - y[i][j][0]);
                    // 右下
                    res = res + (y[i][m][0] - y[i][j][0]) * (o[n][j][1] - o[i][j][1]);
                    res = res + (o[i][m][0] - o[i][j][0]) * (y[n][j][1] - y[i][j][1]);
                    // 下左
                    res = res + (y[n][j][1] - y[i][j][1]) * o[i][j][0];
                    res = res + (o[n][j][1] - o[i][j][1]) * y[i][j][0];
                    // 左上
                    res = res + y[i][j][0] * o[i][j][1];
                    res = res + o[i][j][0] * y[i][j][1];
                }
            }
        }
        System.out.println(res);

    }
}
