package laboratory7.application2.Semaphore;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

class ExecutionThread extends Thread {
    CyclicBarrier barrier;
    Semaphore semaphore1;
    Semaphore semaphore2;
    int sleep_min, sleep_max, activity_min, activity_max;

    public ExecutionThread(CyclicBarrier barrier, Semaphore semaphore1, Semaphore semaphore2, int sleep_min, int sleep_max, int activity_min, int activity_max) {
        this.barrier = barrier;
        this.semaphore1 = semaphore1;
        this.semaphore2 = semaphore2;
        this.sleep_min = sleep_min;
        this.sleep_max = sleep_max;
        this.activity_min = activity_min;
        this.activity_max = activity_max;
    }

    public void run() {
        while (true) {
            System.out.println(this.getName() + " - STATE 1");

            if (this.getName().equals("Thread-2")) {
                try {
                    semaphore1.acquire();
                    {
                        try {
                            semaphore2.acquire();
                            {
                                System.out.println(this.getName() + " - STATE 2");
                                int k = (int) Math.round(Math.random() * (activity_max - activity_min) + activity_min);
                                for (int i = 0; i < k * 100000; i++) {
                                    i++;
                                    i--;
                                }

                                try {
                                    Thread.sleep(Math.round(Math.random() * (sleep_max - sleep_min) + sleep_min) * 500);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            semaphore2.release();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore1.release();
                }

            } else {
                try {
                    semaphore2.acquire();

                    {
                        System.out.println(this.getName() + " - STATE 2");
                        int k = (int) Math.round(Math.random() * (activity_max - activity_min) + activity_min);
                        for (int i = 0; i < k * 100000; i++) {
                            i++;
                            i--;
                        }

                        try {
                            Thread.sleep(Math.round(Math.random() * (sleep_max - sleep_min) + sleep_min) * 500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore2.release();
                }
            }
            System.out.println(this.getName() + " - STATE 3");
            System.out.println(this.getName() + " is waiting at the semaphore");
            try {
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(3);
        Semaphore semaphore1 = new Semaphore(1);
        Semaphore semaphore2 = new Semaphore(1);
        ExecutionThread t1 = new ExecutionThread(barrier, semaphore1, semaphore2, 0, 4, 2, 4);
        ExecutionThread t2 = new ExecutionThread(barrier, semaphore1, semaphore2, 0, 3, 3, 6);
        ExecutionThread t3 = new ExecutionThread(barrier, semaphore1, semaphore2, 0, 5, 2, 5);
        t1.start();
        t2.start();
        t3.start();
    }
}