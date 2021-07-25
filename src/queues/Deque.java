package src.queues;

import java.util.Iterator;
import java.util.stream.IntStream;

public class Deque<Item> implements Iterable<Item> {
    private Item[] deque;
    private int firstIndex = 0;
    private int lastIndex = 0;

    public Deque() {
        deque = (Item[]) new Object[1];
    }

    public boolean isEmpty() {
        return firstIndex == lastIndex && deque[firstIndex] == null;
    }

    public int size() {
        return lastIndex - firstIndex;
    }

    public void addFirst(Item item) {
        deque[firstIndex++] = item;
        lastIndex++;
    }

    public void addLast(Item item) {
        deque[lastIndex++] = item;

    }

    public Item removeFirst() {
        Item item = deque[--firstIndex];
        deque[firstIndex] = null;
        return item;
    }

    public Item removeLast() {
        Item item = deque[--lastIndex];
        deque[lastIndex] = null;
        return item;
    }

    private void resize() {
        Item[] copy = (Item[]) new Object[1];
        IntStream.range(0, firstIndex).forEach(i -> copy[i] = deque[i]);
        deque = copy;
    }

    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private int i = firstIndex;

        public boolean hasNext() {
            return i > 0;
        }

        public void remove() {  /* not supported */ }

        public Item next() {
            return deque[--i];
        }
    }


    public static void main(String[] args) {
        Deque<Integer> d = new Deque<>();
        d.addFirst(1);
        d.addLast(2);
        int firstItem = d.removeFirst();
//        int lastItem = d.removeLast();

        System.out.println(firstItem);

        for (int item : d) {
            System.out.println(item);
        }
    }

}