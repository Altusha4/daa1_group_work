package main.java.algo.quicksort;

import main.java.util.Metrics;

import java.util.concurrent.ThreadLocalRandom;

public final class QuickSort {

    private QuickSort() {}

    public static void sort(int[] a, Metrics m) {
        if (a == null || a.length < 2) {
            if (m != null) m.elapsedNanos = 0;
            return;
        }
        long t0 = System.nanoTime();

        int lo = 0, hi = a.length - 1;
        while (lo < hi) {
            int p = partitionRandom(a, lo, hi, m);
            int left = p - lo;
            int right = hi - p;

            if (left < right) {
                if (m != null) m.onEnter();
                sortRec(a, lo, p - 1, m);
                if (m != null) m.onExit();
                lo = p + 1;
            } else {
                if (m != null) m.onEnter();
                sortRec(a, p + 1, hi, m);
                if (m != null) m.onExit();
                hi = p - 1;
            }
        }

        if (m != null) m.elapsedNanos = System.nanoTime() - t0;
    }

    private static void sortRec(int[] a, int lo, int hi, Metrics m) {
        while (lo < hi) {
            int p = partitionRandom(a, lo, hi, m);
            if (p - lo < hi - p) {
                if (m != null) m.onEnter();
                sortRec(a, lo, p - 1, m);
                if (m != null) m.onExit();
                lo = p + 1;
            } else {
                if (m != null) m.onEnter();
                sortRec(a, p + 1, hi, m);
                if (m != null) m.onExit();
                hi = p - 1;
            }
        }
    }

    private static int partitionRandom(int[] a, int lo, int hi, Metrics m) {
        int r = ThreadLocalRandom.current().nextInt(lo, hi + 1);
        swap(a, r, hi, m);

        int pivot = a[hi];
        int i = lo;
        for (int j = lo; j < hi; j++) {
            if (m != null) m.comparisons++;
            if (a[j] <= pivot) {
                swap(a, i, j, m);
                i++;
            }
        }
        swap(a, i, hi, m);
        return i;
    }

    private static void swap(int[] a, int i, int j, Metrics m) {
        if (i == j) return;
        int t = a[i]; a[i] = a[j]; a[j] = t;
        if (m != null) m.swaps++;
    }
}
