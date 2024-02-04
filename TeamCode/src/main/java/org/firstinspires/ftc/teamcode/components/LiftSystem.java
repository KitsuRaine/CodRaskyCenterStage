package org.firstinspires.ftc.teamcode.components;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.utilities.Motor;
import org.firstinspires.ftc.teamcode.utilities.Servo;

public class LiftSystem {

    private Motor motor1, motor2;
    private int target, tolerance, reachedTarget;
    private final int groundLevel = 0;
    private final int lowLevel = 450;
    private final int midLevel = 650;
    private final int highLevel = 750;
    private final double holdK = 0.15;
    private int resetTolerance = 0;
    boolean manualMode;
    private Servo flip;
    private Servo angleServo;
    private Servo microservo;

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
        servoInitPos();
        angleInitPos();
    }

    public void microInitPos() {

        microservo.setPosition(0.2);
    }

    public void microFirstPos() {

        microservo.setPosition(0.45);
    }

    public void microSecodPos() {

        microservo.setPosition(0.6);
    }

    public void angleInitPos() {
        angleServo.setPosition(0.360);
    }

    public void angleActivePos() {
        angleServo.setPosition(0.18);
    }

    public void servoInitPos() {

        flip.setPosition(0.0385);
    }

    public void servoActivePos() {
        //valoare era 6
        flip.setPosition(5);
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
    public void goLowAsFUccc()
    {
        resetTolerance-=10;
    }

    public void UnderGround() {

        target = groundLevel - resetTolerance;
        if(resetTolerance < 600)
            resetTolerance+= 10;
        manualMode = false;
        reachedTarget = -1;
        
    }

    public void toGround() {

        target = groundLevel;
        manualMode = false;
        reachedTarget = -1;
    }

    public void toLow() {

        target = lowLevel;
        manualMode = false;
        reachedTarget = -1;
    }

    public void toMid() {

        target = midLevel;
        manualMode = false;
        reachedTarget = -1;
    }

    public void setTarget(int target) {

        this.target = target;
        manualMode = false;
        reachedTarget = -1;
    }

    public int getReachedTarget () {

        return reachedTarget;
    }

    public void InitTheFlipper () {

        servoInitPos();
    }

    public void FlipTheFlipper () {

        servoActivePos();
    }

    public void run(double power) {

        if (target == 0 && !manualMode) {

            microInitPos();
            servoInitPos();
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

            if (reachedTarget == target)
                return;

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
