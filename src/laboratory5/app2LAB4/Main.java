package laboratory5.app2LAB4;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ExecutionThread extends Thread {
    Lock lock1;
    Lock lock2;

    int sleep_min, sleep_max, activity1_min, activity1_max, activity2_min, activity2_max;

    public ExecutionThread(Lock lock1, Lock lock2, int sleep_min,
                           int sleep_max, int activity1_min, int activity1_max, int activity2_min, int activity2_max) {
        this.lock1 = lock1;
        this.lock2 = lock2;
        this.sleep_min = sleep_min;
        this.sleep_max = sleep_max;
        this.activity1_min = activity1_min;
        this.activity1_max = activity1_max;
        this.activity2_min = activity2_min;
        this.activity2_max = activity2_max;
    }

    public void run() {

        System.out.println(this.getName() + " - STATE 1");
        int k = (int) Math.round(Math.random() * (activity1_max - activity1_min) + activity1_min);
        for (int i = 0; i < k * 100000; i++) {
            i++;
            i--;
        }

        try {
            if (this.lock1.tryLock(500000, TimeUnit.MILLISECONDS)) {
                try {
                    System.out.println(this.getName() + " - STATE 2");
                    int k2 = (int) Math.round(Math.random() * (activity2_max - activity2_min) + activity2_min);
                    for (int i = 0; i < k2 * 100000; i++) {
                        i++;
                        i--;
                    }

                    if (this.lock2.tryLock(500000, TimeUnit.MILLISECONDS)) {
                        try {
                            System.out.println(this.getName() + " - STATE 3");

                            try {
                                Thread.sleep(Math.round(Math.random() * (sleep_max - sleep_min) + sleep_min) * 500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        } finally {
                            this.lock2.unlock();
                        }
                    }
                } finally {
                    this.lock1.unlock();
                }

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(this.getName() + " - STATE 4");
    }
}
public class Main {
    public static void main(String[] args) {
        Lock lock1= new ReentrantLock();
        Lock lock2= new ReentrantLock();

        new ExecutionThread(lock1, lock2, 0, 4, 2, 4, 4, 6).start();
        new ExecutionThread(lock1, lock2, 3, 5, 3, 5, 5, 7).start();
    }
}