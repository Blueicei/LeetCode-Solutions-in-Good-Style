public class QuickSort {
    // 快排，最好，平均都是nlog_n，最差情况(n^2通常不会触发)，递归调用栈O(log_n)，最差O(n)
    // 为什么选择(平均log_n，最差情况很难出现，连续子串增加缓存命中率，原子操作少=局部性原理&对比merge_sort&空间开销O(n))
    // 优化策略，尾递归优化(即先完成小子串入栈出栈，降低大子串递归中伴生的小子串栈帧数)，基准数优化(选取三个候选元素的中位数作为基准数)
    // 非稳定排序：在每次哨兵划分的最后一步，若等于基准数，交换顺序打乱相对顺序
    // 三路快排

    /* 快速排序 */
    void quickSort(int[] nums, int left, int right) {
        // 子数组长度为 1 时终止递归
        if (left >= right)
            return;
        // 哨兵划分
        int pivot = partition(nums, left, right);
        // 递归左子数组、右子数组
        quickSort(nums, left, pivot - 1);
        quickSort(nums, pivot + 1, right);
    }
    /* 哨兵划分 */
    int partition(int[] nums, int left, int right) {
        // 以 nums[left] 为基准数, i=left不需要换的时候就跟自己交换，配合先判断j也是
        int i = left, j = right;
        while (i < j) {
            while (i < j && nums[j] >= nums[left])
                j--;          // 从右向左找首个小于基准数的元素
            while (i < j && nums[i] <= nums[left])
                i++;          // 从左向右找首个大于基准数的元素
            swap(nums, i, j); // 交换这两个元素
        }
        swap(nums, i, left);  // 将基准数交换至两子数组的分界线
        return i;             // 返回基准数的索引
    }
    /* 元素交换 */
    void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

}
