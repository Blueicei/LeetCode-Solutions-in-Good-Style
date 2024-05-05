package n0_cocurrent;

public class UnsafeThreadExample {
    private static volatile int counter = 0;

    public static void test() throws InterruptedException {
        counter = 0;
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter++;
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter++;
            }
        });

        Thread thread3 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter++;
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();

        System.out.println("Final counter value: " + counter);
    }

    public static void main(String[] args) throws InterruptedException {
        for(int i=0; i<100; i++) {
            test();
        }
    }
}