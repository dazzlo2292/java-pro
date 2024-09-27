package ru.otus.java.pro;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Hw3App {
    public static void main(String[] args) {
        List<Task> allTasks = new ArrayList<>();
            allTasks.add(new Task (1, "Find the cat", TaskStatus.CLOSED));
            allTasks.add(new Task (2, "Buy food", TaskStatus.IN_PROGRESS));
            allTasks.add(new Task (3, "Write code", TaskStatus.IN_PROGRESS));
            allTasks.add(new Task (4, "Complete homework", TaskStatus.OPEN));

        // List tasks with status "IN_PROGRESS"
        List<Task> inProgressTasks = allTasks.stream()
                .filter(task -> task.getStatus().equals(TaskStatus.IN_PROGRESS))
                .toList();
        inProgressTasks.forEach(task -> System.out.println("Task: " + task.getName() + " (" + task.getStatus() + ")"));
        System.out.println();


        // Count closed tasks
        long countClosedTasks = allTasks.stream()
                .filter(task -> task.getStatus().equals(TaskStatus.CLOSED))
                .count();
        System.out.println("Count closed tasks = " + countClosedTasks + "\n");


        // Check tasks with ID = 2 and ID != 99
        boolean result1 = allTasks.stream()
                .noneMatch(task -> task.getId() == 99);
        boolean result2 = allTasks.stream()
                .anyMatch(task -> task.getId() == 2);
        System.out.println((result1 && result2) + "\n");


        // Sorted list
        List<Task> sortedTasks = allTasks.stream()
                .sorted(Comparator.comparing(task -> task.getStatus().getStepNumber()))
                .toList();
        sortedTasks.forEach(task -> System.out.println("Task: " + task.getName() + " (" + task.getStatus() + ")"));
        System.out.println();


        // *Join groups
        Map<TaskStatus, List<Task>> tasksGroupsByStatus = allTasks.stream()
                .collect(Collectors.groupingBy(Task::getStatus));

        Map<Boolean, List<Task>> tasksOpen = tasksGroupsByStatus.get(TaskStatus.OPEN).stream()
                .collect(Collectors.groupingBy(task -> task.getId() % 2 == 0));
        Map<Boolean, List<Task>> tasksInProgress = tasksGroupsByStatus.get(TaskStatus.IN_PROGRESS).stream()
                .collect(Collectors.groupingBy(task -> task.getId() % 2 == 0));
        Map<Boolean, List<Task>> taskClosed = tasksGroupsByStatus.get(TaskStatus.CLOSED).stream()
                .collect(Collectors.groupingBy(task -> task.getId() % 2 == 0));


        // *Partitions for tasks with status "CLOSED" and other
        Map<Boolean, List<Task>> partitioningTasks = allTasks.stream()
                .collect(Collectors.partitioningBy(task -> task.getStatus().equals(TaskStatus.CLOSED)));

        List<Task> closedTasks = partitioningTasks.get(true);
        List<Task> unclosedTasks = partitioningTasks.get(false);

        System.out.println("Closed tasks:");
        closedTasks.forEach(task -> System.out.println("Task: " + task.getName() + " (" + task.getStatus() + ")"));
        System.out.println();
        System.out.println("Unclosed tasks:");
        unclosedTasks.forEach(task -> System.out.println("Task: " + task.getName() + " (" + task.getStatus() + ")"));
        System.out.println();
    }
}
