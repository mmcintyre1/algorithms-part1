import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size = 0;

    @SuppressWarnings("unchecked")
    private Item[] queue = (Item[]) new Object[1];

    private final Random rand = new Random();

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
        if (size == 0) throw new UnsupportedOperationException("Queue is empty.");
        if (size <= queue.length / 4) resize(queue.length / 2);

        int index = rand.nextInt(size);
        Item item = queue[index];
        queue[index] = queue[--size];
        queue[size] = null;

        return item;
    }

    public Item sample() {
        if (size == 0) throw new NoSuchElementException("Queue is empty.");
        int index = rand.nextInt(size);
        return queue[index];
    }

    private void resize(int capacity) {
        System.out.println("Resizing from " + queue.length + " to " + capacity);
        queue = Arrays.copyOfRange(queue, 0, capacity);
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private final Item[] shuffledArray;
        private int current = 0;

        public RandomizedQueueIterator() {
            shuffledArray = queue.clone();
            shuffle(shuffledArray);
        }

        @Override
        public boolean hasNext() {
            return current < queue.length;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return shuffledArray[current++];
        }

        public void shuffle(Item[] array) {
            int n = array.length;
            for (int i = 0; i < n; i++) {
                int r = i + (int) (Math.random() * (n - i));
                Item swap = array[r];
                array[i] = swap;
            }
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

        System.out.print("\nIterator test:\n[");
        for (Integer elem : rq)
            System.out.print(elem + " ");
        System.out.println("]\n");

        for (int i = 0; i < 10; i++) {
            System.out.printf("Removed element: %d\n", rq.dequeue());
            System.out.printf("Current number of elements in queue: %d\n", rq.size());
        }
    }
}