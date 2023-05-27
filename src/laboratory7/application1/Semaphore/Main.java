package laboratory7.application1.Semaphore;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;


class ExecutionThread extends Thread {
    Semaphore semaphore1;
    Semaphore semaphore2;
    CyclicBarrier barrier;
    int sleep_min, sleep_max, activity1_min, activity1_max, activity2_min, activity2_max;

    public ExecutionThread(CyclicBarrier barrier, Semaphore sem1, Semaphore sem2, int sleep_min,
                           int sleep_max, int activity1_min, int activity1_max, int activity2_min, int activity2_max) {
        this.barrier = barrier;
        this.semaphore1 = sem1;
        this.semaphore2 = sem2;
        this.sleep_min = sleep_min;
        this.sleep_max = sleep_max;
        this.activity1_min = activity1_min;
        this.activity1_max = activity1_max;
        this.activity2_min = activity2_min;
        this.activity2_max = activity2_max;
    }

    public void run() {

        while (true) {
            System.out.println(this.getName() + " - STATE 1");
            int k = (int) Math.round(Math.random() * (activity1_max - activity1_min) + activity1_min);
            for (int i = 0; i < k * 100000; i++) {
                i++;
                i--;
            }

            try {
                semaphore1.acquire();
                System.out.println(this.getName() + " - STATE 2");
                int k2 = (int) Math.round(Math.random() * (activity2_max - activity2_min) + activity2_min);
                for (int i = 0; i < k2 * 100000; i++) {
                    i++;
                    i--;
                }

                try {
                    semaphore2.acquire();
                    System.out.println(this.getName() + " - STATE 3");

                    try {
                        Thread.sleep(Math.round(Math.random() * (sleep_max - sleep_min) + sleep_min) * 500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore2.release();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                this.semaphore1.release();
            }

            System.out.println(this.getName() + " - STATE 4");

            try {
                System.out.println(this.getName() + " is waiting at the barrier");
                barrier.await();
            } catch (BrokenBarrierException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Semaphore semaphore1 = new Semaphore(1);
        Semaphore semaphore2 = new Semaphore(1);

        CyclicBarrier barrier = new CyclicBarrier(2);
        new ExecutionThread(barrier, semaphore1, semaphore2, 0, 4, 2, 4, 4, 6).start();
        new ExecutionThread(barrier, semaphore1, semaphore2, 0, 5, 3, 5, 5, 7).start();
    }
}