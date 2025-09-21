package main.java.cli;

import main.java.algo.closest.ClosestPair2D;
import main.java.algo.closest.ClosestPair2D.Point;
import main.java.util.Metrics;

import java.util.Random;
import java.util.Locale;
import java.util.Arrays;

public final class ClosestPairMain {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        demoExample();
        randomPerformance();
    }

    private static void demoExample() {
        System.out.println("=== Closest Pair: Demo Example ===");
        Point[] pts = {
                new Point(0.0, 0.0),
                new Point(1.0, 1.0),
                new Point(2.0, 2.0),
                new Point(1.2, 1.1),
                new Point(5.0, 5.0),
        };
        Metrics m = new Metrics();
        double d = ClosestPair2D.closest(pts, m);
        System.out.printf("Points: %s%n", formatPoints(pts));
        System.out.printf("Closest distance = %.6f%n", d);
        System.out.printf("comparisons=%d | maxDepth=%d | time=%.3f ms%n",
                m.comparisons, m.maxRecursionDepth, m.elapsedNanos / 1e6);
        System.out.println();
    }

    private static void randomPerformance() {
        System.out.println("=== Closest Pair: Testing Performance ===");
        int[] sizes = {1_000, 5_000, 10_000};
        Random rnd = new Random(42);

        for (int n : sizes) {
            Point[] pts = new Point[n];
            for (int i = 0; i < n; i++) {
                pts[i] = new Point(rnd.nextDouble(), rnd.nextDouble());
            }

            if (n >= 2) {
                pts[0] = new Point(0.123456, 0.654321);
                pts[1] = new Point(0.123456, 0.654321);
            }

            Metrics m = new Metrics();
            double d = ClosestPair2D.closest(pts, m);

            System.out.printf("n=%-6d | d=%.6f | comps=%-8d | depth=%-3d | time=%7.3f ms%n",
                    n, d, m.comparisons, m.maxRecursionDepth, m.elapsedNanos / 1e6);
        }
    }

    private static String formatPoints(Point[] pts) {
        String[] arr = new String[pts.length];
        for (int i = 0; i < pts.length; i++) {
            arr[i] = String.format("(%.3f, %.3f)", pts[i].x, pts[i].y);
        }
        return Arrays.toString(arr);
    }
}
