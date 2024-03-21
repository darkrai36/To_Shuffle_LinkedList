package ru.vsu.cs.task2_23;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;

public class MyLinkedList<T> implements Iterable<T> {
    private static class Node<T> {
        private T value;
        private Node<T> next;

        public Node(T value, Node<T> next) {
            this.value = value;
            this.next = next;
        }

        public Node(T value) {
            this(value, null);
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }
    }

    private Node<T> head;
    private int size;
    private Node<T> tail;

    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    public void addFirst(T value) {
        Node<T> n = new Node<T>(value, head);
        if (isEmpty()) {
            tail = n;
        }
        head = n;
        size++;
    }
    public void addLast(T value) {
        if (isEmpty()) {
            addFirst(value);
            return;
        }
        Node<T> n = new Node<T>(value);
        tail.next = n;
        tail = n;
        size++;
    }

    public void insert(int index, T value) {
        if (index == 0) {
            addFirst(value);
        } else if (index == size()) {
            addLast(value);
        } else {
            Node<T> q = getNode(index - 1);
            Node<T> n = new Node<T>(value, q.getNext());
            q.setNext(n);
        }
    }
    public void removeFirst() {
        if (isEmpty()) {
            throw new NullPointerException("List is empty.");
        }
        head = head.getNext();
        size--;
    }
    public int size() {
        return size;
    }
    public boolean isEmpty() {
        return (size() == 0);
    }
    public T get(int index) {
        return getNode(index).getValue();
    }
    private Node<T> getNode(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index must be greater or equal 0");
        }
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("List is empty");
        }
        if (index >= size()) {
            throw new IndexOutOfBoundsException("Index must be < size");
        }
        int counter = 0;
        Node<T> cur = head;
        while (cur != null && counter < index) {
            counter++;
            cur = cur.getNext();
        }
        if (cur == null) {
            throw new NullPointerException("List corrupted exception");
        }
        return cur;
    }

    public String asString() {
        Node<T> current = head;
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        while (current != null) {
            sb.append(current.getValue()).append("; ");
            current = current.getNext();
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public String toString() {
        return asString();
    }

    /*public List<T> asList() {
        List<T> list = new ArrayList<>();
        for (int i = 0; i < size(); i++) {
            list.add(get(i));
        }
        return list;
    }
    public List<T> createListToWriteNexts() {
        List<T> list = new ArrayList<>();
        Node<T> cur = head;

        for (int i = 0; i < size(); i++) {
            list.add(cur.getValue());
            cur = cur.getNext();
        }
    }*/

    private void pushHead() {
        Random rnd = new Random();

        int randomIndex = rnd.nextInt(size() - 1) + 1;
        Node<T> prevRandom = getNode(randomIndex - 1);
        Node<T> curRandom = prevRandom.getNext();
        Node<T> newHead, newTail;
        if (size() < 2) {
            return;
        } else if (size() == 2) {
            boolean change = rnd.nextInt(10) >= 5 ? true : false;
            if (change) {
                tail.setNext(head);
                head.setNext(null);
                newHead = tail;
                tail = head;
                head = newHead;
            } else {
                return;
            }
        } else {
            if (randomIndex == 1) { //Если элемент следующий:
                head.setNext(curRandom.getNext());
                curRandom.setNext(head);
            } else if (randomIndex == size() - 1) { //Если рандомный элемент - хвост:
                curRandom.setNext(head.getNext());
                prevRandom.setNext(head);
                head.setNext(null);
                tail = head;
            } else { //Если элемент не следующий и не хвост
                prevRandom.setNext(curRandom.getNext());
                curRandom.setNext(head.getNext());
                head.setNext(prevRandom.getNext());
                prevRandom.setNext(head);
            }
            head = curRandom;
        }
    }
    public void pushElement(int index) {

    }
    public void mixRandomly() {
        Random rnd = new Random();

        pushHead();

        for (int i = 1; i < size() - 1; i++) {

        }
    }

    public void solve(Comparator<T> comparator) {
        if (isEmpty() || size() <= 2) {
            return;
        }
        Node<T> current = head;
        Node<T> lastMin = head;
        Node<T> prevFirst = null;
        Node<T> previous = null;

        while (current != null) {
            int resultCmp = comparator.compare(current.getValue(), lastMin.getValue());
            if (resultCmp < 0) {
                prevFirst = previous;
                lastMin = current;
            } else if (resultCmp == 0) {
                lastMin = current;
            }
            previous = current;
            current = current.next;
        }
        tail.setNext(prevFirst == null ? head : prevFirst.getNext());
        Node<T> newHead = lastMin.getNext();
        lastMin.setNext(head);
        head = newHead;
        tail = prevFirst == null ? lastMin : prevFirst;
        tail.setNext(null);
    }

    public class MyIterator implements Iterator<T> {
        private Node<T> current;
        private Node<T> prev;

        public MyIterator() {
            current = new Node<T>(null, head);
            prev = null;
        }

        @Override
        public boolean hasNext() {
            return current.getNext() != null;
        }

        @Override
        public T next() {
            prev = current;
            current = current.getNext();
            return current.getValue();
        }
        @Override
        public void remove() {
            if (current == null) {
                throw new IllegalStateException();
            }
            if (current == head) {
                head = head.getNext();
            }
            prev.setNext(current.getNext());
            if (prev.getNext() == null) {
                tail = prev;
            }
        }
    }
    @Override
    public Iterator<T> iterator() {
        return new MyIterator();
    }
}
