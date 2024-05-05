package n2_remove_single_pointer_loop_invariant;

public class RemoveDuplicates {
    // 循环不变量， 单指针删除元素
    // LC26 原地删除重复出现的元素，使每个元素只出现一次
    // 给你一个 非严格递增排列 的数组 nums ，请你 原地 删除重复出现的元素，使每个元素 只出现一次 ，返回删除后数组的新长度。元素的 相对顺序 应该保持 一致 。然后返回 nums 中唯一元素的个数。
    //考虑 nums 的唯一元素的数量为 k ，你需要做以下事情确保你的题解可以被通过：
    //更改数组 nums ，使 nums 的前 k 个元素包含唯一元素，并按照它们最初在 nums 中出现的顺序排列。nums 的其余元素与 nums 的大小不重要。
    //返回 k 。

    public int removeDuplicates(int[] nums) {
        int len = nums.length;
        if (len < 2) {
            return len;
        }
        // 循环不变量：nums[0..j）是移除重复元素以后的数组
        int j = 1;
        for (int i = 1; i < len; i++) {
            if (nums[i] != nums[j - 1]) {
                // 注意顺序：先更新值，再递增下标
                nums[j] = nums[i];
                j++;
            }
        }
        return j;
    }

    // LC80 原地删除重复出现的元素，使得出现次数超过两次的元素只出现两次
    public int removeDoubleDuplicates(int[] nums) {
        int len = nums.length;
        if (len < 2){
            return len;
        }

        // 循环不变量：nums[0..j) 是有序的，并且相同元素最多保留 2 次
        // j 指向下一个要赋值的元素的位置
        int j = 2;
        for (int i = 2; i < len; i++) {
            if (nums[i] != nums[j - 2]){
                nums[j] = nums[i];
                j++;
            }
        }
        return j;
    }
}
