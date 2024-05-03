public class Solution1 {
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
