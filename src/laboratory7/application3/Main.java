package laboratory7.application3;

import java.util.concurrent.CountDownLatch;

class ExecutionMainThread extends Thread {
    Integer monitor;
    CountDownLatch countDownLatch;
    int sleep_min, sleep_max, activity_min, activity_max;

    public ExecutionMainThread(String name, Integer monitor, CountDownLatch countDownLatch, int sleep_min, int sleep_max, int activity_min, int activity_max) {

        super(name);
        this.monitor = monitor;
        this.countDownLatch = countDownLatch;
        this.sleep_min = sleep_min;
        this.sleep_max = sleep_max;
        this.activity_min = activity_min;
        this.activity_max = activity_max;
    }

    public void run() {
        System.out.println(this.getName() + " -STATE 1");
        try {
            Thread.sleep(Math.round(Math.random() * (sleep_max - sleep_min) + sleep_min) * 500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(this.getName() + " - STATE 2");
        int k = (int) Math.round(Math.random() * (activity_max - activity_min) + activity_min);
        for (int i = 0; i < k * 100000; i++) {
            i++;
            i--;
        }
        synchronized (monitor) {
            monitor.notifyAll();
        }


        System.out.println(this.getName() + " -STATE 3");
        countDownLatch.countDown();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

class ExecutionSecThread extends Thread {
    CountDownLatch countDownLatch;
    Integer monitor;
    int activity_min, activity_max;


    public ExecutionSecThread(String name, Integer monitor, CountDownLatch countDownLatch, int activity_min, int activity_max) {
        super(name);
        this.countDownLatch = countDownLatch;
        this.monitor = monitor;
        this.activity_min = activity_min;
        this.activity_max = activity_max;
    }

    public void run() {
        System.out.println(this.getName() + " -STATE 1");
        synchronized (monitor) {
            try {
                monitor.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(this.getName() + " -STATE 2");
        int k = (int) Math.round(Math.random() * (activity_max - activity_min) + activity_min);
        for (int i = 0; i < k * 100000; i++) {
            i++;
            i--;
        }
        System.out.println(this.getName() + " -STATE 3");

        countDownLatch.countDown();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        Integer monitor = 1;

        new ExecutionMainThread("ThreadPrim0", monitor, countDownLatch, 0, 7, 2, 3).start();
        new ExecutionSecThread("ThreadSec1", monitor, countDownLatch, 3, 5).start();
        new ExecutionSecThread("ThreadSec2", monitor, countDownLatch, 4, 6).start();
    }
}