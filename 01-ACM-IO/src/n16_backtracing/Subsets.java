package n16_backtracing;

import java.util.*;

public class Subsets {
    // LC90. 子集. 给你一个整数数组 nums ，其中可能包含重复元素，请你返回该数组所有可能的子集（幂集）。
    // 解集 不能 包含重复的子集。返回的解集中，子集可以按 任意顺序 排列。
    // 输入：nums = [1,2,2]
    // 输出：[[],[1],[1,2],[1,2,2],[2],[2,2]]

    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> rs = new ArrayList<>();
        Deque<Integer> deque = new ArrayDeque<>();
        Arrays.sort(nums);
        dfs(nums, 0, deque, rs);
        return rs;
    }

    public void dfs(int[] nums, int begin, Deque<Integer> deque, List<List<Integer>> rs) {
        rs.add(new ArrayList<>(deque));
        for (int i = begin; i < nums.length; i++) {
            if (i > begin && nums[i] == nums[i - 1])
                continue;
            deque.addLast(nums[i]);
            dfs(nums, i + 1, deque, rs);
            deque.removeLast();
        }
    }
}
