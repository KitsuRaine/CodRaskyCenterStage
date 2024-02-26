package org.firstinspires.ftc.teamcode.driving_and_tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.components.Airplane;
import org.firstinspires.ftc.teamcode.utilities.Servo;

@TeleOp(name = "Test Avion", group = "Main")
public class TestAvion extends LinearOpMode {
    Airplane avion;

    public void runOpMode() throws InterruptedException {
        avion = new Airplane(hardwareMap);

        waitForStart();
        if (isStopRequested())
            return;

        while (opModeIsActive() && !isStopRequested()) {
            if(gamepad1.cross)
                avion.releasePlane();
        }
    }
}
