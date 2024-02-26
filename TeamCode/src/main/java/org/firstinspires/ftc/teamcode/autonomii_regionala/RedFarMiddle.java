package org.firstinspires.ftc.teamcode.autonomii_regionala;

import static org.firstinspires.ftc.teamcode.RoadRunner.util.StartPos.redFar;

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

@Autonomous(group = "main", preselectTeleOp = "Main Driving")
public class RedFarMiddle extends LinearOpMode {
    DetectRedObj pipeline = new DetectRedObj();
    OpenCvWebcam webcam;
    LiftSystem liftSystem;
    Collector collector;
    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        webcam = camera.createWithPipeline(hardwareMap, pipeline);
        liftSystem = new LiftSystem(hardwareMap);
        collector = new Collector(hardwareMap);

        drive.setPoseEstimate(redFar);

        //! =================================== CASE 0 =============================================
        TrajectorySequence seqCaz0 = drive.trajectorySequenceBuilder(redFar)
                //* Place the purple pixel
                .splineToLinearHeading(new Pose2d(-43,-18, Math.toRadians(270)), Math.toRadians(270))
                .forward(10)
                .addDisplacementMarker(()->{
                    collector.stackServoInit();
                })
                .back(5)
                //* Collect one white
                .lineToLinearHeading(new Pose2d(-52,-13.5, Math.toRadians(180)))
                .addDisplacementMarker(()->{
                    collector.runModeCollect();
                })
                .forward(7)
                .addDisplacementMarker(()->{
                    collector.stackServoUse();
                    collector.runModeCollect();
                })
                .forward(0.1)
                .waitSeconds(0.2)
                .addDisplacementMarker(()->{
                    collector.stackServoInit();
                    collector.runModeCollect();
                })
                .back(0.1)
                .waitSeconds(0.2)
                .addDisplacementMarker(()->{
                    collector.runModeThrow();
                })
                //* Go towards the Backdrop and raise lift
                .lineToConstantHeading(new Vector2d(40,-12))
                .addDisplacementMarker(() -> {
                    collector.runModeOff();
                    liftSystem.setTarget(250);
                    liftSystem.runUntilDone();
                })
                .setTangent(Math.toRadians(-45))
                .splineToConstantHeading(new Vector2d(53.5, -34), Math.toRadians(0))
                .waitSeconds(0.1)
                .addDisplacementMarker(() -> {
                    liftSystem.flipActivePos(5);
                    liftSystem.angleActivePos(6.5);
                })
                .back(0.1)
                .waitSeconds(0.2)
                .addDisplacementMarker(()->{
                    liftSystem.microSecondPos();
                })
                //* Reset the lift and cage
                .back(2)
                .waitSeconds(0.1)
                .addDisplacementMarker(()->{
                    liftSystem.flipInitPos();
                    liftSystem.angleInitPos();
                })
                .forward(2)
                .waitSeconds(0.1)
                .addDisplacementMarker(()->{
                    liftSystem.toGround();
                    liftSystem.run();
                })
                .back(2)
                .waitSeconds(0.1)
                //* Park
                /*
                .forward(10)
                .strafeRight(12)
                */
                .build();

        //! =================================== CASE 1 =============================================
        TrajectorySequence seqCaz1 = drive.trajectorySequenceBuilder(redFar)
                //* Place the purple pixel
                .splineToLinearHeading(new Pose2d(-38,-12, Math.toRadians(270)), Math.toRadians(270))
                .forward(7)
                .addDisplacementMarker(()->{
                    collector.stackServoInit();
                })
                .back(5)
                //* Collect one white
                .lineToLinearHeading(new Pose2d(-52,-13.5, Math.toRadians(180)))
                .addDisplacementMarker(()->{
                    collector.runModeCollect();
                })
                .forward(7)
                .addDisplacementMarker(()->{
                    collector.stackServoUse();
                    collector.runModeCollect();
                })
                .forward(0.1)
                .waitSeconds(0.2)
                .addDisplacementMarker(()->{
                    collector.stackServoInit();
                    collector.runModeCollect();
                })
                .back(0.1)
                .waitSeconds(0.2)
                .addDisplacementMarker(()->{
                    collector.runModeThrow();
                })
                //* Go towards the Backdrop and raise lift
                .lineToConstantHeading(new Vector2d(40,-12))
                .addDisplacementMarker(() -> {
                    collector.runModeOff();
                    liftSystem.setTarget(250);
                    liftSystem.runUntilDone();
                })
                .setTangent(Math.toRadians(-45))
                .splineToConstantHeading(new Vector2d(53.5, -40), Math.toRadians(0))
                .waitSeconds(0.1)
                .addDisplacementMarker(() -> {
                    liftSystem.flipActivePos(5);
                    liftSystem.angleActivePos(6.5);
                })
                .back(0.1)
                .waitSeconds(0.2)
                .addDisplacementMarker(()->{
                    liftSystem.microSecondPos();
                })
                //* Reset the lift and cage
                .back(2)
                .waitSeconds(0.1)
                .addDisplacementMarker(()->{
                    liftSystem.flipInitPos();
                    liftSystem.angleInitPos();
                })
                .forward(2)
                .waitSeconds(0.1)
                .addDisplacementMarker(()->{
                    liftSystem.toGround();
                    liftSystem.run();
                })
                .back(2)
                .waitSeconds(0.1)
                //* Park
                /*
                .forward(10)
                .strafeRight(12)
                */
                .build();

