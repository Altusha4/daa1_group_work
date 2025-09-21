package main.java.algo.closest;

import main.java.util.Metrics;

import java.util.*;

public final class ClosestPair2D {

    private ClosestPair2D() {}

    public static final class Point {
        public final double x, y;
        public Point(double x, double y) { this.x = x; this.y = y; }
    }

    public static double closest(Point[] pts, Metrics m) {
        if (pts == null || pts.length < 2) return Double.POSITIVE_INFINITY;

        long t0 = System.nanoTime();

        Point[] px = pts.clone();
        Point[] py = pts.clone();
        Arrays.sort(px, Comparator.comparingDouble(p -> p.x));
        Arrays.sort(py, Comparator.comparingDouble(p -> p.y));

        m.onEnter();
        double ans = dac(px, py, m);
        m.onExit();

        m.elapsedNanos = System.nanoTime() - t0;
        return ans;
    }

    private static double dac(Point[] px, Point[] py, Metrics m) {
        int n = px.length;
        if (n <= 3) {
            return brute(px, m);
        }

        int mid = n >>> 1;
        double midX = px[mid].x;

        Point[] Lx = Arrays.copyOfRange(px, 0, mid);
        Point[] Rx = Arrays.copyOfRange(px, mid, n);

        List<Point> Ly = new ArrayList<>(mid);
        List<Point> Ry = new ArrayList<>(n - mid);
        for (Point p : py) {
            if (p.x < midX || (p.x == midX && Ly.size() < Lx.length)) Ly.add(p);
            else Ry.add(p);
        }

        m.onEnter();
        double dl = dac(Lx, Ly.toArray(new Point[0]), m);
        m.onExit();

        m.onEnter();
        double dr = dac(Rx, Ry.toArray(new Point[0]), m);
        m.onExit();

        double d = Math.min(dl, dr);

        List<Point> strip = new ArrayList<>();
        for (Point p : py) {
            if (Math.abs(p.x - midX) < d) strip.add(p);
        }

        for (int i = 0; i < strip.size(); i++) {
            Point a = strip.get(i);
            for (int j = i + 1; j < strip.size() && j <= i + 7; j++) {
                if (strip.get(j).y - a.y >= d) break;
                m.comparisons++;
                double cur = dist(a, strip.get(j));
                if (cur < d) d = cur;
            }
        }

        return d;
    }

    private static double brute(Point[] a, Metrics m) {
        double d = Double.POSITIVE_INFINITY;
        for (int i = 0; i < a.length; i++) {
            for (int j = i + 1; j < a.length; j++) {
                m.comparisons++;
                double cur = dist(a[i], a[j]);
                if (cur < d) d = cur;
            }
        }
        return d;
    }

    private static double dist(Point p, Point q) {
        double dx = p.x - q.x, dy = p.y - q.y;
        return Math.hypot(dx, dy);
    }
}
