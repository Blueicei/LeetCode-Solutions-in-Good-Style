public class MySolution {

    void swap(int[] arr, int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    int partrition(int[] arr, int left, int right){
        int i = left, j = right;
        while(i<j){
            while(i<j&&arr[j]>=arr[left]){
                j--;
            }
            while(i<j&&arr[i]<=arr[left]){
                i++;
            }
            swap(arr, i, j);
        }
        swap(arr, i, left);
        return i;
    }

    void quickSort(int[] nums, int left, int right){
        if(left>=right){
            return;
        }
        int mid = partrition(nums, left, right);
        quickSort(nums, left, mid-1);
        quickSort(nums, mid+1, right);
    }

    public void sortColors(int[] nums) {
        quickSort(nums, 0, nums.length-1);
    }
}
