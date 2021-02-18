package ru.job4j.concurrent.pool;

import org.junit.Test;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ParallelIndexSearchTest {

    private Integer[] randomArrayInit(int[] targetIndexes) {
        Integer[] array = new Integer[250];
        for (int i = 0; i < 250; i++) {
            array[i] = (int) (Math.random() * 100);
        }
        for (int i : targetIndexes) {
            int target = 555;
            array[i] = target;
        }
        return array;
    }

    @Test
    public void whenNoElementsFound() {
        ForkJoinPool pool = new ForkJoinPool();
        int[] targetIndexes = new int[0];
        ParallelIndexSearch<Integer> search = new ParallelIndexSearch<>(randomArrayInit(targetIndexes), 555);
        assertThat(pool.invoke(search).length, is(0));
    }

    @Test
    public void when1ElementFound() {
        ForkJoinPool pool = new ForkJoinPool();
        int[] targetIndexes = {78};
        ParallelIndexSearch<Integer> search = new ParallelIndexSearch<>(randomArrayInit(targetIndexes), 555);
        int[] result = pool.invoke(search);
        assertThat(result.length, is(1));
        assertThat(result[0], is(78));
    }

    @Test
    public void when3ElementsFound() {
        ForkJoinPool pool = new ForkJoinPool();
        int[] targetIndexes = {18, 145, 210};
        ParallelIndexSearch<Integer> search = new ParallelIndexSearch<>(randomArrayInit(targetIndexes), 555);
        int[] result = pool.invoke(search);
        assertThat(result.length, is(3));
        assertThat(result[0], is(18));
        assertThat(result[1], is(145));
        assertThat(result[2], is(210));
    }

    @Test
    public void whenAllElementsFound() {
        ForkJoinPool pool = new ForkJoinPool();
        Integer[] array = new Integer[10];
        Arrays.fill(array, 555);
        ParallelIndexSearch<Integer> search = new ParallelIndexSearch<>(array, 555);
        int[] result = pool.invoke(search);
        assertThat(result.length, is(10));
    }
}