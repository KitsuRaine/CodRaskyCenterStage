package org.firstinspires.ftc.teamcode.autonomii_vechi;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.components.LiftSystem;
import org.firstinspires.ftc.teamcode.detection.DetectBlueObj;
import org.firstinspires.ftc.teamcode.utilities.DrivingMotors;
import org.firstinspires.ftc.teamcode.utilities.DrivingMotorsCentric;
import org.firstinspires.ftc.teamcode.utilities.Geometry;
import org.firstinspires.ftc.teamcode.utilities.Gyroscope;
import org.firstinspires.ftc.teamcode.utilities.PositionSystem;
import org.firstinspires.ftc.teamcode.utilities.RoadRunner;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

@Disabled
@Autonomous(name = "zblue_right", group = "main")
public class blue_right extends LinearOpMode {

    private RoadRunner roadRunner;
    private DrivingMotors drive;
    private DrivingMotorsCentric centric;
    private PositionSystem positionSystem;
    private Gyroscope gyroscope;
    private LiftSystem liftSystem;

    OpenCvWebcam webcam;
    DetectBlueObj pipe_line = new DetectBlueObj();

    public void runOpMode() throws InterruptedException {

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        webcam.setPipeline(pipe_line);

        webcam.setMillisecondsPermissionTimeout(2500); // Timeout for obtaining permission is configurable. Set before opening.
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                webcam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode)
            {
                /*
                 * This will be called if the camera could not be opened
                 */
            }
        });

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

        int tip_autonomie;

        tip_autonomie = pipe_line.getTipAutonomie();
        telemetry.addData("Autonomie: ", tip_autonomie);
        telemetry.update();

        roadRunner.init(0, 62, 0, true, 0, 1);

        while (roadRunner.isRunning)
            roadRunner.run();

        tip_autonomie=2;

        /// 1 - STANGA
        /// 2 - MIJLOC
        /// 3 - DREAPTA
        /// 0 - STANGA
        if (tip_autonomie == 1 || tip_autonomie == 0)
        {
            roadRunner.init(-10, 0, 0, true, -Geometry.pi/2, 1);

            while (roadRunner.isRunning)
                roadRunner.run();

            roadRunner.init(10, 0, 0, true, Geometry.pi/2, 1);

            while (roadRunner.isRunning)
                roadRunner.run();
        }
        else if (tip_autonomie == 2)
            ;/// Caz fara modificare pozitie
        else if (tip_autonomie == 3)
        {
            roadRunner.init(10, 0, 0, true, Geometry.pi/2, 1);

            while (roadRunner.isRunning)
                roadRunner.run();

            roadRunner.init(-10, 0, 0, true, -Geometry.pi/2, 1);

            while (roadRunner.isRunning)
                roadRunner.run();
        }

        roadRunner.init(-195, 62, 0, true, 0, 1);
        liftSystem.setTarget(575);

        while (roadRunner.getDistanceFromDestination() >= 20)
            roadRunner.run();

        liftSystem.setTarget(600);
        roadRunner.setPrecision(0.6);
        roadRunner.init(-225, 70, 0, true, -Geometry.pi / 2, 1);

        while (roadRunner.isRunning) {

            roadRunner.run();
            liftSystem.run(0);
        }

        roadRunner.setPrecision(1);

        liftSystem.microSecondPos();
        sleep(1500);
        liftSystem.toGround();

        while (liftSystem.getReachedTarget() != 0)
            liftSystem.run(0);

//        roadRunner.init(98.3, 10, 0, true, Geometry.pi / 2, 1);
//
//        while (roadRunner.isRunning) {
//
//            roadRunner.run();
//            liftSystem.run(0);
//        }


    }
}