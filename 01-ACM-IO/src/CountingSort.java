public class CountingSort {
    // LC0075
    public void sortColors(int[] nums) {
        int[] colors = {0,0,0};
        for(int i=0; i<nums.length;i++){
            colors[nums[i]]++;
        }
        int k = 0;
        for(int i=0; i<colors.length; i++){
            while(colors[i]>0){
                nums[k++] = i;
                colors[i]--;
            }
        }
    }
}
