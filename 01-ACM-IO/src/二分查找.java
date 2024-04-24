import java.util.*;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class 二分查找 {
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
        int[] nums = {1,3,5,5,6};
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
        list = list.stream().distinct().collect(Collectors.toList());
        list = list.stream().map(x -> x*2).collect(Collectors.toList());
        list = list.stream().flatMap(x -> Stream.of(x*2)).collect(Collectors.toList());
        list = list.stream().filter(x -> x>5).collect(Collectors.toList());
        list = list.stream().skip(1).limit(1).collect(Collectors.toList());

        String[] strs = {"12", "23", "33", "34", "35"};
        String oneLine = String.join(" ", strs);
        List<Integer> listStr = Arrays.stream(strs).map(Integer::parseInt).collect(Collectors.toList());
        listStr.stream().forEach(x -> System.out.println(x));
        String streamOneLine = listStr.stream().map(Object::toString).collect(Collectors.joining(" "));
        int sum = Arrays.stream(nums).reduce(0, (a, b) -> a + b*2);
        int sum1 = listStr.stream().reduce(0, (subtotal, element) -> subtotal + element);
        System.out.println(binarySearch.apply(0));
        System.out.println(list);
        System.out.println(listStr);
        System.out.println(oneLine);
        System.out.println(streamOneLine);
        System.out.println(sum);
    }
}
