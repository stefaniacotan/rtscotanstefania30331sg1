package laboratory2.app2;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ThreadLocalRandom;

public class Controller implements ActionListener {

    private Fir processorCounter;
    private List<Observer> observers;

    public Controller() {
        processorCounter = new Fir();
        observers = new ArrayList<>();
    }

    public void startThreads() {
        int logicalProcessors = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < logicalProcessors; i++) {
            Thread thread = new Thread(new ProcessorThread());
            thread.setPriority(ThreadLocalRandom.current().nextInt(Thread.MIN_PRIORITY, Thread.MAX_PRIORITY + 1));
            thread.start();
            observers.add(thread);
        }
    }

    public void registerObservers(Observer observer) {
        observers.add(observer);
    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(processorCounter, null);
        }
    }

    private class ProcessorThread extends Observable implements Runnable {
        @Override
        public void run();
    }
}


