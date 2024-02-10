package org.firstinspires.ftc.teamcode.autonomii_noi;

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

@Autonomous(name = "zRed_Left_Middle", group = "main")
public class Red_Left_Middle extends LinearOpMode {

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

        sleep(6000);

        if(tip_autonomie==0)
        {
            roadRunner.init(-27, 55, 0, true, 0, 1);

            while (roadRunner.isRunning)
                roadRunner.run();

            roadRunner.init(-2, 20, 0, true, 0, 1);

            while (roadRunner.isRunning)
                roadRunner.run();

            roadRunner.init(-2, 130, 0, true, 0, 1);

            while (roadRunner.isRunning)
                roadRunner.run();
        }

        else if(tip_autonomie==1)
        {
            roadRunner.init(5, 65, 0, true, 0, 1);

            while (roadRunner.isRunning)
                roadRunner.run();

            roadRunner.init(-32, 46, 0, true, 0, 1);

            while (roadRunner.isRunning)
                roadRunner.run();


            roadRunner.init(-32, 150, 0, true, 0, 1);

            while (roadRunner.isRunning)
                roadRunner.run();
        }

        else if(tip_autonomie==2)
        {

            roadRunner.init(-2, 37, 0, true, 0, 1);

            while (roadRunner.isRunning)
                roadRunner.run();

            roadRunner.init(-30, 80, 0, true, -Geometry.pi/2, 1);

            while (roadRunner.isRunning)
                roadRunner.run();

            roadRunner.init(-7, 80, 0, true, -Geometry.pi/2, 1);

            while (roadRunner.isRunning)
                roadRunner.run();

            roadRunner.init(-45, 120, 0, true, 0, 1);

            while (roadRunner.isRunning)
                roadRunner.run();
        }


        roadRunner.init(125, 135, 0, true, Geometry.pi/2, 1);

        while (roadRunner.isRunning)
            roadRunner.run();

        liftSystem.microInitPos();
        roadRunner.init(200, 70, 0, true, Geometry.pi/2, 1);
        liftSystem.setTarget(525);

        while (roadRunner.isRunning) {
            liftSystem.run(0);
            roadRunner.run();
        }

        if(tip_autonomie==0)
        {
            roadRunner.init(226, 77, 0, true, Geometry.pi / 2, 1);

            while (roadRunner.isRunning)
                roadRunner.run();
        }
        else if(tip_autonomie==1)
        {
            roadRunner.init(226, 65, 0, true, Geometry.pi / 2, 1);

            while (roadRunner.isRunning)
                roadRunner.run();

        }
        else if(tip_autonomie==2)
        {
            roadRunner.init(226, 50, 0, true, Geometry.pi / 2, 1);

            while (roadRunner.isRunning)
                roadRunner.run();

        }

        sleep(3000);
        liftSystem.microSecodPos();
        sleep(3000);
        liftSystem.toGround();

        roadRunner.init(195, 126, 0, true, Geometry.pi / 2, 1);

        while (roadRunner.isRunning) {

            roadRunner.run();
            liftSystem.run(0);
        }

        while (roadRunner.isRunning)
            roadRunner.run();

        roadRunner.init(240, 126, 0, true, Geometry.pi / 2, 1);

        while (roadRunner.isRunning)
            roadRunner.run();

        while (liftSystem.getReachedTarget() != 0)
            liftSystem.run(0);
    }
}
