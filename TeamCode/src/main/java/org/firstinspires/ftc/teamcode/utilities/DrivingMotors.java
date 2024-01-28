package org.firstinspires.ftc.teamcode.utilities;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class DrivingMotors {

    private Motor leftRear, rightRear, leftFront, rightFront;
    private double leftR, leftF, rightR, rightF; // powers
    private double maxi; // aux

    public DrivingMotors(HardwareMap hardwareMap) {

        leftRear = new Motor(hardwareMap, "stangaSpate", false, true, false);
        leftFront = new Motor(hardwareMap, "stangaFataE", true, true, false);
        rightFront = new Motor(hardwareMap, "dreaptaFata", false, true, false);
        rightRear = new Motor(hardwareMap, "dreaptaSpateE", true, true, false);
    }

    private double abs(double x) {
        return x < 0 ? -x : x;
    }

    private double max(double x, double y) {
        return x < y ? y : x;
    }

    public void run(double x, double y, double r) {

        if (abs(x) <= 0.1)
            x = 0;
        if (abs(y) <= 0.1)
            y = 0;
        if (abs(r) <= 0.1)
            r = 0;

        leftR = leftF = rightR = rightF = y;

        rightF -= x;
        rightR += x;
        leftF += x;
        leftR -= x;

        leftF += r;
        leftR += r;
        rightR -= r;
        rightF -= r;

        maxi = max(max(abs(leftR), abs(leftF)), max(abs(rightR), abs(rightF)));

        if (maxi > 1) {

            leftF /= maxi;
            rightF /= maxi;
            leftR /= maxi;
            rightR /= maxi;
        }

        leftRear.setPower(leftR);
        leftFront.setPower(leftF);
        rightRear.setPower(rightR);
        rightFront.setPower(rightF);
    }

    public void set(double pow, double r) {

        rightF = pow;
        rightR = pow;
        leftF = pow;
        leftR = pow;

        rightF -= r;
        rightR -= r;
        leftF += r;
        leftR += r;

        maxi = max(max(abs(rightF), abs(rightR)), max(abs(leftF), abs(leftR)));
        if (maxi > 1) {

            rightF /= maxi;
            rightR /= maxi;
            leftF /= maxi;
            leftR /= maxi;
        }

        rightFront.setPower(rightF);
        rightRear.setPower(rightR);
        leftFront.setPower(leftF);
        leftRear.setPower(leftR);
    }

    public Motor getLeftEncoder() {
        return leftFront;
    }
    public Motor getFrontEncoder() {
        return rightRear;
    }
}
