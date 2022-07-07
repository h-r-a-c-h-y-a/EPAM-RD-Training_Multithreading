package am.epam.locks.reentrant;

import am.epam.locks.reentrant.recursion.FactorialCalculator;

public class RecursiveLockExecutor {

    public static void main(String[] args) {
        new Thread(() -> System.out.println(FactorialCalculator.calc(11))).start();
        new Thread(() -> System.out.println(FactorialCalculator.calc(13))).start();
    }
}
