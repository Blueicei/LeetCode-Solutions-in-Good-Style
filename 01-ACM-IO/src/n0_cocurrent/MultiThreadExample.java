package n0_cocurrent;

public class MultiThreadExample {

    public static void main(String[] args) {
        Thread thread1 = new Test();
        Thread thread2 = new Thread(new God());
        Thread thread3 = new Thread(()->{System.out.println("Thread3");});
        thread1.start();
        thread2.start();
        thread3.start();
    }
}

class Test extends Thread{
    @Override
    public void run() {
        System.out.println("你好啊");
    }
}
class God implements Runnable{

    @Override
    public void run() {
        System.out.println("上帝守护着你");

    }
}
