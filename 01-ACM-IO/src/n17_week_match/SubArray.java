package n17_week_match;

public class SubArray {
    // LC3209. 子数组按位与值为 K 的数目
    // 给你一个整数数组 nums 和一个整数 k ，请你返回 nums 中有多少个子数组 满足：子数组中所有元素按位 AND 的结果为 k 。
    // 输入：nums = [1,1,1], k = 1
    // 输出：6

    // 超时
    public long countSubarrays(int[] nums, int k) {
        int len = nums.length;
        int res = 0;
        for(int i=0; i<len; i++){
            int tmp = nums[i];
            for(int j=i; j<len; j++){
                if(i != j) tmp &= nums[j];
                res += (tmp == k)?1:0;
            }
        }
        return res;
    }
    // 前面的循环保证了每个集合都是其左侧相邻集合的超集
    public long countSubarrays2(int[] nums, int k) {
        long ans = 0;
        int cnt = 0;
        for (int i = 0; i < nums.length; i++) {
            int x = nums[i];
            cnt += x == k ? 1 : 0;
            for (int j = i - 1; j >= 0 && (nums[j] & x) != nums[j]; j--) {
                cnt -= nums[j] == k ? 1 : 0;
                nums[j] &= x;
                cnt += nums[j] == k ? 1 : 0;
            }
            ans += cnt;
        }
        return ans;
    }

}
