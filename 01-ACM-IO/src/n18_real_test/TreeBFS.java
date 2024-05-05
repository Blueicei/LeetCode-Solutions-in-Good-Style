package n18_real_test;
import java.util.*;
public class TreeBFS {
    // 12.推导式
    //形如1→3这样的式子是推导式，说明结论1可以推导出结论3。
    //显然这样的推导式具有传递性：
    //如果1→3且3→5，那么肯定满足1→5 。
    //现在给出n个推导式，你需要输出结论c能够推导出多少个不同的结论。
    // 输入描述：
    //第一行输入两个整数和c，含义如题意所述。
    //接下来行，每行输入两个整数x,y，表示一个推导式x->y，可能会出现重复的推导式。
    // 输出描述：
    //输出一个整数，表示结论c能推导出的不同结论的数目。

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int c = sc.nextInt();
        Map<Integer, Set<Integer>> map = new HashMap<>();
        for(int i=0; i<n; i++){
            int x = sc.nextInt();
            int y = sc.nextInt();
            Set<Integer> set = map.getOrDefault(x, new HashSet<>());
            set.add(y);
            map.put(x, set);
        }
        Deque<Integer> queue = new ArrayDeque<>();
        Set<Integer> global = new HashSet<>();
        queue.addLast(c);
        int res = 0;
        while(!queue.isEmpty()){
            Integer tmp = queue.removeFirst();
            if(global.contains(tmp)) continue;
            res++;
            global.add(tmp);
            if(map.containsKey(tmp)){
                Set<Integer> set = map.get(tmp);
                for(Integer t:set) queue.addLast(t);
            }
        }
        System.out.println(res);
    }
}
