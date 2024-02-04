package org.firstinspires.ftc.teamcode.autonomii_regionala;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.utilities.Motor;

@Autonomous(name = "BlueCC", group = "main")
public class BlueCC extends LinearOpMode {

    private Motor leftRear, rightRear, leftFront, rightFront;

    public void runOpMode() throws InterruptedException {

        leftRear = new Motor(hardwareMap, "stangaSpate", false, true, false);
        leftFront = new Motor(hardwareMap, "stangaFataE", true, true, false);
        rightFront = new Motor(hardwareMap, "dreaptaFata", false, true, false);
        rightRear = new Motor(hardwareMap, "dreaptaSpateE", true, true, false);

        telemetry.addData("Ready!", "");
        telemetry.update();

        while (!opModeIsActive() && !isStopRequested()) {
            leftRear.setPower(0);
            leftFront.setPower(0);
            rightFront.setPower(0);
            rightRear.setPower(0);
        }

        while (opModeIsActive() && !isStopRequested()) {
            leftRear.setPower(0.3);
            leftFront.setPower(0.3);
            rightFront.setPower(0.3);
            rightRear.setPower(0.3);
        }

        leftRear.setPower(0);
        leftFront.setPower(0);
        rightFront.setPower(0);
        rightRear.setPower(0);

    }
}