package src.queues;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class Deque<Item> implements Iterable<Item> {
    private Node first, last;
    private int size;

    private class Node {
        Item item;
        Node previous;
        Node next;
    }

    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();

        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        first.previous = null;

        if (isEmpty()) last = first;
        else oldFirst.previous = first;

        size++;
    }

    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();

        Node oldLast = last;
        last = new Node();

        last.item = item;
        last.next = null;
        last.previous = oldLast;

        if (isEmpty()) first = last;
        else oldLast.next = last;

        size++;
    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("The deque is empty.");
        }
        Item item = first.item;
        first = first.next;
        size--;

        // cleanup if deque is empty
        if (isEmpty()) last = null;
        else first.previous = null;

        return item;
    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("The deque is empty.");
        }
        Item item = last.item;
        Node oldLast = last;
        last = oldLast.previous;
        size--;

        // cleanup if deque is empty
        if (isEmpty()) first = null;
        else last.next = null;

        return item;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    private static class DequeTest {
        private final Deque<Integer> d;

        private DequeTest() {
            d = new Deque<>();
        }

        private void populateFirst(int n) {
            for (int i = 0; i < n; i++) {
                d.addFirst(i);
            }
        }

        private void populateLast(int n) {
            for (int i = 0; i < n; i++) {
                d.addLast(i);
            }
        }

        private void addFirstRemoveLast() {
            System.out.println("testing addFirstRemoveLast");
            System.out.println("Order should be [0, 1, 2, 3, 4]");
            populateFirst(5);
            for (int i = 0; i < 5; i++) {
                int item = d.removeLast();
                System.out.printf("%d\n", item);
            }

        }

        private void addLastRemoveFirst() {
            System.out.println("testing addLastRemoveFirst");
            System.out.println("Order should be [0, 1, 2, 3, 4]");
            populateLast(5);
            for (int i = 0; i < 5; i++) {
                int item = d.removeFirst();
                System.out.printf("%d\n", item);
            }
        }

        private void test() {
            addFirstRemoveLast();
            addLastRemoveFirst();
        }
    }

    public static void main(String[] args) {
        DequeTest dt = new DequeTest();
        dt.test();
    }
}