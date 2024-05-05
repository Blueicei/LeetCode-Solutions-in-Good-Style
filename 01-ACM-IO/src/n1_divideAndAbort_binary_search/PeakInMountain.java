package n1_divideAndAbort_binary_search;

public class PeakInMountain {
    // 二分的中心思想就是减而治之，判断完全不可能的边界舍弃掉，一层循环可以提前break，复杂度log_n。不同于分治
    // 852.平衡树 给你由整数组成的山脉数组 arr ，返回满足 arr[0] < arr[1] < ... arr[i - 1] < arr[i] > arr[i + 1] > ... > arr[arr.length - 1] 的下标 i 。
    // 输入：arr = [0,1,0]
    // 输出：1
    public int peakIndexInMountainArray(int[] arr) {
        int left = 0, right = arr.length;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if(arr[mid-1]<=arr[mid] && arr[mid] >= arr[mid+1]) {
                return mid;
            } else if(arr[mid]>=arr[mid-1]){
                left = mid + 1;
            } else{
                right = mid;
            }
        }
        return left;
    }

}
