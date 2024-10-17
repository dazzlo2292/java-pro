package ru.otus.java.pro.multithreading;

public class Hw4App {
    public static void main(String[] args) {
        CustomThreadPool threadPool = new CustomThreadPool(4);

        int[] counter = new int[1];

        for (int i = 1; i <= 10; i++) {
            counter[0] = i;
            threadPool.execute(() -> {
                System.out.println((int)Math.pow(counter[0], 2) + " " + Thread.currentThread().getName());
            });
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        threadPool.shutdown();

        try {
            counter[0] = 11;
            threadPool.execute(() -> {
                System.out.println((int)Math.pow(counter[0], 2) + " " + Thread.currentThread().getName());
            });
        } catch (IllegalStateException e) {
            System.out.println("Error while adding a task: " + e.getMessage());
        }
    }
}
