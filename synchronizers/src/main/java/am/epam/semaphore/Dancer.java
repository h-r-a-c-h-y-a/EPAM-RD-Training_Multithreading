package am.epam.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Dancer extends Thread {

    private final Semaphore semaphore;

    public Dancer(final Semaphore semaphore, String dancerName) {
        super(dancerName);
        this.semaphore = semaphore;
    }


    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " tries to enter the dancing hall");
        try {
            semaphore.acquire();
            System.out.println(Thread.currentThread().getName() + " is dancing....");
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(5));
            System.out.println(Thread.currentThread().getName() + " has left the dancing hall");
            semaphore.release();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
