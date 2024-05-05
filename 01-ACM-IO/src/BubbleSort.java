public class BubbleSort {
    // 从后往前遍历，再从前往后交换，把两两之间较大的依次交换上来，尾部绝对有序
    //
    public int[] sortArray(int[] nums) {
        int len = nums.length;
        for (int i = len - 1; i >= 0; i--) {
            // 先默认数组是有序的，只要发生一次交换，就必须进行下一轮比较
            boolean sorted = true;
            for (int j = 0; j < i; j++) {
                if (nums[j] > nums[j + 1]) {
                    swap(nums, j, j + 1);
                    sorted = false;
                }
            }

            // 如果在内层循环中，都没有执行一次交换操作，说明此时数组已经是升序数组
            if (sorted) {
                break;
            }
        }
        return nums;
    }

    private void swap(int[] nums, int index1, int index2) {
        int temp = nums[index1];
        nums[index1] = nums[index2];
        nums[index2] = temp;
    }
}
