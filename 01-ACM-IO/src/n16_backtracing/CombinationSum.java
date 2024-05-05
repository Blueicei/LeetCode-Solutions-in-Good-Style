package n16_backtracing;
import java.util.*;

public class CombinationSum {
    // LC39. 组合总和.
    // 给你一个 无重复元素 的整数数组 candidates 和一个目标整数 target ，找出 candidates 中可以使数字和为目标数 target 的 所有 不同组合 ，
    // 并以列表形式返回。你可以按 任意顺序 返回这些组合。
    // candidates 中的 同一个 数字可以 无限制重复被选取 。如果至少一个数字的被选数量不同，则两种组合是不同的。
    // 对于给定的输入，保证和为 target 的不同组合数少于 150 个。
    // 输入：candidates = [2,3,6,7], target = 7
    // 输出：[[2,2,3],[7]]
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> rs = new ArrayList<>();
        Deque<Integer> deque = new ArrayDeque<>();
        dfs(candidates, 0, target, deque, rs);
        return rs;
    }

    public void dfs(int[] nums, int begin, int target, Deque<Integer> deque, List<List<Integer>> rs){
        if(target<0) return;
        if(target == 0){rs.add(new ArrayList<>(deque));return ;}
        for(int i=begin; i<nums.length; i++){
            deque.addLast(nums[i]);
            // 区别：进入的是i！！（因为无限选取）
            dfs(nums, i, target-nums[i], deque, rs);
            deque.removeLast();
        }
    }


}
