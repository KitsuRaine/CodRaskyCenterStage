package org.firstinspires.ftc.teamcode.components;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.utilities.Motor;
import org.firstinspires.ftc.teamcode.utilities.Servo;

public class Collector {

    private Motor motor;
    private Servo servo1;
    private Servo servo2;
    public Collector(HardwareMap hardwareMap) {

        motor = new Motor(hardwareMap, "collector", true, true, false);
        motor.setPower(0);

        servo1 = new Servo(hardwareMap, "servostackleft");
        servo1.setPowerRange(500,2500);

        servo2 = new Servo(hardwareMap, "servostackright");
        servo2.setPowerRange(500,2500);

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


    // servo1 = stanga
    // servo2 = dreapta
    public void stackServoInit() {
        //Modifica servo1
        servo1.setPosition(0.52);
        servo2.setPosition(0.1);
    }

    public void stackServoUse() {
        //Modifica servo1
        servo1.setPosition(0);
        servo2.setPosition(0.576);
    }
}
