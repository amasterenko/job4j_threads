package ru.job4j.concurrent.pool;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Calculate sum of columns and rows of a 2-D array using sequential and asynchronous methods
 */

public class RowColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public Sums() {
            this.colSum = 0;
            this.rowSum = 0;
        }

        public int getRowSum() {
            return rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = sumsInit(matrix.length);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                sums[i].setRowSum(sums[i].getRowSum() + matrix[i][j]);
                sums[j].setColSum(sums[j].getColSum() + matrix[i][j]);
            }
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] sums = sumsInit(matrix.length);
        List<Map<Integer, CompletableFuture<Integer>>> futures = new LinkedList<>();
        for (int i = 0; i < matrix.length; i++) {
            Map<Integer, CompletableFuture<Integer>> map = new HashMap<>();
            map.put(1, getTaskColSum(matrix, i));
            map.put(2, getTaskRowSum(matrix, i));
            futures.add(map);
        }
        int i = 0;
        for (Map<Integer, CompletableFuture<Integer>> m : futures) {
            sums[i].setColSum(m.get(1).get());
            sums[i].setRowSum(m.get(2).get());
            i++;
        }
        return sums;
    }

    //task for summing a column
    private static CompletableFuture<Integer> getTaskColSum(int[][] data, int col) {
        return CompletableFuture.supplyAsync(() -> {
            int sum = 0;
            for (int i = 0; i < data.length; i++) {
                sum += data[i][col];
            }
            return sum;
        });
    }

    //task for summing a row
    private static CompletableFuture<Integer> getTaskRowSum(int[][] data, int row) {
        return CompletableFuture.supplyAsync(() -> {
            int sum = 0;
            for (int i = 0; i < data.length; i++) {
                sum += data[row][i];
            }
            return sum;
        });
    }

    private static Sums[] sumsInit(int size) {
        Sums[] sums = new Sums[size];
        for (int i = 0; i < sums.length; i++) {
            sums[i] = new Sums();
        }
        return sums;
    }
}
