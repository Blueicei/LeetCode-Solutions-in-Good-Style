package n18_real_test;
import java.util.*;
public class GoodMatrix {
    // 7.小美的好矩阵
    //小美定义一个矩阵是好矩阵，当且仅当该矩阵满足：
    //1. 矩阵仅由'A'、'B'、'C'三种字符组成。且三种字符都出现过。
    //2. 矩阵相邻的字符都不相等。
    //现在给定一个n*m的矩阵，小美想知道有多少个3*3的子矩阵是好矩阵，你能帮帮她吗？
    // 第一行输入两个整数n,m，代表矩阵的行数和列数。
    //接下来的n行，每行输入一个仅包含大写字母的长度为m的字符串。
    // 输出一个整数表示答案。
    // 输入例子：
    //4 4
    //DABC
    //ABAB
    //BABA
    //BBAB
    //输出例子：
    //1

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        char[][] matrix = new char[n][m];
        for(int i=0; i<n; i++){
            matrix[i] = sc.next().toCharArray();
        }
        int res = 0;
        for(int i=0; i<n-3+1; i++){
            for(int j=0; j<m-3+1; j++){
                if(isGood(matrix, i, j)) res++;
            }
        }
        System.out.println(res);
    }

    static boolean isGood(char[][] matrix, int x, int y){
        Set<Character> set = new HashSet<>();
        for(int i=x; i<x+3; i++){
            for(int j=y; j<y+3; j++){
                if(matrix[i][j] >= 'D' || (i+1<x+3 && matrix[i][j] == matrix[i+1][j]) || (j+1<y+3 && matrix[i][j] == matrix[i][j+1])) return false;
                set.add(matrix[i][j]);
            }
        }

        return set.size() == 3;
    }
}
