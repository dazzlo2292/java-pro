package ru.otus.java.pro.multithreading;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;

public class CustomThreadPool implements Executor {
    private volatile boolean isRunning = true;

    private final List<Runnable> tasks = new LinkedList<>();

    public CustomThreadPool(int threadsCount) {
        Object monitor = new Object();
        for (int i = 0; i < threadsCount; i++) {
            new Thread(new Worker(this, tasks, monitor)).start();
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void shutdown() {
        isRunning = false;
    }

    @Override
    public void execute(Runnable command) {
        if (isRunning) {
            tasks.add(command);
        } else {
            throw new IllegalStateException("ThreadPool is shutdown!");
        }
    }
}
