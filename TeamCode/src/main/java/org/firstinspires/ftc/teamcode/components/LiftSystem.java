package org.firstinspires.ftc.teamcode.components;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.utilities.Motor;
import org.firstinspires.ftc.teamcode.utilities.Servo;

public class LiftSystem {

    private Motor motor1, motor2;
    private int target, tolerance, reachedTarget;
    private final int groundLevel = 0;
    public final int lowLevel = 275;
    public final int midLevel = 425;
    public final int highLevel = 650;
    private final double holdK = 0.15;
    private int resetTolerance = 0;
    boolean manualMode;
    private Servo flip;
    private Servo angleServo;
    private Servo microservo;
    public boolean isRunning = false;


    //* Values for the flip servo
    public static final class Flip {
        public static final double low = 5;
        public static final double mid = 2.5;
        public static final double high = 0;
    }

    //* Values for the angle servo
    public static final class Angle {
        public static final double low = 6.5;
        public static final double mid = 3.25;
        public static final double high = 0;
    }


    public LiftSystem(HardwareMap hardwareMap) {

        microservo = new Servo(hardwareMap, "microservo");
        microservo.setPowerRange(500, 2500);

        flip = new Servo(hardwareMap, "brat");
        flip.setPowerRange(500, 2500);

        angleServo = new Servo(hardwareMap, "inclinare");
        angleServo.setPowerRange(500, 2500);

        motor1 = new Motor(hardwareMap, "liftMotor", false, true, false);
        motor2 = new Motor(hardwareMap, "liftMotor2", true, true, false);

        motor1.setPower(holdK);
        motor2.setPower(holdK);

        tolerance = 10;
        target = 0;
        reachedTarget = 0;
        manualMode = true;

        microInitPos();
        flipInitPos();
        angleInitPos();
    }

    public void microInitPos() {

        microservo.setPosition(0.2);
    }

    public void microFirstPos() {

        microservo.setPosition(0.45);
    }

    public void microSecondPos() {

        microservo.setPosition(0.6);
    }

    public void angleInitPos() {
        angleServo.setPosition(0.360);
    }

    public void angleActivePos(double height) {
        angleServo.setPosition(0.18 - (height/100));
    }

    public void flipInitPos() {
        flip.setPosition(0.262);
    }

    public void flipActivePos(double height) {
        ///TODO: Modificare pe cele 3 heighturi
        flip.setPosition(0.82+(height/100));
    }

    public void setPower(double power) {

        motor1.setPower(power);
        motor1.setPower(power);
    }

    public int getPosition() {

        return motor1.getPosition();
    }

    public double getPower() {

        return motor1.getPower();
    }

    //* In some cases, the robot may initialize while the lift is still
    //* higher up, whether it be it may have been stuck or something else happened
    //* We need to set its position back to what it should've been, yet we dont know how low
    //* we're supposed to go
    //* Therefore, this function gets called to force the lift back into position,
    public void UnderGround() {

        target = groundLevel - resetTolerance;
        if(resetTolerance < 600)
            resetTolerance+= 10;
        manualMode = false;
    }

    public void toGround() {

        target = groundLevel - resetTolerance;
        manualMode = false;
        reachedTarget = -1;
    }

    public void toLow() {

        target = lowLevel - resetTolerance;
        manualMode = false;
    }

    public void toMid() {

        target = midLevel - resetTolerance;
        manualMode = false;
    }
    public void toHigh() {

        target = highLevel;
        manualMode = false;
    }

    public void setTarget(int target) {

        this.target = target;
        manualMode = false;
    }

    public int getReachedTarget () {

        return reachedTarget;
    }
    public void runUntilDone() {
        run(0);
        while (isRunning) run(0);
    }

    public void run() {run(0);}
    public void run(double power) {

        if (target == 0 && !manualMode) {

            microInitPos();
            flipInitPos();
        }

        if (Math.abs(power) >= holdK)
            manualMode = true;

        if (manualMode) {

            if (power == 0 || (motor1.getPosition() >= highLevel - tolerance && power > 0) || (motor1.getPosition() <= groundLevel - resetTolerance+ tolerance && power < 0)) {

                motor1.setPower(holdK);
                motor2.setPower(holdK);
                return;
            }

            if (power < 0) {

                motor1.setPower(power / 5);
                motor2.setPower(power / 5);
            }
            else {

                motor1.setPower(power * 0.65);
                motor2.setPower(power * 0.65);
            }
        }
        else {

            if (reachedTarget == target) {
                isRunning = false;
                return;
            }
            isRunning = true;

            if (Math.abs(motor1.getPosition() - target) >= tolerance || (motor1.getPosition() <= 15 && motor1.getPosition() >= 0)) {

                if (motor1.getPosition() < target) {

                    //motor1.setPower(0.6);
                    //motor2.setPower(0.6);

                    motor1.setPower(0.75);
                    motor2.setPower(0.75);
                }
                else {

                    //motor1.setPower(-0.4);
                    //motor2.setPower(-0.4);

                    motor1.setPower(-0.3);
                    motor2.setPower(-0.3);
                }
            }
            else {

                if (motor1.getPosition() <= 20) {

                    motor1.setPower(0);
                    motor2.setPower(0);
                }
                else {

                    motor1.setPower(holdK);
                    motor2.setPower(holdK);
                }
                reachedTarget = target;
            }
        }
    }

    public void testServo(double val) {

        microservo.setPosition(val);
    }

    public boolean holding() {
        return motor1.getPower() == 0.3;
    }
}
