package n12_01_package;

public class DivideEqualArray {
    // LC416. 分割等和子集
    // 给你一个 只包含正整数 的 非空 数组 nums 。请你判断是否可以将这个数组分割成两个子集，使得两个子集的元素和相等。
    // 输入：nums = [1,5,11,5]
    // 输出：true
    // 解释：数组可以分割成 [1, 5, 5] 和 [11] 。

    public boolean canPartition(int[] nums) {
        int total = 0;
        for(int x: nums) total += x;
        if(total % 2 == 1) return false;
        int len = nums.length;
        total /= 2;
        boolean[][] arr = new boolean[len+1][total+1];
        arr[0][0] = true;
        for(int i=0; i<len; i++){
            int x = nums[i];
            for(int j=0; j<=total; j++){
                arr[i + 1][j] = j >= x && arr[i][j - x] || arr[i][j];
            }

        }
        return arr[len][total];
    }
}
