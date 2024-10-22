package ru.otus.java.pro.multithreading;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;

public class CustomThreadPool implements Executor {
    private volatile boolean isRunning = true;

    private final List<Runnable> tasks = new LinkedList<>();

    public CustomThreadPool(int threadsCount) {
        for (int i = 0; i < threadsCount; i++) {
            new Thread(new Worker(this, tasks)).start();
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void shutdown() {
        isRunning = false;
        synchronized (tasks) {
            tasks.notifyAll();
        }
    }

    @Override
    public void execute(Runnable command) {
        if (isRunning) {
            synchronized (tasks) {
                tasks.add(command);
                tasks.notify();
            }
        } else {
            throw new IllegalStateException("ThreadPool is shutdown!");
        }
    }
}
