package n18_real_test;
import java.util.Scanner;
public class SubMatrixSum {
    // 1.小美的平衡矩阵
    //小美拿到了一个n*n的矩阵，其中每个元素是 0 或者 1。
    //小美认为一个矩形区域是完美的，当且仅当该区域内 0 的数量恰好等于 1 的数量。
    //现在，小美希望你回答有多少个i*i的完美矩形区域。你需要回答1≤i≤n的所有答案。
    // 输入描述：
    // 第一行输入一个正整数n，代表矩阵大小。
    // 接下来的n行，每行输入一个长度为n的 01 串，用来表示矩阵。
    // 输出描述：输出n行，第i行输出i*i的完美矩形区域的数量。
    // 输入例子：
    //4
    //1010
    //0101
    //1100
    //0011
    //输出例子：
    //0
    //7
    //0
    //1

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[][] matrix = new int[n+1][n+1];
        int line = 1;
        while(sc.hasNext()){
            String s = sc.next();
            for(int i=1; i<=n; i++){
                matrix[line][i] = s.charAt(i-1)=='0'?0:1;
                matrix[line][i] += matrix[line-1][i] + matrix[line][i-1] - matrix[line-1][i-1];
            }

            line++;
        }
        for(int size=1; size<=n; size++){
            int count = 0;
            if(size%2 != 0){
                System.out.println(0);
                continue;
            }
            for(int i=1; i<=n-size+1; i++){
                for(int j=1; j<=n-size+1; j++){
                    int tmp = matrix[i+size-1][j+size-1] - matrix[i-1][j+size-1] - matrix[i+size-1][j-1] + matrix[i-1][j-1];
                    if(tmp == size*size / 2) count++;
                }
            }
            System.out.println(count);
        }

    }
}
