package ru.otus.java.pro.patterns1.iterator.iterators;

import ru.otus.java.pro.patterns1.iterator.Box;
import ru.otus.java.pro.patterns1.iterator.dolls.Doll;
import ru.otus.java.pro.patterns1.iterator.dolls.NestedDoll;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

public class SmallAtFirst implements Iterator<Doll> {
    private final List<NestedDoll> nestedDolls;

    private final Deque<Doll> dolls;

    public SmallAtFirst(Box box) {
        this.nestedDolls = box.getNestedDolls();
        this.dolls = fillDolls();
    }

    private Deque<Doll> fillDolls() {
        Deque<Doll> stack = new ArrayDeque<>();
        int minSize = nestedDolls.get(0).getDolls().get(0).size();
        int maxSize = nestedDolls.get(0).getDolls().get(nestedDolls.get(0).getDolls().size() - 1).size();

        for (NestedDoll nestedDoll : nestedDolls) {
            for (Doll doll : nestedDoll.getDolls()) {
                if (doll.size() < minSize) {
                    minSize = doll.size();
                }
                if (doll.size() > maxSize) {
                    maxSize = doll.size();
                }
            }
        }

        for (int i = minSize; i <= maxSize; i++) {
            int tempI = i;
            nestedDolls.stream()
                    .flatMap(nestedDoll -> nestedDoll.getDolls().stream())
                    .filter(doll -> doll.size() == tempI)
                    .forEach(stack::add);
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
