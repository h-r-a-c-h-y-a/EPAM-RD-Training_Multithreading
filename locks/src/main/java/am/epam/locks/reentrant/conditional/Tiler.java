package am.epam.locks.reentrant.conditional;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Tiler {

    private static int step;
    private final Lock lock;
    private final Condition isTilerAttempt;

    public Tiler(Lock lock, Condition isTilerAttempt) {
        this.lock = lock;
        this.isTilerAttempt = isTilerAttempt;
    }

    public static int getStep() {
        return step;
    }

    public void putTiler() {
        System.out.println(Thread.currentThread().getName() + " attempt putting the next tile....");
        lock.lock();
        try {
            while (step % 2 == 1 && step > 1) {
                isTilerAttempt.await();
            }
            if (step > 1)
            System.out.println(Thread.currentThread().getName() + " has putted the next tile, left steps :" + --step);

            isTilerAttempt.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void putMixture() {
        System.out.println(Thread.currentThread().getName() + " attempt putting the next mixture....");
        lock.lock();
        try {
            while (step % 2 == 0 && step > 0) {
                isTilerAttempt.await();
            }
            if (step > 0) {
                System.out.println(Thread.currentThread().getName() + " has putted the next mixture, left steps :" + step--);
            }
            isTilerAttempt.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void setStep(int stepCount) {
        step = stepCount;
    }
}
