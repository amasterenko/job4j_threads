package ru.job4j.concurrent.synch;

import java.util.*;
import java.util.function.Consumer;

public class SimpleArray<T> implements Iterable<T> {
    private Object[] container;
    private int length;
    private int elementsCounter = 0;
    private float factor;
    private int modCounter = 0;

    public SimpleArray(int initialLength, float factor) {
        if (initialLength <= 0) {
            throw new IllegalArgumentException("Size of the SimpleArray must be grater than 0.");
        }
        if (factor <= 1) {
            throw new IllegalArgumentException("The extend factor must be grater than 1.");
        }
        this.container = new Object[initialLength];
        this.factor = factor;
        this.length = initialLength;
    }

    public SimpleArray() {
        this(10, 1.5f);
    }

    public T get(int index) {
        return (T) container[Objects.checkIndex(index, elementsCounter)];
    }

    public void add(T model) {
        if (elementsCounter == length - 1) {
            container = extend();
        }
        container[elementsCounter++] = model;
        modCounter++;
    }

    private Object[] extend() {
        int newLength =  (int) Math.ceil(length * factor);
        Object[] newContainer = new Object[newLength];
        System.arraycopy(container, 0, newContainer, 0, length - 1);
        length = newLength;
        return newContainer;
    }

    @Override
    public Iterator<T> iterator() {
        if (elementsCounter == 0) {
            return Collections.EMPTY_LIST.iterator();
        }
        return new SimpleArrayIterator<>(this);
    }

    private static class SimpleArrayIterator<T> implements Iterator<T> {
        private final SimpleArray<T> array;
        private final int size;
        private int pointer = 0;
        private int expectedModCounter;

        public SimpleArrayIterator(SimpleArray<T> array) {
            this.array = array;
            this.size = array.elementsCounter;
            this.expectedModCounter = array.modCounter;
        }

        @Override
        public boolean hasNext() {
            return pointer < size;
        }

        @Override
        public T next() {
            if (expectedModCounter != array.modCounter) {
                throw new ConcurrentModificationException();
            }
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return array.get(pointer++);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void forEachRemaining(Consumer<? super T> action) {
            while (hasNext()) {
                action.accept(next());
            }
        }
    }
}
