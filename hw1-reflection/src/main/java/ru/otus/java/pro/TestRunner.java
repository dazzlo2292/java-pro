package ru.otus.java.pro;

import ru.otus.java.pro.annotations.AfterSuite;
import ru.otus.java.pro.annotations.BeforeSuite;
import ru.otus.java.pro.annotations.Test;
import ru.otus.java.pro.exceptions.ClassMarkupException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class TestRunner {
    public static void run(Class targetClass) {
        Method[] methods = targetClass.getDeclaredMethods();
        Map<Integer, List<Method>> tests = new TreeMap<>(Comparator.reverseOrder());

        if (checkMarkup(methods)) {
            fillTests(methods, tests);
            Method beforeSuite = findBeforeSuite(methods);
            Method afterSuite = findAfterSuite(methods);
            runTests(beforeSuite, tests, afterSuite);
        }
    }

    private static boolean checkMarkup(Method[] methods) {
        boolean hasBeforeSuite = false;
        boolean hasAfterSuite = false;
        for (Method method : methods) {
            if (method.isAnnotationPresent(BeforeSuite.class) && !hasBeforeSuite) {
                hasBeforeSuite = true;
            } else if (method.isAnnotationPresent(BeforeSuite.class) && hasBeforeSuite) {
                throw new ClassMarkupException("@BeforeSuite not unique!");
            }

            if (method.isAnnotationPresent(AfterSuite.class) && !hasAfterSuite) {
                hasAfterSuite = true;
            } else if (method.isAnnotationPresent(AfterSuite.class) && hasAfterSuite) {
                throw new ClassMarkupException("@AfterSuite not unique!");
            }

            if (method.isAnnotationPresent(Test.class) && (method.isAnnotationPresent(BeforeSuite.class) || method.isAnnotationPresent(AfterSuite.class))) {
                throw new ClassMarkupException("@Test and @BeforeSuite/@AfterSuite contains in one method!");
            }
        }
        return true;
    }

    private static void fillTests(Method[] methods, Map<Integer, List<Method>> tests) {
        int testPriority;
        for (Method method : methods) {
            if (method.isAnnotationPresent(Test.class)) {
                testPriority = method.getAnnotation(Test.class).priority();
                if (testPriority < 1) {
                    testPriority = 1;
                    addTest(tests, method, testPriority);
                    continue;
                }

                if (testPriority > 10) {
                    testPriority = 10;
                    addTest(tests, method, testPriority);
                    continue;
                }

                addTest(tests, method, testPriority);
            }
        }
    }

    private static void runTests(Method beforeSuite, Map<Integer, List<Method>> tests, Method afterSuite) {
        int countCompleted = 0;
        int countFailed = 0;
        try {
            if (beforeSuite != null) {
                beforeSuite.invoke(null);
            }
            for (List<Method> testList : tests.values()) {
                for (Method test : testList) {
                    try {
                        test.invoke(null);
                        countCompleted++;
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        System.out.println("Exception in method " + test.getName() + ": " + e);
                        countFailed++;
                    }
                }
            }
            if (afterSuite != null) {
                afterSuite.invoke(null);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        System.out.println("\nAll: " + (countCompleted + countFailed) + "\nCompleted: " + countCompleted + "\nFailed: " + countFailed);
    }

    private static void addTest(Map<Integer, List<Method>> map, Method method, int priority) {
        if (map.containsKey(priority)) {
            map.get(priority).add(method);
        } else {
            map.put(priority, new ArrayList<>());
            map.get(priority).add(method);
        }
    }

    private static Method findBeforeSuite(Method[] methods) {
        for (Method method : methods) {
            if (method.isAnnotationPresent(BeforeSuite.class)) {
                return method;
            }
        }
        return null;
    }

    private static Method findAfterSuite(Method[] methods) {
        for (Method method : methods) {
            if (method.isAnnotationPresent(AfterSuite.class)) {
                return method;
            }
        }
        return null;
    }
}
