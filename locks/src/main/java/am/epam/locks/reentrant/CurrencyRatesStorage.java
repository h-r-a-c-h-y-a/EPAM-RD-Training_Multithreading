package am.epam.locks.reentrant;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;

public class CurrencyRatesStorage {
    private final Map<String, Double> currencyRates;

    private StampedLock stampedLock = new StampedLock();

    public CurrencyRatesStorage(final Map<String, Double> currencyRates) {
        this.currencyRates = currencyRates;
    }

    public void changeRate(final String currency, final double newRate) {
        long stamp = stampedLock.writeLock();
        try {
            currencyRates.put(currency, newRate);
        } finally {
            stampedLock.unlockWrite(stamp);
        }
    }

    public Double getRate(final String currency) {
        long stamp = stampedLock.readLock();
        try {
            return currencyRates.get(currency);
        } finally {
            stampedLock.unlockRead(stamp);
        }
    }

    public Set<String> getCurrencies() {
        long stamp = stampedLock.tryOptimisticRead();
        Set<String> strings = currencyRates.keySet();
        try {
            if (!stampedLock.validate(stamp)) {
                stamp = stampedLock.readLock();
                return currencyRates.keySet();
            }
        } finally {
            stampedLock.unlockRead(stamp);
        }
        return strings;
    }
}
