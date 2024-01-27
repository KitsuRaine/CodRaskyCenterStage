package org.firstinspires.ftc.teamcode.utilities;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Gyroscope {

    BNO055IMU imu;

    public static double fixAngle(double angle) {

        while (angle >= Geometry.pi * 2)
            angle -= Geometry.pi * 2;
        while (angle < 0)
            angle += Geometry.pi * 2;

        return angle;
    }

    public static double difAngle(double last, double angle) {

        if (last < 0 && angle > 0) {

            double ret = Geometry.pi - angle + Geometry.pi + last;
            if (ret <= Geometry.pi)
                return ret;
            return -(2. * Geometry.pi - ret);
        }
        if (last > 0 && angle < 0) {

            double ret = Geometry.pi + angle + Geometry.pi - last;
            if (ret <= Geometry.pi)
                return -ret;
            return 2. * Geometry.pi - ret;
        }
        return -(angle - last);
    }

    public Gyroscope(HardwareMap hardwareMap) {

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        imu.initialize(parameters);
    }

    public double getAngle() {
        return imu.getAngularOrientation().firstAngle;
    }

    public double getAngleDegree() {
        return 180 * this.getAngle() / Geometry.pi;
    }
}
