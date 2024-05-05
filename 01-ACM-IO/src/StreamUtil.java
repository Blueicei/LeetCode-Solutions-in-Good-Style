import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class StreamUtil {
    public static void main(String[] args) {
        int[] nums = {1,2,3,4,5,6,7,8};
        int[] arr =new int[20];
        for (int i =0; i < arr.length; i++) {//把1~100填充进arr数组中去
            arr[i]=i+1;
        }
        int[] array = IntStream.rangeClosed(1, 100).toArray();

        // int[]数组转List
        List<Integer> list = Arrays.stream(nums).boxed().collect(Collectors.toList());
        list = list.stream().distinct().collect(Collectors.toList());
        list = list.stream().map(x -> x*2).collect(Collectors.toList());
        list = list.stream().flatMap(x -> Stream.of(x*2)).collect(Collectors.toList());
        list = list.stream().filter(x -> x>5).collect(Collectors.toList());
        list = list.stream().skip(1).limit(3).collect(Collectors.toList());

        list.sort(Comparator.naturalOrder());
        list.sort((x,y)->Integer.compare(y,x));


        String[] strs = {"12", "23", "33", "34", "35"};
        String oneLine = String.join(" ", strs);
        String[] strTmp = oneLine.split(" ");
        System.out.println(Arrays.toString(strTmp));
        List<Integer> listStr = Arrays.stream(strTmp).map(Integer::parseInt).collect(Collectors.toList());
        listStr.stream().forEach(x -> System.out.println(x));
        String streamOneLine = listStr.stream().map(Object::toString).collect(Collectors.joining(" "));

        // Merge Reduce
        int sum = Arrays.stream(nums).reduce(0, (a, b) -> a + b*2);
        int sum1 = listStr.stream().reduce(0, (subtotal, element) -> subtotal + element);

        // List转int[]数组
        array = list.stream().mapToInt(Integer::intValue).toArray();
        array = list.stream().mapToInt((Integer i)->i).toArray();
        array = list.stream().filter(integer -> integer!=null).mapToInt(i->i).toArray();

        System.out.println(list);
        System.out.println(listStr);
        System.out.println(oneLine);
        System.out.println(streamOneLine);
        System.out.println(sum);
    }
}
