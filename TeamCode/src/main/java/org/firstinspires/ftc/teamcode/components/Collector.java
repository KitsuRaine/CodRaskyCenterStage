package org.firstinspires.ftc.teamcode.components;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.utilities.Motor;
import org.firstinspires.ftc.teamcode.utilities.Servo;

public class Collector {

    private Motor motor;
    private Servo stackServoStanga;
    private Servo stackServoDreapta;
    public Collector(HardwareMap hardwareMap) {

        motor = new Motor(hardwareMap, "collector", true, true, false);
        motor.setPower(0);

        stackServoStanga = new Servo(hardwareMap, "servostackleft");
        stackServoStanga.setPowerRange(500,2500);

        stackServoDreapta = new Servo(hardwareMap, "servostackright");
        stackServoDreapta.setPowerRange(500,2500);

        stackServoInit();
    }

    public void runModeCollect() {

        motor.setPower(0.8);
    }

    public void runModeOff() {

        motor.setPower(0);
    }

    public void runModeThrow() {

        motor.setPower(-0.8);
    }

    //* claws init position
    public void stackServoInit() {
        stackServoStanga.setPosition(0.540);
        stackServoDreapta.setPosition(0.1);
    }

    //* claws grab pixel position
    public void stackServoUse() {
        stackServoStanga.setPosition(0.065);
        stackServoDreapta.setPosition(0.55);
    }
}
