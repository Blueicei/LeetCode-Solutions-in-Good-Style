import java.util.*;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HalfSearch {
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

    //关键在于right不可达且mid偏左，那么移动的重任就叫给right也就是（==），向下取整同时要避开left==target的情况
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

    public static void main(String[] args) {
        int[] nums = {1,3,5,5,6,-1,2,2};
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
        List<Integer> list = Arrays.stream(nums).boxed().collect(Collectors.toList());
//        list = list.stream().distinct().collect(Collectors.toList());
//        list = list.stream().map(x -> x*2).collect(Collectors.toList());
//        list = list.stream().flatMap(x -> Stream.of(x*2)).collect(Collectors.toList());
//        list = list.stream().filter(x -> x>5).collect(Collectors.toList());
//        list = list.stream().skip(1).limit(1).collect(Collectors.toList());
//        list.sort(Comparator.naturalOrder());
        list.sort((x,y)->Integer.compare(y,x));
        String[] strs = {"12", "23", "33", "34", "35"};
        String oneLine = String.join(" ", strs);
        List<Integer> listStr = Arrays.stream(strs).map(Integer::parseInt).collect(Collectors.toList());
        listStr.stream().forEach(x -> System.out.println(x));
        String streamOneLine = listStr.stream().map(Object::toString).collect(Collectors.joining(" "));
        int sum = Arrays.stream(nums).reduce(0, (a, b) -> a + b*2);
        int sum1 = listStr.stream().reduce(0, (subtotal, element) -> subtotal + element);

        // List转int[]数组的方法
//        int[] arrays = list.stream().mapToInt(Integer::intValue).toArray();
//        int[] arrays = list.stream().mapToInt((Integer i)->i).toArray();
//        int[] arrays = list.stream().filter(integer -> integer!=null).mapToInt(i->i).toArray();

        System.out.println(binarySearch.apply(0));
        System.out.println(list);
        System.out.println(listStr);
        System.out.println(oneLine);
        System.out.println(streamOneLine);
        System.out.println(sum);
    }
}
