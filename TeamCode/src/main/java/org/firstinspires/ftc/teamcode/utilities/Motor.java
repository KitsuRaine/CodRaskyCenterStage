package org.firstinspires.ftc.teamcode.utilities;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Motor {
    private DcMotorEx motor;
    private int tolerance, target;
    private double delta, left, right;
    private boolean hold;
    private double lastPos;
    private int targeting;
    private double MaxPower, MinPower;

    public Motor(HardwareMap hardwareMap, String name, boolean isReversed, boolean brake, boolean useEncoder) {

        motor = hardwareMap.get(DcMotorEx.class, name);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        if (isReversed)
            motor.setDirection(DcMotorSimple.Direction.REVERSE);
        else
            motor.setDirection(DcMotorSimple.Direction.FORWARD);

        if (useEncoder)
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        else
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        if (brake)
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        else
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        target = 0;
        tolerance = 10;
        hold = false;
        MaxPower = 1;
        MinPower = -1;
    }

    public void setTolerance(int tolerance) {
        this.tolerance = tolerance;
    }
    public void setTarget(int target) {
        this.target = target;
    }
    public void setPower(double power) {
        motor.setPower(power);
    }
    public int getPosition() {
        return motor.getCurrentPosition();
    }
    public double getPower() {return motor.getPower();}
    public void setMaxPower(double p) {this.MaxPower = p;}
    public void setMinPower(double p) {this.MinPower = p;}

    // go to target position
    public void updatePosition() {

        if (!(hold && target == targeting) && Math.abs(motor.getCurrentPosition() - target) > tolerance) {
            if (motor.getCurrentPosition() > target)
                motor.setPower(MinPower);
            else
                motor.setPower(MaxPower);
            hold = false;
        }
        else {

            hold = true;
            targeting = target;
            motor.setPower(0.3);
        }
    }

    public boolean holding() {return hold;}
}
