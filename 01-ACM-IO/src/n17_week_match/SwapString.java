package n17_week_match;

public class SwapString {
    // LC3216. 交换后字典序最小的字符给你一个仅由数字组成的字符串 s，在最多交换一次 相邻 且具有相同 奇偶性 的数字后，返回可以得到的字典序最小的字符串
    //如果两个数字都是奇数或都是偶数，则它们具有相同的奇偶性。例如，5 和 9、2 和 4 奇偶性相同，而 6 和 9 奇偶性不同。
    // 输入： s = "45320"
    // 输出： "43520"

    public String getSmallestString(String s) {
        char[] arr = s.toCharArray();
        int prev = Integer.valueOf(arr[0]);
        for(int i=1; i<arr.length; i++){
            int cur = Integer.valueOf(arr[i]);
            if(prev > cur && cur%2==prev%2){
                swap(arr, i, i-1);
                break;
            }
            prev = cur;
        }
        return String.valueOf(arr);
    }

    public void swap(char[] arr, int cur, int prev){
        char tmp = arr[cur];
        arr[cur] = arr[prev];
        arr[prev] = tmp;
    }
}
