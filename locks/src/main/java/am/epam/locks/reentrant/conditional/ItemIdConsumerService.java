package am.epam.locks.reentrant.conditional;

import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class ItemIdConsumerService {

    private final Queue<Integer> rates;

    private final Lock lock;

    private final Condition isEmpty;

    private final Condition isFull;

    public ItemIdConsumerService(Queue<Integer> rates, Lock lock, Condition isEmpty, Condition isFull) {
        this.rates = rates;
        this.lock = lock;
        this.isEmpty = isEmpty;
        this.isFull = isFull;
    }

    public Integer consume() {
        try {
            lock.lock();
            while (rates.isEmpty()) {
                isEmpty.await();
            }
            Integer item = rates.poll();
            System.out.println("Retrieve item by id: " + item);
            isFull.signalAll();
            return item;
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}
