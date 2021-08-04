package src.queues;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size = 0;
    private Item[] queue = (Item[]) new Object[1];

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException();


        if (size == queue.length)
            resize(queue.length * 2);

        queue[size++] = item;
    }

    public Item dequeue() {
        if (size == 0) throw new NoSuchElementException("Queue is empty.");
        if (size <= queue.length / 4) resize(queue.length / 2);

        int index = StdRandom.uniform(size);

        Item item = queue[index];
        queue[index] = queue[--size];
        queue[size] = null;

        return item;
    }

    public Item sample() {
        if (size == 0) throw new NoSuchElementException("Queue is empty.");
        int index = StdRandom.uniform(size);
        return queue[index];
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            copy[i] = queue[i];
        }
        queue = copy;
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private final Item[] copy = (Item[]) new Object[queue.length];
        private int copySize = size;

        public RandomizedQueueIterator() {
            for (int i = 0; i < queue.length; i++) {
                copy[i] = queue[i];
            }
        }

        @Override
        public boolean hasNext() {
            return copySize > 0;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            int rd = StdRandom.uniform(copySize);
            Item item = copy[rd];
            if (rd != copySize - 1)
                copy[rd] = copy[copySize - 1];
            copy[copySize - 1] = null;
            copySize--;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        for (int i = 0; i < 10; i++) {
            rq.enqueue(i);
            System.out.printf("Added element: %d\n", i);
            System.out.printf("elements in queue: %d\n", rq.size());
        }
        System.out.println(rq.sample());
        System.out.print("\nIterator test:\n[");
        for (Integer elem : rq)
            System.out.print(elem + " ");
        System.out.println("]\n");

        for (int i = 0; i < 10; i++) {
            System.out.printf("Removed element: %d\n", rq.dequeue());
            System.out.printf("Current number of elements in queue: %d\n", rq.size());
        }
        System.out.println(rq.isEmpty());
    }
}