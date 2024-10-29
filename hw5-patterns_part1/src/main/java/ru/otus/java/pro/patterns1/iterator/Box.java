package ru.otus.java.pro.patterns1.iterator;

import ru.otus.java.pro.patterns1.iterator.dolls.NestedDoll;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Box {
    private final List<NestedDoll> nestedDolls;

    public Box() {
        nestedDolls = new ArrayList<>();
    }

    public List<NestedDoll> getNestedDolls() {
        return Collections.unmodifiableList(nestedDolls);
    }

    public void addNestedDoll(NestedDoll nestedDoll) {
        nestedDolls.add(nestedDoll);
    }
}
