package am.epam.locks.reentrant.conditional;

import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class ItemIdProducerService {
    private final int threshold;

    private final Queue<Integer> rates;
    private final Lock lock;

    private final Condition isEmpty;

    private final Condition isFull;

    public ItemIdProducerService(int threshold, Queue<Integer> rates, Lock lock, Condition isEmpty, Condition isFull) {
        this.threshold = threshold;
        this.rates = rates;
        this.lock = lock;
        this.isEmpty = isEmpty;
        this.isFull = isFull;
    }

    public void produce(Integer rate) {
        try {
            lock.lock();
            while (rates.size() >= threshold) {
                isFull.await();
            }
            System.out.println("Rates size: " + rates.size());
            rates.offer(rate);
            isEmpty.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
