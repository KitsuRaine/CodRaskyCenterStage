package org.firstinspires.ftc.teamcode.utilities;

public class DrivingMotorsCentric {

    private DrivingMotors drive;
    private Pair<Double, Double> p; // aux
    Gyroscope gyroscope;

    public DrivingMotorsCentric(Gyroscope gyroscope, DrivingMotors drive) {

        this.gyroscope = gyroscope;
        this.drive = drive;
    }

    public void run(double x, double y, double r) {

        // fix angle
        p = Geometry.move(x, y, gyroscope.getAngle());
        x = p.first;
        y = p.second;

        // run
        drive.run(x, y, r);
    }
}
