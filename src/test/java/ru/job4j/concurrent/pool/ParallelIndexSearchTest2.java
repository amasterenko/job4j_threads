package ru.job4j.concurrent.pool;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ParallelIndexSearchTest2 {
    private final Integer[] array = new Integer[250];

    @Before
    public void arrayInit() {
        for (int i = 0; i < 250; i++) {
            array[i] = i;
        }
    }

    @Test
    public void whenNoElementsFound() {
        ForkJoinPool pool = new ForkJoinPool();
        ParallelIndexSearch2<Integer> search = new ParallelIndexSearch2<>(array, 555);
        assertThat(pool.invoke(search), is(-1));
    }

    @Test
    public void whenElementFound() {
        ForkJoinPool pool = new ForkJoinPool();
        ParallelIndexSearch2<Integer> search = new ParallelIndexSearch2<>(array, 175);
        assertThat(pool.invoke(search), is(175));
    }

    @Test
    public void when2TargetElementsThenFirstFound() {
        array[11] = 111;
        ForkJoinPool pool = new ForkJoinPool();
        ParallelIndexSearch2<Integer> search = new ParallelIndexSearch2<>(array, 111);
        assertThat(pool.invoke(search), is(11));
    }
}