package main.java.cli;

import main.java.algo.quicksort.QuickSort;
import main.java.util.Metrics;

import java.util.Arrays;
import java.util.Random;

public final class QuickSortMain {
    public static void main(String[] args) {
        demoExample();
        testCorrectness();
        testPerformance();
    }

    private static void demoExample() {
        System.out.println("=== QuickSort: Demo Example ===");
        int[] data = {38, 27, 43, 3, 9, 82, 10, -5, 0, 7};
        System.out.println("Before: " + Arrays.toString(data));
        QuickSort.sort(data, new Metrics());
        System.out.println("After:  " + Arrays.toString(data));
        System.out.println();
    }

    private static void testCorrectness() {
        System.out.println("=== QuickSort: Testing Correctness ===");
        Random rnd = new Random(42);

        for (int t = 0; t < 50; t++) {
            int n = 50 + rnd.nextInt(100);
            int[] a = rnd.ints(n, -1_000, 1_000).toArray();
            int[] b = a.clone(); Arrays.sort(b);

            Metrics m = new Metrics();
            QuickSort.sort(a, m);

            if (!Arrays.equals(a, b)) {
                System.out.println("❌ Mismatch on test " + t);
                return;
            }
        }
        System.out.println("✅ All correctness tests passed!");
    }

    private static void testPerformance() {
        System.out.println("\n=== QuickSort: Testing Performance ===");
        Random rnd = new Random(1);
        int[] sizes = {1_000, 5_000, 10_000, 20_000};

        for (int n : sizes) {
            int[] a = rnd.ints(n).toArray();
            Metrics m = new Metrics();

            QuickSort.sort(a, m);

            System.out.printf("n=%-6d | time=%7.3f ms | comps=%-10d | swaps=%-10d | depth=%-3d | sorted=%b%n",
                    n, m.elapsedNanos / 1e6, m.comparisons, m.swaps, m.maxRecursionDepth, isSorted(a));
        }
    }

    private static boolean isSorted(int[] a) {
        for (int i = 1; i < a.length; i++) if (a[i - 1] > a[i]) return false;
        return true;
    }
}
