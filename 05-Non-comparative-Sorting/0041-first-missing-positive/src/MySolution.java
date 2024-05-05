public class MySolution {
    // lc41. 缺失的第一个正数.给你一个未排序的整数数组 nums ，请你找出其中没有出现的最小的正整数。输入：nums = [1,2,0] 输出：3
    // 要求时间复杂度为 O(n) 并且只使用常数级别额外空间的解决方案
    // 常数空间只能swap，需要类似计数排序记录该位置是否出现过
    // 最小正整数决定了结果的取值范围在1-nums.length，不符合取值范围的数可以被覆盖
    // 重点就在顺序遍历+swap，注意swap终止的条件以及处理死循环，必要时确实要往前换所以对于换过来范围外的数就暂存

    void swap (int[] arr, int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public int firstMissingPositive(int[] nums) {
        int len = nums.length;
        for(int i=0;i<len;i++){
            while(nums[i] > 0 && nums[i] <= len && nums[i] != i+1 && nums[i] != nums[nums[i]-1]){
                swap(nums, i, nums[i]-1);
            }
        }
        System.gc(); // 空间复杂度提升至99.99%
        for(int i=0;i<len;i++){
            if(nums[i] != i+1){
                return i+1;
            }
        }
        return len+1;
    }
}
