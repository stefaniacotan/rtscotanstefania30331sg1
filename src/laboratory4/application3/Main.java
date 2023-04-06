package laboratory4.application3;
class ExecutionThread extends Thread {
    Integer monitor;
    int sleep_min, sleep_max, activity_min, activity_max;

    public ExecutionThread(String name, Integer monitor, int sleep_min, int sleep_max, int activity_min, int activity_max) {
        super(name);
        this.monitor = monitor;
        this.sleep_min = sleep_min;
        this.sleep_max = sleep_max;
        this.activity_min = activity_min;
        this.activity_max = activity_max;
    }

    public void run() {
        while (true) {
            System.out.println(this.getName() + " - STATE 1");

            synchronized (monitor) {
                System.out.println(this.getName() + " - STATE 2");
                int k = (int) Math.round(Math.random() * (activity_max - activity_min) + activity_min);
                for (int i = 0; i < k * 100000; i++) {
                    i++;
                    i--;
                }
            }
            System.out.println(this.getName() + " - STATE 3");
            try {
                Thread.sleep(Math.round(Math.random() * (sleep_max - sleep_min) + sleep_min) * 500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(this.getName() + "- STATE 4");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Integer monitor = 1;
        new ExecutionThread("Thread-0", monitor, 0, 3, 4, 7).start();
        new ExecutionThread("Thread-1", monitor, 0, 6, 5, 7).start();
        new ExecutionThread("Thread-2", monitor, 0, 5, 3, 6).start();
    }
}

