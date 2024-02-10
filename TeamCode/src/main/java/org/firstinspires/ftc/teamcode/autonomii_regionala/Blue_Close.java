package org.firstinspires.ftc.teamcode.autonomii_regionala;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.components.Collector;
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

@Autonomous(name = "Blue_Close", group = "main")
public class Blue_Close extends LinearOpMode {

    private RoadRunner roadRunner;
    private DrivingMotors drive;
    private DrivingMotorsCentric centric;
    private PositionSystem positionSystem;
    private Gyroscope gyroscope;
    private LiftSystem liftSystem;
    private Collector collector;
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
        collector = new Collector(hardwareMap);

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

        collector.openStackServo();
        /// TODO: ======================= Autonomie pentru dreapta tabla =======================
        if(tip_autonomie==0)
        {
            ///TODO: ===== - Cazul din stanga - =====

            // PIXEL MOV

            roadRunner.init(-32, 63, 0, true, 0, 1);
            while(roadRunner.isRunning)
                roadRunner.run();
            collector.closeStackServo();
            sleep(200);

            // MERGEM IN SPATE

            roadRunner.init(-4, 50, 0, true, 0, 1);
            while(roadRunner.isRunning)
                roadRunner.run();
            roadRunner.init(-4, 135, 0, true, 0, 1);
            while(roadRunner.isRunning)
                roadRunner.run();
            roadRunner.init(150, 140, 0, true, -Geometry.pi/2, 1);
            while(roadRunner.isRunning)
                roadRunner.run();

            /// LUAM PIXEL ALB

            collector.runModeCollect();
            roadRunner.init(167, 145, 0, true, -Geometry.pi/2, 1);
            while(roadRunner.isRunning)
                roadRunner.run();
            collector.openStackServo();
            sleep(350);
            collector.closeStackServo();
            collector.runModeCollect();
            roadRunner.init(-60, 145, 0, true, -Geometry.pi/2, 1);
            while(roadRunner.isRunning)
                roadRunner.run();
            collector.runModeOff();

            /// POZITIE LA TABLA
            liftSystem.microInitPos();
            liftSystem.setTarget(575);

            roadRunner.init(-80, 90, 0, true, -Geometry.pi/2, 1);
            while(roadRunner.isRunning){
                roadRunner.run();
                liftSystem.run(0);
            }

            liftSystem.flipActivePos(0);
            liftSystem.angleActivePos(0.1);


            roadRunner.init(-80, 90, 0, true, -Geometry.pi/2, 1);
            while(roadRunner.isRunning)
                roadRunner.run();

            liftSystem.setTarget(200);
            roadRunner.init(-90, 80, 0, true, -Geometry.pi/2, 1);
            while(roadRunner.isRunning) {
                roadRunner.run();
                liftSystem.run(0);
            }
            roadRunner.init(-103, 70, 0, true, -Geometry.pi/2, 1);
            while(roadRunner.isRunning) {
                roadRunner.run();
                liftSystem.run(0);
            }

        } else if(tip_autonomie==1)
        {
            ///TODO: ===== - Cazul din stanga - =====

            // PIXEL MOV

            roadRunner.init(-12, 74, 0, true, 0, 1);
            while(roadRunner.isRunning)
                roadRunner.run();
            collector.closeStackServo();
            sleep(200);

            // MERGEM IN SPATE

            roadRunner.init(-40, 30, 0, true, 0, 1);
            while(roadRunner.isRunning)
                roadRunner.run();
            roadRunner.init(-60, 145, 0, true, -Geometry.pi/2, 1);
            while(roadRunner.isRunning)
                roadRunner.run();
            roadRunner.init(150, 145, 0, true, -Geometry.pi/2, 1);
            while(roadRunner.isRunning)
                roadRunner.run();

            /// LUAM PIXEL ALB

            collector.runModeCollect();
            roadRunner.init(167, 140, 0, true, -Geometry.pi/2, 1);
            while(roadRunner.isRunning)
                roadRunner.run();
            collector.openStackServo();
            sleep(350);
            collector.closeStackServo();
            collector.runModeCollect();
            roadRunner.init(-60, 140, 0, true, -Geometry.pi/2, 1);
            while(roadRunner.isRunning)
                roadRunner.run();
            collector.runModeOff();

            /// POZITIE LA TABLA
            liftSystem.microInitPos();
            liftSystem.setTarget(575);

            roadRunner.init(-80, 90, 0, true, -Geometry.pi/2, 1);
            while(roadRunner.isRunning){
                roadRunner.run();
                liftSystem.run(0);
            }

            liftSystem.flipInitPos();
            liftSystem.angleActivePos(0.1);


            roadRunner.init(-80, 90, 0, true, -Geometry.pi/2, 1);
            while(roadRunner.isRunning)
                roadRunner.run();

            liftSystem.setTarget(200);
            roadRunner.init(-90, 80, 0, true, -Geometry.pi/2, 1);
            while(roadRunner.isRunning) {
                roadRunner.run();
                liftSystem.run(0);
            }
            roadRunner.init(-103, 90, 0, true, -Geometry.pi/2, 1);
            while(roadRunner.isRunning) {
                roadRunner.run();
                liftSystem.run(0);
            }

            sleep(1500);
            liftSystem.microSecodPos();
            sleep(1500);

            liftSystem.flipInitPos();
            liftSystem.microInitPos();
            liftSystem.angleInitPos();

            roadRunner.init(-80, 100, 0, true, -Geometry.pi/2, 1);
            while(roadRunner.isRunning)
                roadRunner.run();
            liftSystem.toGround();
            while(liftSystem.getReachedTarget() != 0)
                liftSystem.run(0);
        } else if(tip_autonomie==2) {
            ///TODO: ===== - Cazul din stanga - =====

            // PIXEL MOV

            roadRunner.init(-12, 50, 0, true, 0, 1);
            while (roadRunner.isRunning)
                roadRunner.run();
            roadRunner.init(-12, 70, 0, true, -Geometry.pi/2, 1);
            while (roadRunner.isRunning)
                roadRunner.run();
            roadRunner.init(-2, 80, 0, true, -Geometry.pi/2, 1);
            while (roadRunner.isRunning)
                roadRunner.run();
            collector.closeStackServo();
            sleep(200);

            // MERGEM IN SPATE

            roadRunner.init(-40, 30, 0, true, 0, 1);
            while (roadRunner.isRunning)
                roadRunner.run();
            roadRunner.init(-60, 145, 0, true, -Geometry.pi / 2, 1);
            while (roadRunner.isRunning)
                roadRunner.run();
            roadRunner.init(150, 145, 0, true, -Geometry.pi / 2, 1);
            while (roadRunner.isRunning)
                roadRunner.run();

            /// LUAM PIXEL ALB

            collector.runModeCollect();
            roadRunner.init(167, 140, 0, true, -Geometry.pi / 2, 1);
            while (roadRunner.isRunning)
                roadRunner.run();
            collector.openStackServo();
            sleep(350);
            collector.closeStackServo();
            collector.runModeCollect();
            roadRunner.init(-60, 140, 0, true, -Geometry.pi / 2, 1);
            while (roadRunner.isRunning)
                roadRunner.run();
            collector.runModeOff();

            /// POZITIE LA TABLA
            liftSystem.microInitPos();
            liftSystem.setTarget(575);

            roadRunner.init(-80, 90, 0, true, -Geometry.pi / 2, 1);
            while (roadRunner.isRunning) {
                roadRunner.run();
                liftSystem.run(0);
            }

            liftSystem.flipActivePos(0);
            liftSystem.angleActivePos(0.1);


            roadRunner.init(-80, 90, 0, true, -Geometry.pi / 2, 1);
            while (roadRunner.isRunning)
                roadRunner.run();

            liftSystem.setTarget(200);
            roadRunner.init(-90, 80, 0, true, -Geometry.pi / 2, 1);
            while (roadRunner.isRunning) {
                roadRunner.run();
                liftSystem.run(0);
            }
            roadRunner.init(-103, 90, 0, true, -Geometry.pi / 2, 1);
            while (roadRunner.isRunning) {
                roadRunner.run();
                liftSystem.run(0);
            }

            sleep(1500);
            liftSystem.microSecodPos();
            sleep(1500);

            liftSystem.flipInitPos();
            liftSystem.microInitPos();
            liftSystem.angleInitPos();

            roadRunner.init(-80, 100, 0, true, -Geometry.pi / 2, 1);
            while (roadRunner.isRunning)
                roadRunner.run();
            liftSystem.toGround();
            while (liftSystem.getReachedTarget() != 0)
                liftSystem.run(0);
        }
    }
}