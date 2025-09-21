package main.java.algo.mergesort;

import java.util.Arrays;

public final class MergeSort {

    private MergeSort() {}

    public static void mergeSort(int[] arr) {
        if (arr == null || arr.length < 2) return;
        int[] buffer = new int[arr.length];
        mergeSortRecursive(arr, buffer, 0, arr.length - 1);
    }

    private static void mergeSortRecursive(int[] arr, int[] buffer, int left, int right) {
        if (left >= right) return;
        int mid = left + (right - left) / 2;
        mergeSortRecursive(arr, buffer, left, mid);
        mergeSortRecursive(arr, buffer, mid + 1, right);
        merge(arr, buffer, left, mid, right);
    }

    private static void merge(int[] arr, int[] buffer, int left, int mid, int right) {
        for (int i = left; i <= right; i++) buffer[i] = arr[i];

        int i = left, j = mid + 1, k = left;
        while (i <= mid && j <= right) {
            if (buffer[i] <= buffer[j]) arr[k++] = buffer[i++];
            else                        arr[k++] = buffer[j++];
        }
        while (i <= mid) arr[k++] = buffer[i++];
    }

}
