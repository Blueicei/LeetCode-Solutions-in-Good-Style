package n5_situ_hash_swap_rightPosition_non_compare_sort;

import java.util.ArrayList;
import java.util.List;

public class FindDuplicate {
    // 原地交换，为element找到正确位置，陷入死循环时，便找到了重复元素
    // LC287 寻找重复数:给定一个包含 n + 1 个整数的数组 nums ，其数字都在 [1, n] 范围内（包括 1 和 n），可知至少存在一个重复的整数。
    // 假设 nums 只有 一个重复的整数 ，返回 这个重复的数 。
    // 你设计的解决方案必须 不修改 数组 nums 且只用常量级 O(1) 的额外空间。
    // 输入：nums = [1,3,4,2,2]
    // 输出：2

    public int findDuplicate(int[] nums) {
        int len = nums.length;
        for (int i = 0; i < len; i++) {
            while (nums[i] != i + 1) {
                if (nums[i] == nums[nums[i] - 1]) {
                    return nums[i];
                }
                swap(nums, i, nums[i] - 1);
            }
        }
        // 数组中没有重复的整数，测试用例错误
        return 0;
    }

    // LC422 给你一个长度为 n 的整数数组 nums ，其中 nums 的所有整数都在范围 [1, n] 内，且每个整数出现 一次 或 两次。请你找出所有出现 两次 的整数，并以数组形式返回。
    // 你必须设计并实现一个时间复杂度为 O(n) 且仅使用常量额外空间的算法解决此问题。
    // 输入：nums = [4,3,2,7,8,2,3,1]
    // 输出：[2,3]
    public List<Integer> findDuplicates(int[] nums) {
        List<Integer> duplicates = new ArrayList<>();
        for(int i=0; i<nums.length; i++){
            while(nums[i] != i+1 && nums[i] != nums[nums[i]-1]){
                swap(nums, i, nums[i]-1);
            }
        }
        // 上面一步得到处在正确位置的数组，重复的元素随机处在空出来的位置
        // 找出出现两次的整数
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i + 1) {
                duplicates.add(nums[i]);
            }
        }
        return duplicates;
    }

    private void swap(int[] nums, int index1, int index2) {
        int temp = nums[index1];
        nums[index1] = nums[index2];
        nums[index2] = temp;
    }
}
