package org.firstinspires.ftc.teamcode.components;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.utilities.Motor;

public class Collector {

    private Motor motor;

    public Collector(HardwareMap hardwareMap) {

        motor = new Motor(hardwareMap, "collector", true, true, false);
        motor.setPower(0);
    }

    public void runModeCollect() {

        motor.setPower(0.8);
    }

    public void runModeOf() {

        motor.setPower(0);
    }

    public void runModeThrow() {

        motor.setPower(-0.8);
    }
}
