package ru.otus.java.pro.multithreading;

import java.util.List;

public class Worker implements Runnable{
    private final CustomThreadPool pool;
    private final List<Runnable> tasks;

    private Runnable currentTask;

    public Worker(CustomThreadPool pool, List<Runnable> tasks) {
        this.pool = pool;
        this.tasks = tasks;
        this.currentTask = null;
    }

    @Override
    public void run() {
        while (pool.isRunning()) {
            try {
                synchronized (tasks) {
                    if (!tasks.isEmpty()) {
                        currentTask = tasks.get(0);
                        tasks.remove(0);
                    } else {
                        tasks.wait();
                    }
                }
                if (currentTask != null) {
                    currentTask.run();
                    currentTask = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
