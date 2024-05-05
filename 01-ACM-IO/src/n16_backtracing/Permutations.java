package n16_backtracing;

import java.util.*;
import java.util.stream.Collectors;

public class Permutations {
    // LC46. 全排列.给定一个不含重复数字的数组 nums ，返回其 所有可能的全排列 。你可以 按任意顺序 返回答案。
    // 输入：nums = [1,2,3]
    // 输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]

    public List<List<Integer>> permute(int[] nums) {
        int len = nums.length;
        List<List<Integer>> res = new ArrayList<>();
        if (len == 0) {
            return res;
        }

        boolean[] used = new boolean[len];
        Deque<Integer> path = new ArrayDeque<>(len);
        dfs(nums, len, 0, path, used, res);
        return res;
    }

    private void dfs(int[] nums, int len, int depth, Deque<Integer> path, boolean[] used, List<List<Integer>> res) {
        if (depth == len) {
            res.add(new ArrayList<>(path));
            return;
        }

        for (int i = 0; i < len; i++) {
            if (used[i]) {
                continue;
            }
            used[i] = true;
            path.addLast(nums[i]);

            dfs(nums, len, depth + 1, path, used, res);

            used[i] = false;
            path.removeLast();
        }
    }

    // Method2
    private List<List<Integer>> rs;
    private int len;
    public List<List<Integer>> permute2(int[] nums) {
        rs = new ArrayList<>();
        len = nums.length;
        dfs(nums, 0);
        return rs;
    }

    public void dfs(int[] nums, int begin){
        if(begin == len-1){
            List<Integer> list = Arrays.stream(nums).boxed().collect(Collectors.toList());
            rs.add(list);
            return;
        }
        for(int i=begin; i<len; i++){
            swap(nums, i, begin);
            dfs(nums, begin+1);
            swap(nums, i, begin);
        }
    }

    public void swap(int[] arr, int i, int j){
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    // LC60. 排列序列
    // 给出集合 [1,2,3,...,n]，其所有元素共有 n! 种排列。按大小顺序列出所有排列情况，并一一标记，当 n = 3 时, 所有排列如下：
    //"123"
    //"132"
    //"213"
    //"231"
    //"312"
    //"321"
    // 给定 n 和 k，返回第 k 个排列。
    // 输入：n = 3, k = 3
    // 输出："213"
    String rss = "";
    public String getPermutation(int n, int k) {
        StringBuilder deque = new StringBuilder();
        boolean[] used = new boolean[n];
        dfs(n, k, used, deque);
        return rss;
    }

    public int dfs(int n, int k, boolean[] used, StringBuilder deque){
        if(deque.length() == n){
            k--;
            if(k==0) rss = deque.toString();
            return k;
        }
        for(int i=0;i<n;i++){
            if(used[i]) continue;
            deque.append(i+1);
            used[i] = true;
            k = dfs(n, k, used, deque);
            deque.deleteCharAt(deque.length()-1);
            used[i] = false;
        }
        return k;
    }
}
