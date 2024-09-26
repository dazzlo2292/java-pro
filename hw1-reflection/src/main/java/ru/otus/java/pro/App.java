package ru.otus.java.pro;

public class App {
    public static void main(String[] args) {
        TestRunner testRunner = new TestRunner();
        testRunner.run(TestSuite.class);
    }
}