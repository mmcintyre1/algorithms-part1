package src.queues;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first, last;
    private int size;

    private class Node {
        Item item;
        Node next;
    }

    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    public boolean isEmpty() {
        return first == null && last == null;
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

        if (last == null) last = oldFirst;
        size++;
    }

    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();

        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = oldLast;

        if (isEmpty()) first = last;
        else oldLast.next = last;
        size++;
    }

    public Item removeFirst() {
        Item item = first.item;
        first = first.next;
        size--;
        return item;
    }

    public Item removeLast() {
        Item item = last.item;
        last = last.next;
        size--;
        return item;
    }


    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }
    }


    public static void main(String[] args) {
        Deque<Integer> d = new Deque<>();
        System.out.println(d.isEmpty());
        d.addFirst(1);
        System.out.println(d.isEmpty());
        d.addFirst(2);
        d.addFirst(3);
        d.addLast(0);
        System.out.printf("deque size => %d\n", d.size());
        d.addLast(1);
        d.addFirst(0);

        System.out.println("iteration =>");
        for (int item : d) {
            System.out.println(item);
        }

        int first = d.removeFirst();
        System.out.printf("first item => %d\n", first);
        int nextFirst = d.removeFirst();
        System.out.printf("next first item => %d\n", nextFirst);

        int last = d.removeLast();
        System.out.printf("last item => %d\n", last);
        int nextLast = d.removeLast();
        System.out.printf("next last item => %d\n", nextLast);

        System.out.printf("deque size => %d\n", d.size());
        Iterator<Integer> i = d.iterator();

//        System.out.println("iteration =>");
//        for (int item : d) {
//            System.out.println(item);
//        }
    }
}