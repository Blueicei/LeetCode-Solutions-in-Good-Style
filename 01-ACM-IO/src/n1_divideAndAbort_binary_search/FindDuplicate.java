package n1_divideAndAbort_binary_search;

public class FindDuplicate {
    // 二分确定一个有范围的整数（二分答案）减而治之。如果题目要我们找一个整数，这个整数有确定的范围，可以通过二分查找逐渐缩小范围，最后逼近到一个数。
    // 找mid数不一定要取(left+right)//2，根据题意找到可以判别另一侧无可能的点
    // LC287 寻找重复数:给定一个包含 n + 1 个整数的数组 nums ，其数字都在 [1, n] 范围内（包括 1 和 n），可知至少存在一个重复的整数。
    // 假设 nums 只有 一个重复的整数 ，返回 这个重复的数 。
    // 你设计的解决方案必须 不修改 数组 nums 且只用常量级 O(1) 的额外空间。
    // 输入：nums = [1,3,4,2,2]
    // 输出：2

    public int findDuplicate(int[] nums) {
        int len = nums.length;
        int left = 1;
        int right = len - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            int cnt = 0;
            for (int num : nums) {
                if (num <= mid) {
                    cnt += 1;
                }
            }

            // 根据抽屉原理，小于等于 4 的个数如果严格大于 4 个
            // 此时重复元素一定出现在 [1, 4] 区间里
            if (cnt > mid) {
                // 重复元素位于区间 [left, mid]
                right = mid;
            } else {
                // if 分析正确了以后，else 搜索的区间就是 if 的反面
                // [mid + 1, right]
                left = mid + 1;
            }
        }
        return left;
    }
}
