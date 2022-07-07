package am.epam.executors;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ExecutorsSample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.execute(() -> System.out.println("Asynchronous executed task"));

        System.out.println(Thread.currentThread().getName() + " executes immediately");

        Future future = executorService.submit(() -> {
            System.out.println("Asynchronous submitted task");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        future.get();
        System.out.println(Thread.currentThread().getName() + " executes after completion submitted task");

        future = executorService.submit((Callable) () -> {
            System.out.println("Asynchronous Callable");
            return "Callable Result";
        });

        System.out.println("future.get() = " + future.get());

        executeInvokeAny(executorService);

        executeInvokeAll(executorService);

        executorService.shutdown();
    }

    private static void executeInvokeAny(ExecutorService executorService) throws ExecutionException, InterruptedException {
        Set<Callable<String>> callables = new HashSet<>();

        callables.add(() -> "Task 1");
        callables.add(() -> "Task 2");
        callables.add(() -> "Task 3");

        String result = executorService.invokeAny(callables);

        System.out.println("result = " + result);
    }

    private static void executeInvokeAll(ExecutorService executorService) throws ExecutionException, InterruptedException {
        Set<Callable<String>> callables = new HashSet<>();

        callables.add(() -> "Task 1");
        callables.add(() -> "Task 2");
        callables.add(() -> "Task 3");

        List<Future<String>> futures = executorService.invokeAll(callables);

        for(Future<String> future : futures){
            System.out.println("future.get = " + future.get());
        }
    }
}
