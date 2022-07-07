package am.epam.semaphore;

import java.util.concurrent.Semaphore;

public class DancingHall {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);
        for (int i = 1; i <= 15; i++) {
            new Dancer(semaphore, "Dancer " + i).start();
        }
        System.out.println(Thread.currentThread().getName() + " has finished the execution");
    }
}
