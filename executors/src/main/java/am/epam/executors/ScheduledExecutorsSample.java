package am.epam.executors;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorsSample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();

        scheduledExecutor.schedule(() -> System.out.println("Asynchronous scheduled task after 2days"), 2, TimeUnit.DAYS);

        ScheduledFuture<String> scheduledFuture = scheduledExecutor.schedule(() -> "Asynchronous scheduled task", 3, TimeUnit.SECONDS);
        System.out.println(Thread.currentThread().getName() + " waited result from scheduled task: " + scheduledFuture.get());

        System.out.println(Thread.currentThread().getName() + " continue execution");

        scheduledExecutor.scheduleAtFixedRate(
                () -> System.out.println("Execution of schedule At fixed rate"), 2, 3, TimeUnit.SECONDS);

        scheduledExecutor.scheduleWithFixedDelay(
                () -> System.out.println("Execution scheduleWithFixedDelay"), 2, 3, TimeUnit.SECONDS);

    }
}
