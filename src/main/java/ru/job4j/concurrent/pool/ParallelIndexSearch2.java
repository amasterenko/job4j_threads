package ru.job4j.concurrent.pool;

import java.util.*;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

/**
 * Search for the index of a given object in an array of objects
 * @param <T> type of the object
 */
public class ParallelIndexSearch2<T> extends RecursiveTask<Integer> {
    private final T[] array;
    //using for storing the original array's index
    private final int startIndex;
    private final T targetObject;
    private static final int THRESHOLD = 10;

    public ParallelIndexSearch2(T[] array, T targetObject) {
        this.array = array;
        this.startIndex = 0;
        this.targetObject = targetObject;
    }

    private ParallelIndexSearch2(T[] array, int startIndex, T targetObject) {
        this.array = array;
        this.startIndex = startIndex;
        this.targetObject = targetObject;
    }

    @Override
    protected Integer compute() {
        if (array.length <= THRESHOLD) {
            return indexSearch(array, startIndex, targetObject);
        }
        ParallelIndexSearch2<T> left = new ParallelIndexSearch2<>(
                Arrays.copyOfRange(array, 0, array.length / 2),
                startIndex,
                targetObject);

        ParallelIndexSearch2<T> right = new ParallelIndexSearch2<>(
                Arrays.copyOfRange(array, array.length / 2, array.length),
                startIndex + array.length / 2,
                targetObject);

        return invokeAll(List.of(left, right))
                .stream()
                .map(ForkJoinTask::join)
                .filter(i -> i != -1)
                .findFirst()
                .orElse(-1);
    }

    //linear searching
    private int indexSearch(T[] array, int markedStartIndex, T targetObject) {
        for (int i = 0; i < array.length; i++) {
            if (Objects.equals(array[i], targetObject)) {
                return i + markedStartIndex;
            }
        }
        return -1;
    }
}