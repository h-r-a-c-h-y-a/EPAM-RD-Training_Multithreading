package am.epam;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

class RecursiveSum extends RecursiveTask<Long> {

    private final int minThreshold = 1000000;
    private long start, end;

    RecursiveSum(long start, long end) {
        this.start = start;
        this.end = end;
    }


    @Override
    protected Long compute() {
        if (end - start <= minThreshold) {
            long total = 0;
            for (long i = start; i <= end; i++) {
                total += i;
            }
            return total;
        } else {
            long middle = (end + start) / 2;
            RecursiveSum left = new RecursiveSum(start, middle);
            RecursiveSum right = new RecursiveSum(middle + 1, end);
            left.fork();
            return right.compute() + left.join();
        }

    }
}

public class Main {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
//        System.out.println("Result: " + seq());//-5340232226128654848
        System.out.println("Result: " + parallel());//-5340232216128654848
        System.out.println("Execution time: " + (System.currentTimeMillis() - start));
    }

    private static long seq() {
        long total = 0;
        for (long i = 0; i < 10_000_000_000L; i++) {
            total += i;
        }
        return total;
    }

    private static long parallel() {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        Long total = forkJoinPool.invoke(new RecursiveSum(0, 10_000_000_000L));
        forkJoinPool.shutdown();
        return total;
    }
}