package n18_real_test;
import java.util.*;

public class GraphTopology {
    // 问题描述K小姐是一家图书馆的管理员。
    // 最近，她设计了一个新的图书整理系统。这个系统使用一个长度为n的书架，每个位署可以放置一本书或保持空置(用-1表示)
    // 系统使用一个简单的哈希函数,f(x)=x%n来决定每本书的理想位置，其中x是书的唯一编号。
    // 当需要放置一本新书时，系统从位置f(x)开始，沿书架向右循环查找，直到找到第一个空位置放置该书。
    // 如果书架已满或该书已在书架上，则不再放置。
    // 现在，K小姐想知道当前书架状态是由哪些书按什么顺序放置而成的。如果有多种可能的放置顺序，她希望找出字典序最小的一种。
    // 输入格式第一行包含一个正整数n，表示书架的长度。
    // 第二行包含n个整数a1,a2...,an，表示当前书架上每个位置的状态。ai=-1表示该位置为空，否则表示该位置放置的书的编号。
    // 输出格式输出t个非负整数，表示放置书籍的顺序，其中t为书架上非空位置的数量。
    // 样例输入
    //5
    //-1 1 -1 3 4
    //样例输出
    //1 3 4

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }

        List<Integer> result = solve(n, a);
        for (int book : result) {
            System.out.print(book + " ");
        }
    }

    static List<Integer> solve(int n, int[] a) {
        // 构建图和计算入度
        List<List<Integer>> graph = new ArrayList<>(n);
        int[] inDegree = new int[n];
        List<int[]> books = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
            if (a[i] != -1) {
                books.add(new int[]{a[i], i});
            }
        }

        // 按书号排序
        books.sort(Comparator.comparingInt(b -> b[0]));

        // 构建图
        for (int[] book : books) {
            int ideal = book[0] % n;
            int cur = ideal;
            while (cur != book[1]) {
                graph.get(cur).add(book[1]);
                inDegree[book[1]]++;
                cur = (cur + 1) % n;
            }
        }

        // 初始化优先队列
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(b -> b[0]));
        for (int i = 0; i < n; i++) {
            if (a[i] != -1 && inDegree[i] == 0) {
                pq.offer(new int[]{a[i], i});
            }
        }

        // 拓扑排序
        List<Integer> result = new ArrayList<>();
        while (!pq.isEmpty()) {
            int[] node = pq.poll();
            result.add(a[node[1]]);
            for (int nei : graph.get(node[1])) {
                if (--inDegree[nei] == 0) {
                    pq.offer(new int[]{a[nei], nei});
                }
            }
        }

        return result;
    }

}
