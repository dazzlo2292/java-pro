package ru.otus.java.pro;

public enum TaskStatus {
    OPEN("Открыта",0),
    IN_PROGRESS("В работе",1),
    CLOSED("Закрыта",2);

    final private String description;
    final private int stepNumber;

    TaskStatus(String description, int stepNumber) {
        this.description = description;
        this.stepNumber = stepNumber;
    }

    public String getDescription() {
        return description;
    }

    public int getStepNumber() {
        return stepNumber;
    }
}
