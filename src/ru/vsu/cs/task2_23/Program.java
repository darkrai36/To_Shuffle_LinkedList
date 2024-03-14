package ru.vsu.cs.task2_23;

import java.util.Comparator;
import java.util.Random;

public class Program {
    public static void main(String[] args) {
        MyLinkedList<Integer> list = new MyLinkedList<>();
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);
        list.addLast(4);
        list.addLast(5);
        System.out.println("Array: " + list.asString());
        list.mixRandomly();
        System.out.println("Array: " + list.asString());
    }
}