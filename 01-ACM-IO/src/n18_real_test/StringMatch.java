package n18_real_test;
import java.util.*;
public class StringMatch {
    // 2.小美的字符串匹配度
    //小美有两个长度为n只包含小写字母的字符串s和t，小美定义“两个字符串的匹配度”为i∈[1,n]中s_i = t_i的数量，例如"abacd"和"aabdd"的匹配度就是2。
    // 现在你可以进行最多一次以下操作:
    //对于字符串tt，选择两个索引i,j(1≤i<j≤n)，交换t_i 和t_j
    // 小美想知道，ss和tt的最大字符串匹配度是多少？
    // 第一行输入一个整数n
    //第二行输入一个长度为n的字符串s。
    //第三行输入一个长度为n的字符串t。
    // 输出描述：
    //输出一个整数，s和t的最大匹配度。
    // 输入例子：
    //5
    //ababc
    //babac
    //输出例子：
    //3

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        String s = sc.next();
        String t = sc.next();
        int res = 0;
        Map<Character, List<Integer>> map = new HashMap<>();
        for(int i=0; i<n; i++){
            if(s.charAt(i) == t.charAt(i)) res++;
            else {
                List<Integer> list = map.getOrDefault(t.charAt(i), new ArrayList<>());
                list.add(i);
                map.put(t.charAt(i), list);
            }
        }
        int tmp = res;
        for(int i=0; i<n; i++){
            if(s.charAt(i) != t.charAt(i) && map.containsKey(s.charAt(i))){
                List<Integer> list = map.get(s.charAt(i));
                for(int j=0; j<list.size(); j++){
                    int cnt = 1;
                    int idx = list.get(j);
                    if(s.charAt(idx) == t.charAt(i)){
                        cnt++;
                    }
                    tmp = Math.max(tmp, res + cnt);
                }
            }
        }
        System.out.println(tmp);

    }
}
