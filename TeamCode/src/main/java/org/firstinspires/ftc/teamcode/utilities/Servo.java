package org.firstinspires.ftc.teamcode.utilities;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.ServoImplEx;

public class Servo {

    ServoImplEx servo;

    public Servo(HardwareMap hardwareMap, String name) {

        servo = hardwareMap.get(ServoImplEx.class, name);
    }

    public void setPowerRange(double minim, double maxim) {

        servo.setPwmRange(new PwmControl.PwmRange(minim, maxim));
    }

    public void setPosition(double pos) {

        servo.setPosition(pos);
    }
}
