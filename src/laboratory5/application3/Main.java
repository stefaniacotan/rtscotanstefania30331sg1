package laboratory5.application3;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ProducerConsumer {
    private static final int MAX_CAPACITY = 100;
    private final ArrayList<Integer> buffer = new ArrayList<>(MAX_CAPACITY);
    private final Lock lock = new ReentrantLock();
    private final Condition bufferNotEmpty = lock.newCondition();
    private final Condition bufferNotFull = lock.newCondition();
    private final Random random = new Random();

    public void start() {
        Thread producerThread = new Thread(this::produce);
        producerThread.start();

        Thread consumerThread1 = new Thread(this::consume);
        Thread consumerThread2 = new Thread(this::consume);
        Thread consumerThread3 = new Thread(this::consume);
        consumerThread1.start();
        consumerThread2.start();
        consumerThread3.start();
    }

    private void produce() {
        while (true) {
            lock.lock();
            try {
                while (buffer.size() == MAX_CAPACITY) {
                    bufferNotFull.await();
                }
                int value = random.nextInt();
                buffer.add(value);
                System.out.println("Producer produced " + value);
                bufferNotEmpty.signalAll();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void consume() {
        while (true) {
            lock.lock();
            try {
                while (buffer.isEmpty()) {
                    bufferNotEmpty.await();
                }
                int value = buffer.remove(0);
                System.out.println(Thread.currentThread().getName() + " consumed " + value);
                bufferNotFull.signalAll();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        ProducerConsumer producerConsumer = new ProducerConsumer();
        producerConsumer.start();
    }
}

