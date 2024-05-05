package n0_cocurrent;

import java.util.concurrent.*;

public class ThreadPoolExample {
    public static void main(String[] args) {
        // 创建一个线程池
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // 创建 Callable 任务
        MyCallable task1 = new MyCallable(5);
        MyCallable task2 = new MyCallable(10);

        // 提交任务并获取 Future 对象
        Future<Integer> future1 = executorService.submit(task1);
        Future<Integer> future2 = executorService.submit(task2);

        try {
            // 获取结果
            Integer result1 = future1.get(); // 阻塞等待结果
            Integer result2 = future2.get(); // 阻塞等待结果
            System.out.println("任务 1 的结果: " + result1); // 25
            System.out.println("任务 2 的结果: " + result2); // 100
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            // 关闭线程池
            executorService.shutdown();
        }
    }
}

class MyCallable implements Callable<Integer> {
    private final int number;

    public MyCallable(int number) {
        this.number = number;
    }

    @Override
    public Integer call() throws Exception {
        // 模拟一些复杂的计算，返回结果为数字的平方
        Thread.sleep(1000); // 模拟延迟
        return number * number; // 返回平方
    }
}
