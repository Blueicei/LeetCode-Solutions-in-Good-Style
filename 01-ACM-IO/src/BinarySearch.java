import java.util.function.IntFunction;

public class BinarySearch {
    // 二分的中心思想就是减而治之，判断完全不可能的边界舍弃掉，一层循环可以提前break，复杂度log_n。不同于分治
    public int search(int[] nums, int target) {
        int left = 0, right = nums.length;
        while(left<right){
            int mid = (left + right) >>> 1;
            if(target > nums[mid]){
                left = mid + 1;
            } else if(target == nums[mid]){
                return mid;
            }else {
                right = mid;
            }
        }
        return -1;
    }

    // 关键在于right不可达且mid偏左，那么移动的重任就叫给right也就是（==），向下取整同时要避开left==target的情况
    // 二分下取整，right=mid保证每一步都在动，不会陷入死循环，二元落左就移右
    // 落左移右就是把判断相等的条件归为移右的操作上
    public int findPosition(int[] nums, int target) {
        int left = 0, right = nums.length;
        while(left<right){
            int mid = (left + right) >>> 1;
            if(target > nums[mid]){
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }

    // 852.平衡树
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

    public static void main(String[] args) {
        int[] nums = {1,3,5,5,6,-1,2,2};
        // lambda
        IntFunction<Integer> binarySearch = (int target) -> {
            int left = 0, right = nums.length;
            while(left<right){
                int mid = (left + right) >>> 1;
                if(target > nums[mid]){
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }
            return left;
        };
        System.out.println(binarySearch.apply(0));
    }
}
