package ru.otus.java.pro.multithreading;

import java.util.List;

public class Worker implements Runnable{
    private final CustomThreadPool pool;
    private final List<Runnable> tasks;
    private final Object monitor;

    public Worker(CustomThreadPool pool, List<Runnable> tasks, Object monitor) {
        this.pool = pool;
        this.tasks = tasks;
        this.monitor = monitor;
    }

    @Override
    public void run() {
        while (pool.isRunning()) {
            synchronized (monitor) {
                if (!tasks.isEmpty()) {
                    Runnable nextTask = tasks.get(0);
                    tasks.remove(0);
                    nextTask.run();
                }
            }
        }
    }
}
