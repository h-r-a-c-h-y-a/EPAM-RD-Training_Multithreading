package am.epam.locks;


import am.epam.locks.reentrant.conditional.Tiler;
import am.epam.locks.reentrant.conditional.TilerService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    public static void main(String[] args) {
        final Lock lock = new ReentrantLock();
        final Condition condition = lock.newCondition();
        final Tiler tiler1 = new Tiler(lock, condition);
        final Tiler tiler2 = new Tiler(lock, condition);
        final int steps = 100;
        Tiler.setStep(steps);

        final TilerService tilerService = new TilerService();
        final ExecutorService executorService = Executors.newCachedThreadPool();
        while (true) {
            executorService.submit(() -> tilerService.putTiles(tiler1));
            executorService.submit(() -> tilerService.putMixture(tiler2));
            if(tilerService.isFinishedTheWork()) break;
        }
        executorService.shutdown();
    }
}
