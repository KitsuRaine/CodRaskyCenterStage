package org.firstinspires.ftc.teamcode.utilities;

public class Geometry {

    public static final double pi = 3.1415926535897932;

    public static double distance (double x1, double y1, double x2, double y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    // move a line from (0, 0) to (x, y) by "angle" radians
    public static Pair<Double, Double> move(double x, double y, double angle) {

        Pair<Double, Double> ret = new Pair<Double, Double>();
        double aux = Math.atan2(y, x);

        angle = aux - angle;

        ret.first = distance(0, 0, x, y) * Math.cos(angle);
        ret.second = distance(0, 0, x, y) * Math.sin(angle);

        return ret;
    }

    // go forward by the angle
    public static Pair<Double, Double> forward(double x, double y, double dst, double angle) {

        Pair <Double, Double> ret = new Pair<>();

        ret.first = x + dst * Math.cos(angle);
        ret.second = y + dst * Math.sin(angle);

        return ret;
    }

    public static Pair<Double, Double> edge(double dst, double angle) {

        if (dst == 0)
            return Pair.make(0., 0.);

        if (angle == 0)
            return Pair.make(0, dst);

        double multiX = 1, multiY = 1;

        if (angle > 0)
            multiX = -1;
        if (dst < 0)
            multiY = -1;

        angle = Math.abs(angle);
        dst = Math.abs(dst);

        double r = dst / angle;

        Pair<Double, Double> ret = new Pair<>();

        ret.first = (Math.cos(angle) * r - r) * multiX;
        ret.second = Math.sin(angle) * r * multiY;

        return ret;
    }

    public static double toDegrees(double angle) {
        return 180 * angle / pi;
    }
}
