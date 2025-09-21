package main.java.algo.select;

import main.java.util.Metrics;

import java.util.Arrays;

public final class DeterministicSelect {

    private DeterministicSelect() {}

    public static int select(int[] a, int k, Metrics m) {
        if (a == null || a.length == 0) throw new IllegalArgumentException("array is empty");
        if (k < 0 || k >= a.length) throw new IllegalArgumentException("k out of range");
        int[] copy = Arrays.copyOf(a, a.length);
        if (m != null) m.allocations += copy.length;
        long t0 = System.nanoTime();
        int ans = selectInPlace(copy, 0, copy.length - 1, k, m);
        if (m != null) m.elapsedNanos = System.nanoTime() - t0;
        return ans;
    }

    private static int selectInPlace(int[] a, int l, int r, int k, Metrics m) {
        while (true) {
            if (l == r) return a[l];

            int pivotIdx = medianOfMediansIndex(a, l, r, m);
            int pivotVal = a[pivotIdx];
            int p = partitionAroundValue(a, l, r, pivotVal, m);

            if (k == p) return a[p];
            if (k < p) r = p - 1;
            else       l = p + 1;
        }
    }

    private static int medianOfMediansIndex(int[] a, int l, int r, Metrics m) {
        int n = r - l + 1;
        if (n <= 5) {
            insertionSort(a, l, r, m);
            return l + n / 2;
        }

        int write = l;
        for (int i = l; i <= r; i += 5) {
            int gL = i;
            int gR = Math.min(i + 4, r);
            insertionSort(a, gL, gR, m);
            int medIdx = gL + (gR - gL) / 2;
            swap(a, write++, medIdx, m);
        }

        int numMedians = write - l;
        int midIndex = l + (numMedians - 1) / 2;

        if (m != null) m.onEnter();
        int momValue = selectInPlace(a, l, write - 1, midIndex, m);
        if (m != null) m.onExit();

        return findValueIndex(a, l, r, momValue, m);
    }

    private static int findValueIndex(int[] a, int l, int r, int value, Metrics m) {
        for (int i = l; i <= r; i++) {
            if (m != null) m.comparisons++;
            if (a[i] == value) return i;
        }
        return l;
    }

    private static int partitionAroundValue(int[] a, int l, int r, int pivotValue, Metrics m) {
        int pivotIdx = findValueIndex(a, l, r, pivotValue, m);
        swap(a, pivotIdx, r, m);

        int store = l;
        for (int i = l; i < r; i++) {
            if (m != null) m.comparisons++;
            if (a[i] < pivotValue) {
                swap(a, store, i, m);
                store++;
            }
        }
        swap(a, store, r, m);
        return store;
    }

    private static void swap(int[] a, int i, int j, Metrics m) {
        if (i == j) return;
        int t = a[i]; a[i] = a[j]; a[j] = t;
        if (m != null) m.swaps++;
    }

    private static void insertionSort(int[] a, int l, int r, Metrics m) {
        for (int i = l + 1; i <= r; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= l) {
                if (m != null) m.comparisons++;
                if (a[j] <= key) break;
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = key;
        }
    }
}
