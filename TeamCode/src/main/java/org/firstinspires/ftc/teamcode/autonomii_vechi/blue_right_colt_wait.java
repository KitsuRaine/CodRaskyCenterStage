package org.firstinspires.ftc.teamcode.autonomii_vechi;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.components.LiftSystem;
import org.firstinspires.ftc.teamcode.detection.DetectRedObj;
import org.firstinspires.ftc.teamcode.utilities.DrivingMotors;
import org.firstinspires.ftc.teamcode.utilities.DrivingMotorsCentric;
import org.firstinspires.ftc.teamcode.utilities.Geometry;
import org.firstinspires.ftc.teamcode.utilities.Gyroscope;
import org.firstinspires.ftc.teamcode.utilities.PositionSystem;
import org.firstinspires.ftc.teamcode.utilities.RoadRunner;
import org.openftc.easyopencv.OpenCvWebcam;

@Autonomous(name = "zblue_right_colt_wait", group = "main")
public class blue_right_colt_wait extends LinearOpMode {

    private RoadRunner roadRunner;
    private DrivingMotors drive;
    private DrivingMotorsCentric centric;
    private PositionSystem positionSystem;
    private Gyroscope gyroscope;
    private LiftSystem liftSystem;

    OpenCvWebcam webcam;
    DetectRedObj pipe_line;

    public void runOpMode() throws InterruptedException {

        gyroscope = new Gyroscope(hardwareMap);
        drive = new DrivingMotors(hardwareMap);
        centric = new DrivingMotorsCentric(gyroscope, drive);
        positionSystem = new PositionSystem(hardwareMap, gyroscope, drive.getLeftEncoder(), drive.getFrontEncoder());
        roadRunner = new RoadRunner(drive, centric, positionSystem, telemetry);
        liftSystem = new LiftSystem(hardwareMap);

        telemetry.addData("Ready!", "");
        telemetry.update();

        while (!opModeIsActive() && !isStopRequested()) {
        }

        if (isStopRequested())
            return;

        sleep(15000);

        roadRunner.init(0, 62, 0, true, 0, 1);

        while (roadRunner.isRunning)
            roadRunner.run();

        roadRunner.init(-195, 62, 0, true, 0, 1);
        liftSystem.setTarget(575);

        while (roadRunner.getDistanceFromDestination() >= 20)
            roadRunner.run();

        liftSystem.setTarget(600);
        roadRunner.setPrecision(0.6);
        roadRunner.init(-224.75, 75, 0, true, -Geometry.pi / 2, 1);

        while (roadRunner.isRunning) {

            roadRunner.run();
            liftSystem.run(0);
        }

        roadRunner.setPrecision(1);

        liftSystem.microSecondPos();
        sleep(1500);
        liftSystem.toGround();

        roadRunner.init(-224.75, 10, 0, true, Geometry.pi / 2, 1);

        while (roadRunner.isRunning) {

            roadRunner.run();
            liftSystem.run(0);
        }

        while (liftSystem.getReachedTarget() != 0)
            liftSystem.run(0);
    }
}