package ru.job4j.concurrent.pool;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class RolColSumTest {
    private int[][] array = {
            {1, 0, 3},
            {2, 4, 1},
            {3, 4, 5}
    };

    @Test
    public void whenSumOfFirstColThen6() {
        assertThat(RowColSum.sum(array)[0].getColSum(), is(6));
    }

    @Test
    public void whenSumOfSecColThen8() {
        assertThat(RowColSum.sum(array)[1].getColSum(), is(8));
    }

    @Test
    public void whenSumOfThirdColThen11() {
        assertThat(RowColSum.sum(array)[2].getColSum(), is(9));
    }

    @Test
    public void whenSumOfFirstRowThen4() {
        assertThat(RowColSum.sum(array)[0].getRowSum(), is(4));
    }

    @Test
    public void whenSumOfSecRowThen7() {
        assertThat(RowColSum.sum(array)[1].getRowSum(), is(7));
    }

    @Test
    public void whenSumAndThirdColThen12() {
        assertThat(RowColSum.sum(array)[2].getRowSum(), is(12));
    }
}