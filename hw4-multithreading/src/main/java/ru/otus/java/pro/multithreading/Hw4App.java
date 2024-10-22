package ru.otus.java.pro.multithreading;

public class Hw4App {
    public static void main(String[] args) throws InterruptedException {
        CustomThreadPool threadPool = new CustomThreadPool(4);

        for (int i = 1; i <= 10; i++) {
            int counter = i;
            threadPool.execute(() -> {
                System.out.println((int)Math.pow(counter,2) + " " + Thread.currentThread().getName());
            });
        }

        Thread.sleep(4000);
        threadPool.shutdown();

        try {
            int counter = 11;
            threadPool.execute(() -> {
                System.out.println((int)Math.pow(counter, 2) + " " + Thread.currentThread().getName());
            });
        } catch (IllegalStateException e) {
            System.out.println("Error while adding a task: " + e.getMessage());
        }
    }
}
