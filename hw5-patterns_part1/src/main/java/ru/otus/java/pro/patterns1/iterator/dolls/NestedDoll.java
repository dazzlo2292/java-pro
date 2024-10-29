package ru.otus.java.pro.patterns1.iterator.dolls;

import java.util.LinkedList;
import java.util.List;

public class NestedDoll {
    private final List<Doll> dolls;

    public NestedDoll(DollColor color, int size) {
        this.dolls = new LinkedList<>();

        for (int i = 1; i <= size; i++) {
            dolls.add(new Doll(i, color));
        }
    }

    public List<Doll> getDolls() {
        return dolls;
    }
}
