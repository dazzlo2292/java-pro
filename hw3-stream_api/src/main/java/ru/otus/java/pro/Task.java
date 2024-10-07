package ru.otus.java.pro;

public class Task {
    final private long id;
    final private String name;
    private TaskStatus status;

    public Task(long id, String name, TaskStatus status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}
