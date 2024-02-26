package org.firstinspires.ftc.teamcode.components;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.utilities.Motor;
import org.firstinspires.ftc.teamcode.utilities.Servo;


/**
 * Robot hanging mechanism
 * @author Raine
 */
public class SistemAgatare {
    private Motor motorRidicare;
    private Servo servoAgatare;

    public SistemAgatare(HardwareMap hardwareMap) {
        motorRidicare = new Motor(hardwareMap, "motor_ridicare", false, true, true);
        servoAgatare = new Servo(hardwareMap, "servo_agatare");
        servoAgatare.setPowerRange(500, 2500);
    }
    public void openServo() {
        servoAgatare.setPosition(0);
    }
    public void closeServo() {
        servoAgatare.setPosition(0.4);
    }

    public void push() {
        motorRidicare.setPower(1);
    }
    public void pull() {
        motorRidicare.setPower(-1);
    }
    public void stop() {
        motorRidicare.setPower(0);
    }
}
