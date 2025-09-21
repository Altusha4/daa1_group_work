package main.java.cli;

import main.java.algo.select.DeterministicSelect;
import main.java.util.Metrics;

import java.util.Arrays;
import java.util.Random;

public final class DeterministicSelectMain {
    public static void main(String[] args) {
        testCorrectness();
        testPerformance();
    }

    private static void testCorrectness() {
        System.out.println("=== Testing Correctness ===");
        Random rnd = new Random(42);
        int numTests = 100;
        int arraySize = 100;

        for (int test = 0; test < numTests; test++) {
            int[] arr = new int[arraySize];
            for (int i = 0; i < arraySize; i++) arr[i] = rnd.nextInt(1000);

            int[] sorted = arr.clone();
            Arrays.sort(sorted);

            for (int k = 0; k < arraySize; k++) {
                Metrics m = new Metrics();
                int result = DeterministicSelect.select(arr.clone(), k, m);
                if (result != sorted[k]) {
                    System.out.printf("Error: test=%d, k=%d, expected=%d, got=%d%n",
                            test, k, sorted[k], result);
                    return;
                }
            }
        }
        System.out.println("All correctness tests passed!");
    }

    private static void testPerformance() {
        System.out.println("\n=== Testing Performance ===");
        Random rnd = new Random(42);
        int[] sizes = {1000, 5000, 10000};

        for (int n : sizes) {
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) arr[i] = rnd.nextInt();

            int k = n / 2;
            Metrics m = new Metrics();

            long startTime = System.nanoTime();
            int result = DeterministicSelect.select(arr, k, m);
            long time = System.nanoTime() - startTime;

            int[] sorted = arr.clone();
            Arrays.sort(sorted);
            boolean correct = (result == sorted[k]);

            System.out.printf("n=%-6d | time=%-8.3fms | comps=%-10d | swaps=%-10d | depth=%-3d | correct=%b%n",
                    n, time / 1e6, m.comparisons, m.swaps, m.maxRecursionDepth, correct);
        }
    }
}
