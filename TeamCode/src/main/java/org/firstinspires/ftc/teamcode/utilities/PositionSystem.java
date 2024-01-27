package org.firstinspires.ftc.teamcode.utilities;

import com.qualcomm.robotcore.hardware.HardwareMap;

// version 3.0

public class PositionSystem {

    private Motor vertical; // encoder for y
    private Motor horizontal; // encoder for x
    private Gyroscope gyroscope;
    private double lastAngle, lastVertical, lastHorizontal;
    private double x, y, xx, yy; // position
    private Pair<Double, Double> d;
    private final double radius = 11.87;

    // auxiliars
    private double dif, angle, currVertical, currHorizontal;
    private double deltaHorizontal, deltaVertical;

    // cm / tic
    private static final double unit = 0.001438973148761;

    private static double toCM(double tics) {
        return unit * tics;
    }

    public int horizontalPos() {
        return -horizontal.getPosition();
    }
    public int verticalPos() {
        return vertical.getPosition();
    }

    public PositionSystem(HardwareMap hardwareMap, Gyroscope gyroscope, Motor vertical, Motor horizontal) {

        this.vertical = vertical;
        this.horizontal = horizontal;
        this.gyroscope = gyroscope;
        lastAngle = 0;
        x = y = 0;
        lastHorizontal = lastVertical = 0;
    }

    public void run() {

        currVertical = verticalPos();
        currHorizontal = horizontalPos();
        angle = gyroscope.getAngle();
        dif = gyroscope.difAngle(lastAngle, angle);

        deltaHorizontal = currHorizontal - lastHorizontal;
        deltaVertical = currVertical - lastVertical;

        d = Geometry.forward(x, y, Geometry.distance(0, 0, deltaHorizontal, deltaVertical), Gyroscope.fixAngle(lastAngle + Math.atan2(deltaVertical, deltaHorizontal)));

        x = d.first;
        y = d.second;

        // =========== final ===========

        lastAngle = angle;
        lastVertical = currVertical;
        lastHorizontal = currHorizontal;
    }

    public Pair<Double, Double> getPosition() {
        return Pair.make(toCM(x) + correctX(), toCM(y) + correctY());
    }

    public Pair<Double, Double> getValue() {
        return Pair.make(1.0 * horizontalPos(), 1.0 * verticalPos());
    }

    public int test1() {
        return horizontalPos();
    }

    public int test2() {
        return verticalPos();
    }

    public double getAngle() {
        return gyroscope.getAngle();
    }

    private double correctX() {
        return Math.cos(Gyroscope.fixAngle(Geometry.pi + Geometry.pi / 4)) * radius - Math.cos(Gyroscope.fixAngle(Geometry.pi + Geometry.pi / 4 + angle)) * radius;
    }
    private double correctY() {
        return Math.sin(Gyroscope.fixAngle(Geometry.pi + Geometry.pi / 4)) * radius - Math.sin(Gyroscope.fixAngle(Geometry.pi + Geometry.pi / 4 + angle)) * radius;
    }
}
