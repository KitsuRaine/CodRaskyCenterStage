package org.firstinspires.ftc.teamcode.autonomii_vechi;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
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

@Disabled
@Autonomous(name = "zred_right_colt", group = "main")
public class red_right_colt extends LinearOpMode {

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

        roadRunner.init(0, 62, 0, true, 0, 1);

        while (roadRunner.isRunning)
            roadRunner.run();

        roadRunner.setPrecision(0.6);
        roadRunner.init(98, 62, 0, true, Geometry.pi / 2, 1);
        liftSystem.setTarget(575);

        while (roadRunner.isRunning) {

            liftSystem.run(0);
            roadRunner.run();
        }

        roadRunner.setPrecision(1);

        liftSystem.microSecondPos();
        sleep(1500);
        liftSystem.toGround();

        roadRunner.init(98, 10, 0, true, Geometry.pi / 2, 1);

        while (roadRunner.isRunning) {

            roadRunner.run();
            liftSystem.run(0);
        }

        while (liftSystem.getReachedTarget() != 0)
            liftSystem.run(0);
    }
}
