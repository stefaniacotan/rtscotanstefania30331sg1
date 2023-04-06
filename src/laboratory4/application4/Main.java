package laboratory4.application4;

class ExecutionMainThread extends Thread {
    Integer monitor, monitor2;
    int sleep_min, sleep_max, activity_min, activity_max;

    public ExecutionMainThread(String name, Integer monitor,Integer monitor2, int sleep_min, int sleep_max, int activity_min, int activity_max) {

        super(name);
        this.monitor = monitor;
        this.monitor2 = monitor2;
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
        synchronized (monitor2) {
            monitor2.notifyAll();
        }
        System.out.println(this.getName() + " -STATE 3");
    }
}
class ExecutionSecondaryThread extends Thread{
    Integer monitor;
    int activity_min, activity_max;
    Thread T;

    public ExecutionSecondaryThread(String name, Integer monitor, int activity_min, int activity_max, Thread T) {
        super(name);
        this.monitor = monitor;
        this.activity_min = activity_min;
        this.activity_max = activity_max;
        this.T = T;
    }
    public void run(){
        System.out.println(this.getName()+" -STATE 1");
        synchronized (monitor){
            try {
                monitor.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(this.getName()+" -STATE 2");
        int k = (int) Math.round(Math.random() * (activity_max - activity_min) + activity_min);
        for (int i = 0; i < k * 100000; i++) {
            i++;
            i--;
        }
        System.out.println(this.getName()+" -STATE 3");
        try {
            if (T != null) T.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Integer monitor = 1;
        Integer monitor2 = 1;

        ExecutionMainThread T1 = new ExecutionMainThread("Thread-0-Principal", monitor, monitor2, 0, 7, 2, 3);
        ExecutionSecondaryThread T2 = new ExecutionSecondaryThread("Thread-1-Secondary", monitor, 3, 5, T1);
        ExecutionSecondaryThread T3 = new ExecutionSecondaryThread("Thread-2-Secondary", monitor, 4, 6, T1);
        T1.start();
        T2.start();
        T3.start();
    }
}

