package ru.otus.java.pro.patterns1.iterator.iterators;

import ru.otus.java.pro.patterns1.iterator.Box;
import ru.otus.java.pro.patterns1.iterator.dolls.Doll;
import ru.otus.java.pro.patterns1.iterator.dolls.NestedDoll;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

public class SmallerToBigger implements Iterator<Doll> {
    private final List<NestedDoll> nestedDolls;

    private final Deque<Doll> dolls;

    public SmallerToBigger(Box box) {
        this.nestedDolls = box.getNestedDolls();
        this.dolls = fillDolls();
    }

    private Deque<Doll> fillDolls() {
        Deque<Doll> stack = new ArrayDeque<>();
        for (NestedDoll nestedDoll : nestedDolls) {
            stack.addAll(nestedDoll.getDolls());
        }
        return stack;
    }

    @Override
    public boolean hasNext() {
        return !dolls.isEmpty();
    }

    @Override
    public Doll next() {
        return dolls.pop();
    }
}
