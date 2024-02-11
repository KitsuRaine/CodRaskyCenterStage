package org.firstinspires.ftc.teamcode.autonomii_vechi;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
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

@Autonomous(name = "testBlueFar", group = "main")
public class testBlueFar extends LinearOpMode {

    private RoadRunner roadRunner;
    private DrivingMotors drive;
    private DrivingMotorsCentric centric;
    private PositionSystem positionSystem;
    private Gyroscope gyroscope;
    private LiftSystem liftSystem;

    OpenCvWebcam webcam;
    DetectBlueObj pipe_line_blue = new DetectBlueObj();

    public void runOpMode() throws InterruptedException {

        //Webcam
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        webcam.setPipeline(pipe_line_blue);

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

        tip_autonomie = pipe_line_blue.gen_tip_autonomie();
        telemetry.addData("Autonomie: ", tip_autonomie);
        telemetry.update();

        if (isStopRequested())
            return;

        if(tip_autonomie==0)
        {
            roadRunner.init(10, 20, 0, true, 0, 1);
            while (roadRunner.isRunning)
                roadRunner.run();

            roadRunner.init(20, 30, 0, true, Geometry.pi/7, 1);
            while (roadRunner.isRunning)
                roadRunner.run();

            roadRunner.init(-2, 60, 0, true, Geometry.pi/7, 1);
            while (roadRunner.isRunning)
                roadRunner.run();

            roadRunner.init(20, 50, 0, true, Geometry.pi/7, 1);
            while (roadRunner.isRunning)
                roadRunner.run();
        }

        else if(tip_autonomie==1)
        {
            roadRunner.init(10, 20, 0, true, 0, 1);
            while (roadRunner.isRunning)
                roadRunner.run();

            roadRunner.init(15, 63, 0, true, 0, 1);
            while (roadRunner.isRunning)
                roadRunner.run();

            roadRunner.init(45, 50, 0, true, 0, 1);
            while (roadRunner.isRunning)
                roadRunner.run();
        }

        else if(tip_autonomie==2)
        {

            roadRunner.init(10, 10, 0, true, Geometry.pi/2, 1);
            while (roadRunner.isRunning)
                roadRunner.run();

            roadRunner.init(10, 30, 0, true, 0, 1);
            while (roadRunner.isRunning)
                roadRunner.run();

            roadRunner.init(10, 100, 0, true, 0, 1);
            while (roadRunner.isRunning)
                roadRunner.run();
        }

        roadRunner.init(45, 130, 0, true, 0, 1);
        while (roadRunner.isRunning)
            roadRunner.run();

        roadRunner.init(-145, 130, 0, true, -Geometry.pi/2, 1);
        while (roadRunner.isRunning)
            roadRunner.run();

        roadRunner.setPrecision(0.6);
        if(tip_autonomie==0)
        {
            roadRunner.init(-211, 62, 0, true, -Geometry.pi / 2, 1);
        }
        else if(tip_autonomie==1)
        {
            roadRunner.init(-211, 72, 0, true, -Geometry.pi / 2, 1);
        }
        else if(tip_autonomie==2)
        {
            roadRunner.init(-211, 82, 0, true, -Geometry.pi / 2, 1);
        }
        liftSystem.setTarget(475);

        while (roadRunner.isRunning) {

            liftSystem.run(0);
            roadRunner.run();
        }

        liftSystem.microSecondPos();
        sleep(1500);
        liftSystem.toGround();

        roadRunner.init(-209 , 72, 0, true, -Geometry.pi / 2, 1);

        while (roadRunner.isRunning) {

            roadRunner.run();
            liftSystem.run(0);
        }

        while (liftSystem.getReachedTarget() != 0)
            liftSystem.run(0);
    }
}
