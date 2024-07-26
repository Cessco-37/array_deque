import java.util.ArrayList;
import java.util.List;

public class ArrayDeque<T> implements Deque<T> {

    private T[] list;
    private int size;
    private int first;
    private int last;
    private int initial = 8;

    public ArrayDeque() {
        list = (T[]) new Object[initial];
        size = 0;
        first = 0;
        last = 0;
    }

    private boolean upsizeNeeded() {
        return size == list.length;
    }

    private void resize(int capacity) {
        T[] newlist = (T[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            newlist[i] = list[(first + i) % list.length];
        }
        list = newlist;
        first = 0;
        last = size;
        if (isEmpty()) {
            first = 0;
            last = 0;
        }
    }

    @Override
    public void addFirst(T x) {
        if (upsizeNeeded()) {
            resize(size * 2);
        }
        first = (first + list.length - 1) % list.length;
        list[first] = x;
        size++;
    }

    @Override
    public void addLast(T x) {
        if (upsizeNeeded()) {
            resize(size * 2);
        }
        list[last] = x;
        last = (last + 1) % list.length;
        size++;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            int realIndex = (first + i) % list.length;
            returnList.add((T) list[realIndex]);
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T removedItem = (T) list[first];
        first = (first + 1) % list.length;
        size--;

        if (list.length >= initial && size < list.length / 4 * 2) {
            resize(list.length / 2);
        }
        return removedItem;

    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        last = (last - 1 + list.length) % list.length;
        T removedItem = (T) list[last];
        size--;

        if (list.length >= initial * 2 && size < list.length / 4) {
            resize(list.length / 2);
        }
        return removedItem;
    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        int realIndex = (first + index) % list.length;
        return (T) list[realIndex];
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }
}
