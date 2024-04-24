import java.util.*;

public class MainACM{

    public static void main(String[] args){
        //输入字符串数组，每行字符串之间空格分割
        Scanner sc = new Scanner(System.in);
        while(sc.hasNextLine()){
            String[] arr = sc.nextLine().split(" ");
            Arrays.sort(arr);
            for(String s : arr){
                System.out.print(s + " ");
            }
            System.out.println();
        }
        //输入n行a b 输出a+b
        Scanner in = new Scanner(System.in);
        int num = in.nextInt();
        for(int i = 0; i < num; i++) { // 注意 while 处理多个 case
            int a = in.nextInt();
            int b = in.nextInt();
            System.out.println(a + b);
        }
        while(num-->0){
            int a = in.nextInt();
            int b = in.nextInt();
            System.out.println(a+b);
        }
        //输入n行int数组，空格间隔
        while (in.hasNextLine()) {
            String[] s = in.nextLine().split(" ");
            // 1
            int sum = 0;
            for(int i = 0; i < s.length; i++) {
                sum += Integer.parseInt(s[i]);
            }
            // 2
            for(String ss:s){
                sum +=Integer.valueOf(ss);
            }
            // 3
            int n = sc.nextInt();
            int[] array = new int[n];
            for (int i = 0; i < n; i++) {
                array[i] = sc.nextInt();
            }
            System.out.println(sum);
        }
    }
}