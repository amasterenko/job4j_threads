package ru.job4j.concurrent.pool;

import java.util.*;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

/**
 * Search for the index(es) of a given object in an array of objects
 * @param <T> type of the object
 */
public class ParallelIndexSearch<T> extends RecursiveTask<int[]> {
    private final T[] array;
    //using for storing the original array's index
    private final int startIndex;
    private final T targetObject;
    private static final int THRESHOLD = 10;

    public ParallelIndexSearch(T[] array, T targetObject) {
        this.array = array;
        this.startIndex = 0;
        this.targetObject = targetObject;
    }

    private ParallelIndexSearch(T[] array, int startIndex, T targetObject) {
        this.array = array;
        this.startIndex = startIndex;
        this.targetObject = targetObject;
    }

    @Override
    protected int[] compute() {
        if (array.length <= THRESHOLD) {
            return indexSearch(array, startIndex, targetObject);
        }

        return invokeAll(createSubtasks())
                .stream()
                .map(ForkJoinTask::join)
                .flatMapToInt(IntStream::of)
                .toArray();
    }

    //divide the array into two parts and create a list of two subtasks
    private Collection<ParallelIndexSearch<T>> createSubtasks() {
        List<ParallelIndexSearch<T>> subTasks = new ArrayList<>();
        subTasks.add(new ParallelIndexSearch<>(
                Arrays.copyOfRange(array, 0, array.length / 2),
                startIndex,
                targetObject)
        );
        subTasks.add(new ParallelIndexSearch<>(
                Arrays.copyOfRange(array, array.length / 2, array.length),
                startIndex + array.length / 2,
                targetObject)
        );
        return subTasks;
    }

    //linear searching
    private int[] indexSearch(T[] array, int markedStartIndex, T targetObject) {
        List<Integer> resList = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            if (Objects.equals(array[i], targetObject)) {
                resList.add(i + markedStartIndex);
            }
        }
        return resList.stream().mapToInt(i -> i).toArray();
    }
}