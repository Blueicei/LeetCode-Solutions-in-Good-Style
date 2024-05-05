package n18_real_test;
import java.util.*;
public class DiceRotate {

    // 4.多多的骰子组合
    //多多君拼团购买了N个骰子，为了方便后面进行活动，多多君需要将这些骰子进行分类。
    // 两个骰子为同类的定义是：将其中一个骰子通过若干次上下、左右或前后翻转后，其与另一个骰子对应的6面数字
    // 现在多多君想知道不同种类的骰子的数量分别有多少。
    // 输入描述：
    //第一行1个整数N，表示骰子的数量。
    //（1 <= N <= 1,000）
    //接下来N行，每行6个数字（1～6，且各不相同）
    //其中第i行表示第i个骰子当前上、下、左、右、前、后这6面的数字。
    // 输出描述：
    //共2行:
    //第一行1个整数M，表示不同种类的骰子的个数
    //第二行M个整数，由大到小排序，表示每个种类的骰子的数量
    // 输入例子：
    //2
    //1 2 3 4 5 6
    //1 2 6 5 3 4
    //输出例子：
    //1
    //2

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[][] matrix = new int[n][6];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 6; j++) {
                matrix[i][j] = sc.nextInt();
            }
        }

        getDiffNum(matrix, n);
    }

    private static void getDiffNum(int[][] matrix, int n) {
        for (int i = 0; i < n; i++) {
            // 将数据全转成 1 在上且左侧比右侧小的排序
            int[] nums = matrix[i];
            for (int j = 0; j < 6; j++) {
                if (1 == nums[j]) {
                    rotate(nums, j);
                }
            }
        }

        Map<String, Integer> map = new HashMap<>();
        // 进行次数统计
        for (int i = 0; i < n; i++) {
            int[] nums = matrix[i];
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < 6; j++) {
                sb.append(nums[j]);
            }

            String key = sb.toString();
            if (map.containsKey(key)) {
                map.put(key, 1 + map.get(key));
            } else {
                map.put(key, 1);
            }
        }

        System.out.println(map.size());
        map.values().stream().sorted((t1, t2) -> {
            return t2 - t1;
        }).forEach(item -> {
            System.out.print(item + " ");
        });
        System.out.println();
    }

    private static void rotate(int[] nums, int pos) {
        if (1 == pos) {
            swap(nums, 0, 1);
            swap(nums, 4, 5);
        } else if (2 == pos) {
            swap(nums, 0, 2);
            swap(nums, 1, 3);
            swap(nums, 2, 3);
        } else if (3 == pos) {
            swap(nums, 0, 2);
            swap(nums, 1, 3);
            swap(nums, 0, 1);
        } else if (4 == pos) {
            swap(nums, 0, 4);
            swap(nums, 1, 5);
            swap(nums, 4, 5);
        } else if (5 == pos) {
            swap(nums, 0, 4);
            swap(nums, 1, 5);
            swap(nums, 0, 1);

        }
        rotateLeftMin(nums);
    }

    private static void rotateLeftMin(int[] nums) {
        int idx = -1, min = Integer.MAX_VALUE;
        for (int i = 2; i < 6; i++) {
            if (min > nums[i]) {
                min = nums[i];
                idx = i;
            }
        }

        rotate2(nums, idx);
    }

    /**
     * 将第三个位置置为后四个最小值
     * @param nums
     * @param idx
     */
    private static void rotate2(int[] nums, int idx) {
        if (3 == idx) {
            swap(nums, 2,3);
            swap(nums, 4,5);
        } else if (4 == idx) {
            swap(nums, 2,4);
            swap(nums, 3,5);
            swap(nums, 4,5);
        } else if (5 == idx) {
            swap(nums, 2,4);
            swap(nums, 3,5);
            swap(nums, 2,3);
        }
    }


    private static void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}
