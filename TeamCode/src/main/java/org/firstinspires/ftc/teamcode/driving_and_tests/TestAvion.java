package org.firstinspires.ftc.teamcode.driving_and_tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.utilities.Servo;

@TeleOp(name = "Test Avion", group = "Main")
public class TestAvion extends LinearOpMode {

    Gamepad drivingGamepad;
    Servo avion;

    public void runOpMode() throws InterruptedException {

        avion.setPowerRange(500, 2500);
        avion.setPosition(0);

        while (!opModeIsActive() && !isStopRequested()) {
        }

        if (isStopRequested())
            return;

        while (opModeIsActive() && !isStopRequested()) {

            if(drivingGamepad.cross)
                avion.setPosition(0.4);

        }
    }
}
