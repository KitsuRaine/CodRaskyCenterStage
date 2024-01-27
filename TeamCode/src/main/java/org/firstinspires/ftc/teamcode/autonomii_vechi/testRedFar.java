package org.firstinspires.ftc.teamcode.autonomii_vechi;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.components.LiftSystem;
import org.firstinspires.ftc.teamcode.detection.DetectRedObj;
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

@Autonomous(name = "testRedFar", group = "main")
public class testRedFar extends LinearOpMode {

    private RoadRunner roadRunner;
    private DrivingMotors drive;
    private DrivingMotorsCentric centric;
    private PositionSystem positionSystem;
    private Gyroscope gyroscope;
    private LiftSystem liftSystem;
    OpenCvWebcam webcam;
    DetectRedObj pipe_line_red = new DetectRedObj();

    public void runOpMode() throws InterruptedException {

        //Webcam
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        webcam.setPipeline(pipe_line_red);

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

        int tip_autonomie;
        waitForStart();

        tip_autonomie = pipe_line_red.gen_tip_autonomie();
        telemetry.addData("Autonomie: ", tip_autonomie);
        telemetry.update();

        if (isStopRequested())
            return;

        if(tip_autonomie==0)
        {
            roadRunner.init(-14, 58, 0, true, Geometry.pi/18, 1);

            while (roadRunner.isRunning)
                roadRunner.run();

            roadRunner.init(10, 32, 0, true, 0, 1);

            while (roadRunner.isRunning)
                roadRunner.run();

            roadRunner.init(10, 130, 0, true, 0, 1);

            while (roadRunner.isRunning)
                roadRunner.run();
        }

        else if(tip_autonomie==1)
        {
            roadRunner.init(0, 65, 0, true, 0, 1);

            while (roadRunner.isRunning)
                roadRunner.run();

            roadRunner.init(0, 28, 0, true, 0, 1);

            while (roadRunner.isRunning)
                roadRunner.run();

            roadRunner.init(-42, 28, 0, true, 0, 1);

            while (roadRunner.isRunning)
                roadRunner.run();

            roadRunner.init(-40, 145, 0, true, 0, 1);

            while (roadRunner.isRunning)
                roadRunner.run();
        }

        else if(tip_autonomie==2)
        {
            roadRunner.init(0, 18, 0, true, -Geometry.pi / 11, 1);

            while (roadRunner.isRunning)
                roadRunner.run();

            roadRunner.init(-12, 28, 0, true, -Geometry.pi / 8, 1);

            while (roadRunner.isRunning)
                roadRunner.run();

            roadRunner.init(20, 58, 0, true, -Geometry.pi / 5, 1);

            while (roadRunner.isRunning)
                roadRunner.run();

            roadRunner.init(-40, 40, 0, true, 0, 1);

            while (roadRunner.isRunning)
                roadRunner.run();

            roadRunner.init(-40, 145, 0, true, 0, 1);

            while (roadRunner.isRunning)
                roadRunner.run();
        }

        roadRunner.init(180, 145, 0, true, 0, 1);

        while (roadRunner.isRunning)
            roadRunner.run();

        if(tip_autonomie==0)
        {
            roadRunner.init(230, 87, 0, true, Geometry.pi / 2, 1);
        }
        else if(tip_autonomie==1)
        {
            roadRunner.init(230, 60, 0, true, Geometry.pi / 2, 1);
        }
        else if(tip_autonomie==2)
        {
            roadRunner.init(230, 44, 0, true, Geometry.pi / 2, 1);
        }

        liftSystem.setTarget(475);

        while (roadRunner.isRunning) {

            liftSystem.run(0);
            roadRunner.run();
        }

        sleep(800);
        liftSystem.microSecodPos();
        sleep(1500);
        liftSystem.toGround();

        roadRunner.init(228, 70, 0, true, Geometry.pi / 2, 1);

        while (roadRunner.isRunning) {

            roadRunner.run();
            liftSystem.run(0);
        }
        roadRunner.init(228, 130, 0, true, Geometry.pi / 2, 1);

        while (roadRunner.isRunning)
            roadRunner.run();

        roadRunner.init(244, 127, 0, true, Geometry.pi / 2, 1);

        while (roadRunner.isRunning)
            roadRunner.run();

        while (liftSystem.getReachedTarget() != 0)
            liftSystem.run(0);
    }
}
