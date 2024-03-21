package ru.vsu.cs.task2_23;

import java.util.Comparator;
import java.util.Random;

public class Program {
    public static void main(String[] args) {
        MyLinkedList<Integer> list = new MyLinkedList<>();
        for (int i = 1; i <= 5000; i++) {
            list.addLast(i);
        }
        System.out.println("Array: " + list.asString());
        list.mixRandomly();
        System.out.println("Array: " + list.asString());
    }
}