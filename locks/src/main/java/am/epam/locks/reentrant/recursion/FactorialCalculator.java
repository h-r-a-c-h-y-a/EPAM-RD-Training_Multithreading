package am.epam.locks.reentrant.recursion;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.StampedLock;

public class FactorialCalculator {
    private static StampedLock lock = new StampedLock();
    public static int calc(int n) {
        long stamp = lock.writeLock();
        try {
            if (n == 1) return n;
            return n * calc(n - 1);
        } finally {
            lock.unlockWrite(stamp);
        }
    }
}
