package n0_cocurrent;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> task1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("Task 1 is running...");
            return "Task 1 Result";
        });

        CompletableFuture<String> task2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("Task 2 is running...");
            return "Task 2 Result";
        });

        CompletableFuture<String> combinedTask = task1.thenCombine(task2, (result1, result2) -> {
            System.out.println("Task 3 is running after Task 1 and Task 2 are done...");
            return result1 + " " + result2 + " -> Task 3 Result";
        });

        // 等待任务完成并获取结果
        String finalResult = combinedTask.get();
        System.out.println(finalResult);
    }
}
