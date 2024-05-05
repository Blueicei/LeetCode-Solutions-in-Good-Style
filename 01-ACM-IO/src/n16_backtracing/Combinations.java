package n16_backtracing;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Combinations {
    // LC77. 组合
    // 给定两个整数 n 和 k，返回范围 [1, n] 中所有可能的 k 个数的组合。你可以按 任何顺序 返回答案。
    // 输入：n = 4, k = 2
    //输出：
    //[
    //  [2,4],
    //  [3,4],
    //  [2,3],
    //  [1,2],
    //  [1,3],
    //  [1,4],
    //]

    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> rs = new ArrayList<>();
        Deque<Integer> deque = new ArrayDeque<>();
        dfs(n, k, 0, deque, rs);
        return rs;
    }

    public void dfs(int n, int k, int begin, Deque<Integer> deque, List<List<Integer>> rs){
        if(deque.size() == k) {rs.add(new ArrayList<>(deque));System.out.println(deque.toString());return;}
        for(int i=begin; i<n; i++){
            deque.addLast(i+1);
            dfs(n, k, i+1, deque, rs);
            deque.removeLast();
        }
        // 剪枝
        for (int i = begin; i <= n - (k - deque.size()) + 1; i++) {
            continue;
        }
    }

    // Method2.DFS
    public List<List<Integer>> combine2(int n, int k) {
        List<List<Integer>> res = new ArrayList<>();
        if (k <= 0 || n < k) {
            return res;
        }

        // 为了防止底层动态数组扩容，初始化的时候传入最大长度
        Deque<Integer> path = new ArrayDeque<>(k);
        dfs2(1, n, k, path, res);
        return res;
    }

    private void dfs2(int begin, int n, int k, Deque<Integer> path, List<List<Integer>> res) {
        if (k == 0) {
            res.add(new ArrayList<>(path));
            return;
        }

        // 基础版本的递归终止条件：if (begin == n + 1) {
        if (begin > n - k + 1) {
            return;
        }
        // 不选当前考虑的数 begin，直接递归到下一层
        dfs2(begin + 1, n, k, path, res);

        // 不选当前考虑的数 begin，递归到下一层的时候 k - 1，这里 k 表示还需要选多少个数
        path.addLast(begin);
        dfs2(begin + 1, n, k - 1, path, res);
        // 深度优先遍历有回头的过程，因此需要撤销选择
        path.removeLast();
    }

}
