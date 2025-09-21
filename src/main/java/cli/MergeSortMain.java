package main.java.cli;

import main.java.algo.mergesort.MergeSort;

import java.util.Arrays;
import java.util.Random;

public final class MergeSortMain {
    public static void main(String[] args) {
        testCorrectness();
        testPerformance();
        demoExample();
    }

    private static void testCorrectness() {
        System.out.println("=== MergeSort: Testing Correctness ===");
        Random rnd = new Random(42);

        for (int t = 0; t < 5; t++) {
            int n = 10 + rnd.nextInt(10);
            int[] arr = rnd.ints(n, -50, 50).toArray();

            System.out.println("Before: " + Arrays.toString(arr));
            MergeSort.mergeSort(arr);
            System.out.println("After:  " + Arrays.toString(arr));
            System.out.println();
        }
    }

    private static void testPerformance() {
        System.out.println("\n=== MergeSort: Testing Performance ===");
        Random rnd = new Random(42);
        int[] sizes = {1000, 5000, 10000, 20000};

        for (int n : sizes) {
            int[] arr = rnd.ints(n).toArray();

            long start = System.nanoTime();
            MergeSort.mergeSort(arr);
            long elapsed = System.nanoTime() - start;

            System.out.printf("n=%-6d | time=%6.3f ms | sortedCorrectly=%b%n",
                    n, elapsed / 1e6, isSorted(arr));
        }
    }

    private static boolean isSorted(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i - 1] > arr[i]) return false;
        }
        return true;
    }

    private static void demoExample() {
        System.out.println("\n=== MergeSort: Demo Example ===");
        int[] data = {38, 27, 43, 3, 9, 82, 10};
        System.out.println("Before: " + Arrays.toString(data));
        MergeSort.mergeSort(data);
        System.out.println("After:  " + Arrays.toString(data));
    }
}
