package org.firstinspires.ftc.teamcode.autonomii_regionala;

import static org.firstinspires.ftc.teamcode.RoadRunner.util.StartPos.blueClose;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.RoadRunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.components.Collector;
import org.firstinspires.ftc.teamcode.components.LiftSystem;

@Autonomous(name = "BlueCloseMiddle", group = "main")
public class BlueCloseMiddle extends LinearOpMode {
    //DetectBlueObj pipeline = new DetectBlueObj();
    Collector collector;
    LiftSystem liftSystem;
    //OpenCvWebcam webcam;
    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        collector = new Collector(hardwareMap);
        liftSystem = new LiftSystem(hardwareMap);

        /*int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        OpenCvWebcam webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        webcam.setPipeline(pipeline);

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
            {}
        });*/



        drive.setPoseEstimate(blueClose);

        TrajectorySequence seqCaz0 = drive.trajectorySequenceBuilder(blueClose)
                .splineTo(new Vector2d(35,32),Math.toRadians(180))
                .forward(6)
                .addDisplacementMarker(() -> {
                    collector.stackServoInit();
                })
                .back(3)
                .lineToConstantHeading(new Vector2d(45,35))
                .addDisplacementMarker(() -> {
                    liftSystem.setTarget(300);
                    liftSystem.run();
                })
                .back(6)
                .waitSeconds(0.2)
                .addDisplacementMarker(() -> {
                    liftSystem.flipActivePos(5);
                    liftSystem.angleActivePos(6.5);
                })
                .forward(2)
                .waitSeconds(0.2)
                .addDisplacementMarker(()->{
                    liftSystem.microSecondPos();
                })
                .waitSeconds(0.2)
                .back(2)
                .addDisplacementMarker(()->{
                    liftSystem.flipInitPos();
                    liftSystem.angleInitPos();
                })
                .forward(2)
                .waitSeconds(0.2)
                .addDisplacementMarker(()->{
                    liftSystem.toGround();
                    liftSystem.run();
                })
                .back(2)
                .waitSeconds(0.2)
                .lineToSplineHeading(new Pose2d(20, 13, Math.toRadians(180)))
                .forward(50)
                .addDisplacementMarker(()->{
                    collector.runModeCollect();
                })
                .forward(32)
                //* run and put it back
                .addDisplacementMarker(()->{
                    collector.stackServoUse();
                })
                .forward(3)
                .addDisplacementMarker(()->{
                    collector.stackServoInit();
                })
                .back(3)
                .addDisplacementMarker(()->{
                    collector.stackServoUse();
                })
                .forward(3)
                .addDisplacementMarker(()->{
                    collector.stackServoInit();
                })
                .back(3)
                .waitSeconds(0.5)
                .addDisplacementMarker(()->{
                    collector.runModeThrow();
                })
                .back(90)
                .splineToConstantHeading(new Vector2d(47,35), Math.toRadians(0))
                .addDisplacementMarker(()->{
                    collector.runModeOff();
                })
                .addDisplacementMarker(() -> {
                    liftSystem.setTarget(100);
                    liftSystem.run();
                })
                .forward(1)
                .waitSeconds(0.2)
                .addDisplacementMarker(() -> {
                    liftSystem.flipActivePos(5);
                    liftSystem.angleActivePos(6.5);
                })
                .back(1)
                .waitSeconds(0.2)
                .addDisplacementMarker(()->{
                    liftSystem.microSecondPos();
                })
                .waitSeconds(0.3)
                .back(2)
                .addDisplacementMarker(()->{
                    liftSystem.flipInitPos();
                    liftSystem.angleInitPos();
                })
                .forward(2)
                .waitSeconds(0.2)
                .addDisplacementMarker(()->{
                    liftSystem.toGround();
                    liftSystem.run();
                })
                .forward(2)
                .build();

