package n4_divide_3range_quick_sort;

public class OneEpoch {
    // QuickSort LC0075 给定一个包含红色、白色和蓝色、共 n 个元素的数组 nums ，原地对它们进行排序，使得相同颜色的元素相邻，并按照红色、白色、蓝色顺序排列。
    // 我们使用整数 0、 1 和 2 分别表示红色、白色和蓝色。
    // 输入：nums = [2,0,2,1,1,0]
    // 输出：[0,0,1,1,2,2]


    public void sortColors(int[] nums) {
        int len = nums.length;
        if (len < 2) {
            return;
        }

        // 定义
        // all in [0, zero) = 0
        // all in [zero, i) = 1
        // all in [two, len) = 2

        // 初始化
        int zero = 0;
        int i = 0;
        int two = len ;

        // 循环终止的条件是 i == two

        while (i < two) {
            if (nums[i] == 0) {
                swap(nums, zero, i);
                zero++;
                i++;
            } else if (nums[i] == 1) {
                i++;
            } else {
                two--;
                swap(nums, i, two);
            }
        }
    }

    private void swap(int[] nums, int index1, int index2) {
        int temp = nums[index1];
        nums[index1] = nums[index2];
        nums[index2] = temp;
    }
}