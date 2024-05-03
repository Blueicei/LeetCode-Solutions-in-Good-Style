public class ReversePairs {
    /* 合并左子数组和右子数组 */
    public int merge(int[] nums, int left, int mid, int right) {
        int res = 0;
        int i = left, j = mid + 1, k = 0;
        int[] tmp = new int[right - left + 1];
        // 当左右子数组都还有元素时，进行比较并将较小的元素复制到临时数组中
        while (i <= mid && j <= right) {
            if (nums[i] > nums[j]) {
                tmp[k++] = nums[i++];
                res += right - j + 1;
            } else {
                tmp[k++] = nums[j++];
            }

        }
        while (i <= mid) {
            tmp[k++] = nums[i++];
        }
        while (j <= right) {
            tmp[k++] = nums[j++];
        }
        // 将临时数组 tmp 中的元素复制回原数组 nums 的对应区间
        for (k = 0; k < tmp.length; k++) {
            nums[left + k] = tmp[k];
        }
        return res;
    }

    /* 归并排序 */
    public int mergeSort(int[] nums, int left, int right) {
        int res = 0;
        // 终止条件
        if (left >= right)
            return res; // 当子数组长度为 1 时终止递归
        // 划分阶段
        int mid = (left + right) / 2; // 计算中点
        res += mergeSort(nums, left, mid); // 递归左子数组
        res += mergeSort(nums, mid + 1, right); // 递归右子数组
        // 合并阶段
        res += merge(nums, left, mid, right);
        return res;
    }

    public int reversePairs(int[] record) {
        int len = record.length;
        return mergeSort(record, 0, len - 1);
    }
}