        //! =================================== CASE 2 =============================================
        TrajectorySequence seqCaz2 = drive.trajectorySequenceBuilder(redFar)
                //* Place the purple pixel
                .splineToLinearHeading(new Pose2d(-30,-35, Math.toRadians(45)), Math.toRadians(0))
                .forward(2)
                .addDisplacementMarker(()->{
                    collector.stackServoInit();
                })
                .back(7)
                //* Collect one white
                .lineToLinearHeading(new Pose2d(-52,-13.5, Math.toRadians(180)))
                .addDisplacementMarker(()->{
                    collector.runModeCollect();
                })
                .forward(7)
                .addDisplacementMarker(()->{
                    collector.stackServoUse();
                    collector.runModeCollect();
                })
                .forward(0.1)
                .waitSeconds(0.2)
                .addDisplacementMarker(()->{
                    collector.stackServoInit();
                    collector.runModeCollect();
                })
                .back(0.1)
                .waitSeconds(0.2)
                .addDisplacementMarker(()->{
                    collector.runModeThrow();
                })
                //* Go towards the Backdrop and raise lift
                .lineToConstantHeading(new Vector2d(40,-12))
                .addDisplacementMarker(() -> {
                    collector.runModeOff();
                    liftSystem.setTarget(250);
                    liftSystem.runUntilDone();
                })
                .setTangent(Math.toRadians(-45))
                .splineToConstantHeading(new Vector2d(53.5, -46), Math.toRadians(0))
                .waitSeconds(0.1)
                .addDisplacementMarker(() -> {
                    liftSystem.flipActivePos(5);
                    liftSystem.angleActivePos(6.5);
                })
                .back(0.1)
                .waitSeconds(0.2)
                .addDisplacementMarker(()->{
                    liftSystem.microSecondPos();
                })
                //* Reset the lift and cage
                .back(2)
                .waitSeconds(0.1)
                .addDisplacementMarker(()->{
                    liftSystem.flipInitPos();
                    liftSystem.angleInitPos();
                })
                .forward(2)
                .waitSeconds(0.1)
                .addDisplacementMarker(()->{
                    liftSystem.toGround();
                    liftSystem.run();
                })
                .back(2)
                .waitSeconds(0.1)
                //* Park
                /*
                .forward(10)
                .strafeRight(12)
                */
                .build();

        telemetry.addLine("Ready!");
        telemetry.update();
        while (!opModeIsActive() && !isStopRequested()) {
            if (!pipeline.initialized) telemetry.addLine("!!! CAMERA NOT INITIALIZED !!!");
            telemetry.addLine("Ready!");
            telemetry.addData("tip autonomie", pipeline.getTipAutonomie());
            telemetry.update();
        }
        waitForStart();
        if (isStopRequested()) return;

        collector.stackServoUse();
        liftSystem.microInitPos();
        liftSystem.flipInitPos();
        liftSystem.angleInitPos();

        int caz = pipeline.getTipAutonomie();
        telemetry.addData("tip autonomie", caz);
        telemetry.update();

        if (caz == 0) {
            drive.followTrajectorySequence(seqCaz0);
        } else if (caz == 1) {
            drive.followTrajectorySequence(seqCaz1);
        } else {
            drive.followTrajectorySequence(seqCaz2);
        }
    }
}
