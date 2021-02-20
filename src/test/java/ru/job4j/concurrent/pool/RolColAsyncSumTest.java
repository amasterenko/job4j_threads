package ru.job4j.concurrent.pool;

import org.junit.Test;

import java.util.concurrent.ExecutionException;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class RolColAsyncSumTest {
    private int[][] array = {
            {1, 0, 3},
            {2, 4, 1},
            {3, 4, 5}
    };

    @Test
    public void whenAsyncSumOfFirstColThen6() throws ExecutionException, InterruptedException {
        assertThat(RowColSum.asyncSum(array)[0].getColSum(), is(6));
    }

    @Test
    public void whenAsyncSumOfSecColThen8() throws ExecutionException, InterruptedException {
        assertThat(RowColSum.asyncSum(array)[1].getColSum(), is(8));
    }

    @Test
    public void whenAsyncSumOfThirdColThen11() throws ExecutionException, InterruptedException {
        assertThat(RowColSum.asyncSum(array)[2].getColSum(), is(9));
    }

    @Test
    public void whenAsyncSumOfFirstRowThen4() throws ExecutionException, InterruptedException {
        assertThat(RowColSum.asyncSum(array)[0].getRowSum(), is(4));
    }

    @Test
    public void whenAsyncSumOfSecRowThen7() throws ExecutionException, InterruptedException {
        assertThat(RowColSum.asyncSum(array)[1].getRowSum(), is(7));
    }

    @Test
    public void whenAsyncSumAndThirdColThen12() throws ExecutionException, InterruptedException {
        assertThat(RowColSum.asyncSum(array)[2].getRowSum(), is(12));
    }
}