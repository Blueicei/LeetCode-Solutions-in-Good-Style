package n18_real_test;
import java.util.*;
import java.io.*;

public class MaxMinSubArrangeSum {
    // 问题描述/LYA 是一位园艺爱好者，她在自家后院设计了一个长条形花园。花园里种植了两种花卉：红玫瑰和白牡丹。这些花按照一定顺序排列，从左到右编号为 1 到 n。
    //LYA 定义花园的"和谐度"为红玫瑰与白牡丹数量之差的绝对值。她想尝试一种特殊的园艺技巧：选择一段连续的花卉区间 ，将这个区间内的红玫瑰全部替换为白牡丹，同时将白牡丹全部替换为红玫瑰。
    //LYA 对花园的和谐度高低并不在意，她更关心花园可能呈现出多少种不同的和谐度。她想知道，如果最多进行一次这样的替换操作，花园一共会有多少种不同的和谐度。
    //输入格式
    //输入共两行：
    //第一行是一个整数n，表示花园中花卉的总数。
    //第二行包含n个数字，每个数字代表一株花卉的类型，0 表示红玫瑰，1 表示白牡丹。
    //输出格式
    //输出一行，包含一个整数，表示经过至多一次替换操作后，花园可能呈现的不同和谐度的数量。
    //样例输入 1
    //2
    //0 1
    //样例输出 1
    //2

    // 这题可以用 最大/最小子数组和 来解决
    //可以将红玫瑰看作 -1，白牡丹看作 1。
    //这样，花园的和谐度就等于所有花卉值的总和的绝对值。
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 读取输入
        int n = Integer.parseInt(br.readLine());
        String[] flowerStr = br.readLine().split(" ");

        int[] flowers = new int[n];
        int totalSum = 0;

        // 转换花卉类型并计算总和
        for (int i = 0; i < n; i++) {
            flowers[i] = flowerStr[i].equals("0") ? -1 : 1;
            totalSum += flowers[i];
        }

        // 初始化动态规划数组
        int[] dpMin = new int[n + 1];
        int[] dpMax = new int[n + 1];
        int minSum = 0, maxSum = 0;

        // 动态规划计算最小和最大子数组和
        for (int i = 0; i < n; i++) {
            dpMin[i + 1] = Math.min(dpMin[i] + flowers[i], flowers[i]);
            dpMax[i + 1] = Math.max(dpMax[i] + flowers[i], flowers[i]);
            minSum = Math.min(minSum, dpMin[i + 1]);
            maxSum = Math.max(maxSum, dpMax[i + 1]);
        }

        // 计算所有可能的和谐度
        Set<Integer> harmonySet = new HashSet<>();
        for (int i = minSum; i <= maxSum; i++) {
            harmonySet.add(Math.abs(totalSum - 2 * i));
        }

        // 输出结果
        System.out.println(harmonySet.size());
    }
}
