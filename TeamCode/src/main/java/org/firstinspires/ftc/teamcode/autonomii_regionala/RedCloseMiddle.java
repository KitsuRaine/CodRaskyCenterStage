package org.firstinspires.ftc.teamcode.autonomii_regionala;

import static org.firstinspires.ftc.teamcode.RoadRunner.util.StartPos.redClose;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.RoadRunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.components.Collector;
import org.firstinspires.ftc.teamcode.components.LiftSystem;
import org.firstinspires.ftc.teamcode.detection.DetectRedObj;
import org.firstinspires.ftc.teamcode.utilities.camera;
import org.openftc.easyopencv.OpenCvWebcam;

@Autonomous(name = "RedCloseMiddle", group = "main")
public class RedCloseMiddle extends LinearOpMode {
    DetectRedObj pipeline = new DetectRedObj();
    LiftSystem liftSystem;
    Collector collector;
    OpenCvWebcam webcam;
    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        webcam = camera.createWithPipeline(hardwareMap, pipeline);

        liftSystem = new LiftSystem(hardwareMap);
        collector = new Collector(hardwareMap);

        drive.setPoseEstimate(redClose);

        TrajectorySequence seqCaz0 = drive.trajectorySequenceBuilder(redClose)

                .splineTo(new Vector2d(8,-32),Math.toRadians(180))
                .forward(4)
                .addDisplacementMarker(() -> {
                    collector.stackServoInit();
                })
                .back(3)
                .lineToConstantHeading(new Vector2d(43,-32))
                .addDisplacementMarker(() -> {
                    liftSystem.setTarget(300);
                    liftSystem.runUntilDone();
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
                .lineToSplineHeading(new Pose2d(20, -13, Math.toRadians(180)))
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
                .splineToConstantHeading(new Vector2d(45,-35), Math.toRadians(0))
                .addDisplacementMarker(()->{
                    collector.runModeOff();
                })
                .addDisplacementMarker(() -> {
                    liftSystem.setTarget(300);
                    liftSystem.runUntilDone();
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

        TrajectorySequence seqCaz1 = drive.trajectorySequenceBuilder(redClose)
                //* pune pixel
                .splineToLinearHeading(new Pose2d(20,-27, Math.toRadians(180)), Math.toRadians(180))
                .addDisplacementMarker(()->{
                    collector.stackServoInit();
                })
                .back(5)
                .lineToConstantHeading(new Vector2d(43,-35))
                .addDisplacementMarker(() -> {
                    liftSystem.setTarget(300);
                    liftSystem.runUntilDone();
                })
                .back(6)
                .waitSeconds(0.2)
                .addDisplacementMarker(() -> {
                    liftSystem.flipActivePos(5);
                    liftSystem.angleActivePos(6.5);
                })
                .forward(2)
                .waitSeconds(0.2)
                .forward(2)
                .addDisplacementMarker(()->{
                    liftSystem.microSecondPos();
                })
                .forward(2)
                .waitSeconds(0.4)
                .back(2)
                .addDisplacementMarker(()->{
                    liftSystem.flipInitPos();
                    liftSystem.angleInitPos();
                })
                .forward(2)
                .waitSeconds(0.2)
                .addDisplacementMarker(()->{
                    liftSystem.toGround();
                    liftSystem.runUntilDone();
                })
                .back(2)
                .waitSeconds(0.2)
                .lineToSplineHeading(new Pose2d(20, -13, Math.toRadians(180)))
                .forward(50)
                .addDisplacementMarker(()->{
                    collector.runModeCollect();
                })
                .forward(32)
                //* run and put it back
                .addDisplacementMarker(()->{
                    collector.stackServoUse();
                    collector.runModeCollect();
                })
                .forward(3)
                .addDisplacementMarker(()->{
                    collector.stackServoInit();
                    collector.runModeCollect();
                })
                .back(3)
                .addDisplacementMarker(()->{
                    collector.stackServoUse();
                    collector.runModeCollect();
                })
                .forward(3)
                .addDisplacementMarker(()->{
                    collector.stackServoInit();
                    collector.runModeCollect();
                })
                .back(3)
                .waitSeconds(0.5)
                .addDisplacementMarker(()->{
                    collector.runModeThrow();
                })
                .back(90)
                .splineToConstantHeading(new Vector2d(45,-35), Math.toRadians(0))
                .addDisplacementMarker(()->{
                    collector.runModeOff();
                })
                .addDisplacementMarker(() -> {
                    liftSystem.setTarget(300);
                    liftSystem.runUntilDone();
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
                .addDisplacementMarker(()->{
                    liftSystem.flipInitPos();
                    liftSystem.angleInitPos();
                })
                .waitSeconds(0.2)
                .addDisplacementMarker(()->{
                    liftSystem.toGround();
                    liftSystem.run();
                })
                .forward(2)
                .build();

        TrajectorySequence seqCaz2 = drive.trajectorySequenceBuilder(redClose)
                //* pune pixel
                .splineToLinearHeading(new Pose2d(24,-38, Math.toRadians(180)),Math.toRadians(180))
                .waitSeconds(0.1)
                .addDisplacementMarker(()->{
                    collector.stackServoInit();
                })
                .setTangent(Math.toRadians(-90))
                .lineToConstantHeading(new Vector2d(43,-40))
                .addDisplacementMarker(() -> {
                    liftSystem.setTarget(300);
                    liftSystem.runUntilDone();
                })
                .back(6)
                .waitSeconds(0.2)
                .addDisplacementMarker(() -> {
                    liftSystem.flipActivePos(5);
                    liftSystem.angleActivePos(6.5);
                })
                .forward(2)
                .waitSeconds(0.2)
                .back(2)
                .addDisplacementMarker(()->{
                    liftSystem.microSecondPos();
                })
                .forward(2)
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
                .lineToSplineHeading(new Pose2d(20, -14, Math.toRadians(180)))
                .forward(50)
                .addDisplacementMarker(()->{
                    collector.runModeCollect();
                })
                .forward(32)
                //* run and put it back
                .addDisplacementMarker(()->{
                    collector.stackServoUse();
                    collector.runModeCollect();
                })
                .back(3)
                .addDisplacementMarker(()->{
                    collector.stackServoInit();
                    collector.runModeCollect();
                })
                .forward(9)
                .addDisplacementMarker(()->{
                    collector.stackServoUse();
                    collector.runModeCollect();
                })
                .back(6)
                .addDisplacementMarker(()->{
                    collector.stackServoInit();
                    collector.runModeCollect();
                })
                .forward(3)
                .waitSeconds(0.5)
                .addDisplacementMarker(()->{
                    collector.runModeThrow();
                })
                .back(80)
                .splineToConstantHeading(new Vector2d(45,-35), Math.toRadians(0))
                .addDisplacementMarker(()->{
                    collector.runModeOff();
                })
                .addDisplacementMarker(() -> {
                    liftSystem.setTarget(300);
                    liftSystem.runUntilDone();
                })
                .forward(1)
                .waitSeconds(0.2)
                .addDisplacementMarker(() -> {
                    liftSystem.flipActivePos(5);
                    liftSystem.angleActivePos(6.5);
                })
                .back(1)
                .waitSeconds(0.2)
                .back(9)
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

        //int caz = pipeline.gen_tip_autonomie();
        int caz=2;
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
