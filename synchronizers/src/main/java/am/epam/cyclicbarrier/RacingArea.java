package am.epam.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadLocalRandom;

public class RacingArea {

    public static void main(String args[]) throws InterruptedException {
        final CyclicBarrier barrier = new CyclicBarrier(4);
        final Car first = new Car(1000, barrier, "Car-1");
        final Car second = new Car(2000, barrier, "Car-2");
        final Car third = new Car(3000, barrier, "Car-3");
        final Car fourth = new Car(4000, barrier, "Car-4");
        first.start();
        second.start();
        third.start();
        fourth.start();
        fourth.join();
        second.join();
        first.join();
        third.join();
        System.out.println("Race has finished");
    }
}

class Car extends Thread {
    private int speed;
    private CyclicBarrier barrier;

    public Car(int speed, CyclicBarrier barrier, String name) {
        super(name);
        this.speed = speed;
        this.barrier = barrier;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(speed));
            System.out.println(Thread.currentThread().getName() + " has arrived to start line");
            barrier.await();
            System.out.println(Thread.currentThread().getName() + " has started race");
            barrier.reset();
            Thread.sleep(ThreadLocalRandom.current().nextInt(speed));
            System.out.println(Thread.currentThread().getName() + " has finished race");
            barrier.await();
            System.out.println(Thread.currentThread().getName() + " has left racing area");
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}

