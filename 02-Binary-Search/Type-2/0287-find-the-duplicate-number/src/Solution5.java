import java.util.HashMap;
import java.util.Map;

public class Solution5 {

    // 原地哈希（不符合题目要求）

    public int findDuplicate(int[] nums) {
        Map<Integer, Integer> numCounts = new HashMap<>();

        for (int num : nums) {
            if (numCounts.containsKey(num)) {
                return num;
            }
            numCounts.put(num, numCounts.getOrDefault(num, 0) + 1);
        }
        return 0;
    }
    public int findDuplicate1(int[] nums) {
        boolean[] seen= new boolean[nums.length];
        for (int num:nums) {
            if (seen[num]) return num;
            seen[num]=true;
        }
        return -1;
    }

}