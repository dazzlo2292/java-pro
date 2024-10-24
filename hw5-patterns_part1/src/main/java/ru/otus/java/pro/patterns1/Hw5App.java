package ru.otus.java.pro.patterns1;

import ru.otus.java.pro.patterns1.iterator.Box;
import ru.otus.java.pro.patterns1.iterator.dolls.Doll;
import ru.otus.java.pro.patterns1.iterator.dolls.DollColor;
import ru.otus.java.pro.patterns1.iterator.dolls.NestedDoll;
import ru.otus.java.pro.patterns1.iterator.iterators.SmallAtFirst;
import ru.otus.java.pro.patterns1.iterator.iterators.SmallerToBigger;

import java.util.*;

public class Hw5App {
    public static void main(String[] args) {
        Box box = new Box();

        box.addNestedDoll(new NestedDoll(DollColor.ORANGE, 5));
        box.addNestedDoll(new NestedDoll(DollColor.GREEN, 3));
        box.addNestedDoll(new NestedDoll(DollColor.PURPLE, 5));
        box.addNestedDoll(new NestedDoll(DollColor.WHITE, 4));

        print(new SmallerToBigger(box));
        print(new SmallAtFirst(box));
    }

    private static void print(Iterator<Doll> iterator) {
        while (iterator.hasNext()) {
            Doll doll = iterator.next();
            System.out.println(doll.color() + " " + doll.size());
        }
        System.out.println("-------------------");
    }
}
