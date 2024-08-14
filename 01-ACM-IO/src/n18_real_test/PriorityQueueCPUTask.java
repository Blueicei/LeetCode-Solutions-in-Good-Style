package n18_real_test;

import java.util.PriorityQueue;
import java.util.Scanner;

public class PriorityQueueCPUTask {
    // 问题描述: LYA是一家科技公司的项目经理，她负责管理多个并行项目。公司最近开发了一个新的任务管理系统，可以帮助LYA更高效地分配和完成任务。
    //系统有以下特点：
    //同一时刻只能处理一个任务。
    //任务可以在任意时刻开始、暂停和恢复（前提是该任务已开始且未完成）。
    //每个任务有两个关键属性：创建时间 t_i和所需时间 w_i
    //任务的完成时间计算方式为：最后完成时刻减去任务创建时间
    //LYA希望能够最优化任务处理顺序，使得所有任务的完成时间之和最小。你能帮助她设计一个算法来实现这个目标吗？
    //输入格式
    //第一行包含一个整数n表示任务的总数。
    //接下来的 n 行，每行包含两个整数 t_i和w_i
    //输出格式
    //输出一个整数，表示所有任务的最小完成时间之和。
    //样例输入1
    //3
    //1 5
    //5 1
    //7 3
    //样例输出1
    //10

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[][] task = new int[n][2];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 2; j++) {
                task[i][j] = in.nextInt();
            }
        }
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> (a[1] == b[1] ? a[0] - b[0] : a[1] - b[1]));
        long ans = 0, cur = 0;  // cur表示当前时间
        for (int i = 0; i < n - 1; i++) {
            cur = task[i][0];
            pq.offer(task[i]);
            int interval = task[i + 1][0] - task[i][0];  // 距离下一项作业被布置的时间
            while (interval > 0 && !pq.isEmpty()) {
                int[] t = pq.peek();
                if (interval >= t[1]) {
                    pq.poll();
                    cur += t[1];
                    ans += (cur - t[0]);
                    interval -= t[1];
                } else {
                    t[1] -= interval;
                    interval = 0;
                }
            }
        }
        cur = task[n - 1][0];
        pq.offer(task[n - 1]);
        while (!pq.isEmpty()) {
            int[] t = pq.poll();
            cur += t[1];
            ans += (cur - t[0]);
        }
        System.out.println(ans);
    }

}
