package am.epam.locks.reentrant;

import am.epam.locks.reentrant.conditional.ItemIdConsumerService;
import am.epam.locks.reentrant.conditional.ItemIdProducerService;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionalLockExecutor {
    public static void main(String[] args) throws InterruptedException {
        ArrayDeque<Integer> itemIds = new ArrayDeque<>();
        ReentrantLock reentrantLock = new ReentrantLock();
        Condition isEmpty = reentrantLock.newCondition();
        Condition isFull = reentrantLock.newCondition();
        ItemIdProducerService itemIdProducerService = new ItemIdProducerService(2, itemIds, reentrantLock, isEmpty, isFull);
        ItemIdConsumerService itemIdConsumerService = new ItemIdConsumerService(itemIds, reentrantLock, isEmpty, isFull);
        List<Thread> threads = new ArrayList<>();
        for (int i = 1; i < 21; i++) {
            final Integer rate = i;
            Runnable r1 = () -> System.out.println(itemIdConsumerService.consume());
            Runnable r2 = () -> itemIdProducerService.produce(rate);
            threads.add(new Thread(r1));
            threads.add(new Thread(r2));
        }
        threads.forEach(Thread::start);
        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
//        List<Callable<Integer>> threads = Stream.generate(() -> (Callable<Integer>) rateConsumerService::consume)
//                .limit(200).collect(Collectors.toUnmodifiableList());
//        List<Runnable> threads1 = Stream.generate(() -> (Runnable) () -> rateProducerService.produce(ThreadLocalRandom.current().nextInt(1000)))
//                .limit(200).collect(Collectors.toUnmodifiableList());
//        ExecutorService executorService = Executors.newCachedThreadPool();
//        threads1.forEach(executorService::submit);
//        List<Future<Integer>> futures = executorService.invokeAll(threads);
//        futures.forEach(integerFuture -> {
//            try {
//                System.out.println("Result: " + integerFuture.get());
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            } catch (ExecutionException e) {
//                throw new RuntimeException(e);
//            }
//        });
//        executorService.shutdown();
    }
}
