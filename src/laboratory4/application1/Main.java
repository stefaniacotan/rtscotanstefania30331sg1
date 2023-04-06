package laboratory4.application1;

class ExecutionThread extends Thread {
    Integer monitor;
    Integer monitor2;
    int sleep_min, sleep_max, activity_min, activity_max;

    public ExecutionThread(String name, Integer monitor, Integer monitor2, int sleep_min, int sleep_max, int activity_min, int activity_max) {
        super(name);
        this.monitor = monitor;
        this.monitor2 = monitor2;
        this.sleep_min = sleep_min;
        this.sleep_max = sleep_max;
        this.activity_min = activity_min;
        this.activity_max = activity_max;
    }

    public void run() {
        System.out.println(this.getName() + " - STATE 1");

        if (this.getName().equals("Thread-2")) {
            synchronized (monitor) {
                System.out.println("P9 and P10 are providing");
                synchronized (monitor2) {
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
            }
        } else {
            synchronized (monitor) {
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
        }
        System.out.println(this.getName() + " - STATE 3");
    }
}

public class Main {
    public static void main(String[] args) {
        Integer monitor = 1;
        Integer monitor2 = 1;
        new ExecutionThread("Thread-0", monitor, 0, 0, 4, 2, 4).start();
        new ExecutionThread("Thread-2", monitor, monitor2, 0, 3, 3, 6).start();
        new ExecutionThread("Thread-1", monitor2, 0, 0, 5, 2, 5).start();
    }
}
