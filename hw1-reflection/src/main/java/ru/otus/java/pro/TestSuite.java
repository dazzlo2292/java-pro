package ru.otus.java.pro;

import ru.otus.java.pro.annotations.AfterSuite;
import ru.otus.java.pro.annotations.BeforeSuite;
import ru.otus.java.pro.annotations.Test;
import ru.otus.java.pro.exceptions.TestFailedException;

public class TestSuite {
    @BeforeSuite
    public static void init() {
        System.out.println("Init suite");
    }

    @Test(priority = 3)
    public static void test2() {
        System.out.println("Run Test #2");
    }

    @Test(priority = 1)
    public static void test1() {
        System.out.println("Run Test #1");
    }

    public static void test3() {
        System.out.println("Run Test #3");
    }

    @Test(priority = 5)
    public static void test4() {
        System.out.println("Run Test #4");
    }

    @Test(priority = 5)
    public static void test5() {
        System.out.println("Run Test #5");
    }

    @Test(priority = 20)
    public static void test6() {
        System.out.println("Run Test #6");
    }

    @Test(priority = 7)
    public static void test7() {
        throw new TestFailedException("Error after run Test #7");
    }

    @AfterSuite
    public static void finish() {
        System.out.println("Suite finished");
    }
}