        TrajectorySequence seqCaz1 = drive.trajectorySequenceBuilder(blueClose)
                .forward(28)
                .addDisplacementMarker(() -> {
                    collector.stackServoInit();
                })
                .lineToLinearHeading(new Pose2d(49, 35, Math.toRadians(180)))
                .addDisplacementMarker(() -> {
                    liftSystem.setTarget(300);
                    liftSystem.run();
                })
                .back(2)
                .waitSeconds(0.2)
                .addDisplacementMarker(() -> {
                    liftSystem.flipActivePos(5);
                    liftSystem.angleActivePos(6.5);
                })
                .forward(2)
                .waitSeconds(0.2)
                .addDisplacementMarker(()->{
                    liftSystem.microSecondPos();
                })
                .waitSeconds(0.2)
                .back(2)
                .addDisplacementMarker(()->{
                    liftSystem.flipInitPos();
                    liftSystem.angleInitPos();
                })
                .forward(2)
                .waitSeconds(0.2)
                .addDisplacementMarker(()->{
                    liftSystem.toGround();
                    liftSystem.run();
                })
                .back(2)
                .waitSeconds(0.2)
                .lineToSplineHeading(new Pose2d(20, 13, Math.toRadians(180)))
                .forward(50)
                .addDisplacementMarker(()->{
                    collector.runModeCollect();
                })
                .forward(32)
                //* run and put it back
                .addDisplacementMarker(()->{
                    collector.stackServoUse();
                })
                .forward(3)
                .addDisplacementMarker(()->{
                    collector.stackServoInit();
                })
                .back(3)
                .addDisplacementMarker(()->{
                    collector.stackServoUse();
                })
                .forward(3)
                .addDisplacementMarker(()->{
                    collector.stackServoInit();
                })
                .back(3)
                .waitSeconds(0.5)
                .addDisplacementMarker(()->{
                    collector.runModeThrow();
                })
                .back(90)
                .splineToConstantHeading(new Vector2d(47,35), Math.toRadians(0))
                .addDisplacementMarker(()->{
                    collector.runModeOff();
                })
                .addDisplacementMarker(() -> {
                    liftSystem.setTarget(100);
                    liftSystem.run();
                })
                .forward(1)
                .waitSeconds(0.2)
                .addDisplacementMarker(() -> {
                    liftSystem.flipActivePos(5);
                    liftSystem.angleActivePos(6.5);
                })
                .back(1)
                .waitSeconds(0.2)
                .addDisplacementMarker(()->{
                    liftSystem.microSecondPos();
                })
                .waitSeconds(0.3)
                .back(2)
                .addDisplacementMarker(()->{
                    liftSystem.flipInitPos();
                    liftSystem.angleInitPos();
                })
                .forward(2)
                .waitSeconds(0.2)
                .addDisplacementMarker(()->{
                    liftSystem.toGround();
                    liftSystem.run();
                })
                .forward(2)
                .build();

        TrajectorySequence seqCaz2 = drive.trajectorySequenceBuilder(blueClose)
                //* ocoleste si pune pixel
                .setTangent(-1.5)
                .splineToSplineHeading(new Pose2d(8,31, Math.toRadians(180)), Math.toRadians(180))
                .forward(5)
                .addDisplacementMarker(() -> {
                    collector.stackServoInit();
                })
                .back(5)
                .lineToConstantHeading(new Vector2d(49, 35))
                .addDisplacementMarker(() -> {
                    liftSystem.setTarget(300);
                    liftSystem.run();
                })
                .back(2)
                .waitSeconds(0.2)
                .addDisplacementMarker(() -> {
                    liftSystem.flipActivePos(5);
                    liftSystem.angleActivePos(6.5);
                })
                .forward(2)
                .waitSeconds(0.2)
                .addDisplacementMarker(()->{
                    liftSystem.microSecondPos();
                })
                .waitSeconds(0.2)
                .back(2)
                .addDisplacementMarker(()->{
                    liftSystem.flipInitPos();
                    liftSystem.angleInitPos();
                })
                .forward(2)
                .waitSeconds(0.2)
                .addDisplacementMarker(()->{
                    liftSystem.toGround();
                    liftSystem.run();
                })
                .back(2)
                .waitSeconds(0.2)
                .lineToSplineHeading(new Pose2d(20, 13, Math.toRadians(180)))
                .forward(50)
                .addDisplacementMarker(()->{
                    collector.runModeCollect();
                })
                .forward(32)
                //* run and put it back
                .addDisplacementMarker(()->{
                    collector.stackServoUse();
                })
                .forward(3)
                .addDisplacementMarker(()->{
                    collector.stackServoInit();
                })
                .back(3)
                .addDisplacementMarker(()->{
                    collector.stackServoUse();
                })
                .forward(3)
                .addDisplacementMarker(()->{
                    collector.stackServoInit();
                })
                .back(3)
                .waitSeconds(0.5)
                .addDisplacementMarker(()->{
                    collector.runModeThrow();
                })
                .back(90)
                .splineToConstantHeading(new Vector2d(47,35), Math.toRadians(0))
                .addDisplacementMarker(()->{
                    collector.runModeOff();
                })
                .addDisplacementMarker(() -> {
                    liftSystem.setTarget(100);
                    liftSystem.run();
                })
                .forward(1)
                .waitSeconds(0.2)
                .addDisplacementMarker(() -> {
                    liftSystem.flipActivePos(5);
                    liftSystem.angleActivePos(6.5);
                })
                .back(1)
                .waitSeconds(0.2)
                .addDisplacementMarker(()->{
                    liftSystem.microSecondPos();
                })
                .waitSeconds(0.3)
                .back(2)
                .addDisplacementMarker(()->{
                    liftSystem.flipInitPos();
                    liftSystem.angleInitPos();
                })
                .forward(2)
                .waitSeconds(0.2)
                .addDisplacementMarker(()->{
                    liftSystem.toGround();
                    liftSystem.run();
                })
                .forward(2)
                .build();

        telemetry.addLine("Ready!");
        telemetry.update();
        waitForStart();
        if (isStopRequested()) return;

        int caz = 0;
        telemetry.addData("tip autonomie", caz);

        collector.stackServoUse();

        if (caz == 0) {
            drive.followTrajectorySequence(seqCaz0);
        } else if (caz == 1) {
            drive.followTrajectorySequence(seqCaz1);
        } else {
            drive.followTrajectorySequence(seqCaz2);
        }
    }
}
