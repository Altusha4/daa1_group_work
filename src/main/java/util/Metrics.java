package main.java.util;

public final class Metrics {
    public long comparisons, swaps, allocations;
    public int recursionDepth, maxRecursionDepth;
    public long elapsedNanos;

    public void onEnter() {
        if (++recursionDepth > maxRecursionDepth) maxRecursionDepth = recursionDepth;
    }
    public void onExit()  { --recursionDepth; }

    @Override
    public String toString() {
        return "Metrics{comparisons=" + comparisons + ", swaps=" + swaps +
                ", allocations=" + allocations + ", maxRecursionDepth=" + maxRecursionDepth +
                ", elapsedNanos=" + elapsedNanos + '}';
    }
}
