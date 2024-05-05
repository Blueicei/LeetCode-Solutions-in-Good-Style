package n0_cocurrent;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.*;
public class ProducerConsumerExample {
    private static final int MAX_SIZE = 10; // 缓冲区最大容量
    private final Queue<Integer> buffer = new LinkedList<>();
    private final Semaphore empty = new Semaphore(MAX_SIZE); // 代表空位的信号量
    private final Semaphore full = new Semaphore(0); // 代表已填充的信号量
    private final Semaphore mutex = new Semaphore(1); // 互斥信号量

    // 生产者
    class Producer implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    produce();
                    Thread.sleep(1000); // 模拟生产时间
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        private void produce() throws InterruptedException {
            int item = (int) (Math.random() * 100); // 生产一个随机整数
            empty.acquire(); // 等待空位
            mutex.acquire(); // 获取互斥锁

            buffer.add(item);
            System.out.println("Produced: " + item);

            mutex.release(); // 释放互斥锁
            full.release(); // 增加已填充的信号量
        }
    }

    // 消费者
    class Consumer implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    consume();
                    Thread.sleep(1500); // 模拟消费时间
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        private void consume() throws InterruptedException {
            full.acquire(); // 等待已填充
            mutex.acquire(); // 获取互斥锁

            int item = buffer.poll(); // 从缓冲区取出数据
            System.out.println("Consumed: " + item);

            mutex.release(); // 释放互斥锁
            empty.release(); // 增加空位的信号量
        }
    }

    public static void main(String[] args) {
        ProducerConsumerExample pc = new ProducerConsumerExample();

        Thread producerThread = new Thread(pc.new Producer());
        Thread consumerThread = new Thread(pc.new Consumer());

        producerThread.start();
        consumerThread.start();
    }
}
