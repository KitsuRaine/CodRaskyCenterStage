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

@Autonomous(group = "main", preselectTeleOp = "Main Driving")
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

        //! ============================= NEXT case ===============================================
        TrajectorySequence seqCaz0 = drive.trajectorySequenceBuilder(redClose)
                //* Place the purple pixel
                .splineTo(new Vector2d(8,-32),Math.toRadians(180))
                .forward(4)
                .addDisplacementMarker(() -> {
                    collector.stackServoInit();
                })
                .back(3)
                //* Place the yellow pixel
                .lineToConstantHeading(new Vector2d(44,-30.5))
                .addDisplacementMarker(() -> {
                    liftSystem.setTarget(275);
                    liftSystem.runUntilDone();
                })
                .back(7)
                .waitSeconds(0.1)
                .addDisplacementMarker(() -> {
                    liftSystem.flipActivePos(5);
                    liftSystem.angleActivePos(6.5);
                })
                .forward(2)
                .waitSeconds(0.1)
                .addDisplacementMarker(()->{
                    liftSystem.microSecondPos();
                })
                //* Reset the lift and cage
                .waitSeconds(0.1)
                .back(2)
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
                /*
                //* Start the run towards the stacks
                .lineToSplineHeading(new Pose2d(20, -13, Math.toRadians(180)))
                .forward(50)
                //* Get the collector ready
                .addDisplacementMarker(()->{
                    collector.runModeCollect();
                })
                .forward(32)
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
                .forward(0.1)
                .waitSeconds(0.2)
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
                .forward(0.1)
                .waitSeconds(0.2)
                .addDisplacementMarker(()->{
                    collector.stackServoUse();
                    collector.runModeCollect();
                })
                .forward(0.1)
                .addDisplacementMarker(()->{
                    collector.stackServoInit();
                })
                .back(3)
                .waitSeconds(0.5)
                .addDisplacementMarker(()->{
                    collector.runModeThrow();
                })
                .back(30)
                //* Start walking back cuz might have more than 2 at once
                .addDisplacementMarker(()->{
                    collector.runModeThrow();
                })
                .back(60)
                .splineToConstantHeading(new Vector2d(43.5,-38), Math.toRadians(0))
                .addDisplacementMarker(()->{
                    collector.runModeOff();
                })
                //* Get ready to place the white ones on the board
                .addDisplacementMarker(() -> {
                    liftSystem.setTarget(275);
                    liftSystem.runUntilDone();
                })
                .back(1)
                .waitSeconds(0.3)
                .addDisplacementMarker(() -> {
                    liftSystem.flipActivePos(5);
                    liftSystem.angleActivePos(6.5);
                })
                .back(2)
                .waitSeconds(0.2)
                .addDisplacementMarker(()->{
                    liftSystem.microSecondPos();
                })
                .back(2)
                .waitSeconds(0.2)
                .addDisplacementMarker(()->{
                    liftSystem.flipInitPos();
                    liftSystem.angleInitPos();
                })
                .forward(2)
                .waitSeconds(0.25)
                .addDisplacementMarker(()->{
                    liftSystem.toGround();
                    liftSystem.runUntilDone();
                })
                 */
                .forward(10)
                .strafeLeft(24)
                .build();

        //! ============================= NEXT case ===============================================
        TrajectorySequence seqCaz1 = drive.trajectorySequenceBuilder(redClose)
                //* Place the purple pixel
                .splineToLinearHeading(new Pose2d(20,-25, Math.toRadians(180)), Math.toRadians(180))
                .addDisplacementMarker(()->{
                    collector.stackServoInit();
                })
                .back(5)
                //* Place the yellow pixel
                .lineToConstantHeading(new Vector2d(44,-37.5))
                .addDisplacementMarker(() -> {
                    liftSystem.setTarget(250);
                    liftSystem.runUntilDone();
                })
                .back(7)
                .waitSeconds(0.1)
                .addDisplacementMarker(() -> {
                    liftSystem.flipActivePos(5);
                    liftSystem.angleActivePos(6.5);
                })
                .forward(2)
                .waitSeconds(0.1)
                .addDisplacementMarker(()->{
                    liftSystem.microSecondPos();
                })
                //* Reset the lift and cage
                .waitSeconds(0.1)
                .back(2)
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
                /*
                //* Start the run towards the stacks
                .lineToSplineHeading(new Pose2d(20, -14.8, Math.toRadians(180)))
                .forward(50)
                //* Get the collector ready
                .addDisplacementMarker(()->{
                    collector.runModeCollect();
                })
                .forward(32.5)
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
                .forward(0.1)
                .waitSeconds(0.2)
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
                .forward(0.1)
                .waitSeconds(0.2)
                .addDisplacementMarker(()->{
                    collector.stackServoUse();
                    collector.runModeCollect();
                })
                .forward(0.1)
                .addDisplacementMarker(()->{
                    collector.stackServoInit();
                })
                .back(3)
                .waitSeconds(0.5)
                .addDisplacementMarker(()->{
                    collector.runModeThrow();
                })
                .back(30)
                //* Start walking back cuz might have more than 2 at once
                .addDisplacementMarker(()->{
                    collector.runModeThrow();
                })
                .back(60)
                .splineToConstantHeading(new Vector2d(43.5,-38), Math.toRadians(0))
                .addDisplacementMarker(()->{
                    collector.runModeOff();
                })
                //* Get ready to place the white ones on the board
                .addDisplacementMarker(() -> {
                    liftSystem.setTarget(275);
                    liftSystem.runUntilDone();
                })
                .back(1)
                .waitSeconds(0.3)
                .addDisplacementMarker(() -> {
                    liftSystem.flipActivePos(5);
                    liftSystem.angleActivePos(6.5);
                })
                .back(2)
                .waitSeconds(0.2)
                .addDisplacementMarker(()->{
                    liftSystem.microSecondPos();
                })
                .back(2)
                .waitSeconds(0.2)
                .addDisplacementMarker(()->{
                    liftSystem.flipInitPos();
                    liftSystem.angleInitPos();
                })
                .forward(2)
                .waitSeconds(0.25)
                .addDisplacementMarker(()->{
                    liftSystem.toGround();
                    liftSystem.runUntilDone();
                })
                 */
                .forward(10)
                .strafeLeft(24)
                .build();

        //! ============================= NEXT case ===============================================
        TrajectorySequence seqCaz2 = drive.trajectorySequenceBuilder(redClose)
                //* Place the purple pixel
                .splineToLinearHeading(new Pose2d(25,-28, Math.toRadians(180)), Math.toRadians(180))
                .addDisplacementMarker(()->{
                    collector.stackServoInit();
                })
                .back(5)
                //* Place the yellow pixel
                .lineToConstantHeading(new Vector2d(44,-44))
                .addDisplacementMarker(() -> {
                    liftSystem.setTarget(250);
                    liftSystem.runUntilDone();
                })
                .back(7)
                .waitSeconds(0.1)
                .addDisplacementMarker(() -> {
                    liftSystem.flipActivePos(5);
                    liftSystem.angleActivePos(6.5);
                })
                .forward(2)
                .waitSeconds(0.1)
                .addDisplacementMarker(()->{
                    liftSystem.microSecondPos();
                })
                //* Reset the lift and cage
                .waitSeconds(0.1)
                .back(2)
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
                /*
                //* Start the run towards the stacks
                .lineToSplineHeading(new Pose2d(20, -14, Math.toRadians(180)))
                .forward(50)
                //* Get the collector ready
                .addDisplacementMarker(()->{
                    collector.runModeCollect();
                })
                .forward(33)
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
                .forward(0.1)
                .waitSeconds(0.2)
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
                .forward(0.1)
                .waitSeconds(0.2)
                .addDisplacementMarker(()->{
                    collector.stackServoUse();
                    collector.runModeCollect();
                })
                .forward(0.1)
                .addDisplacementMarker(()->{
                    collector.stackServoInit();
                })
                .back(3)
                .waitSeconds(0.5)
                .addDisplacementMarker(()->{
                    collector.runModeThrow();
                })
                .back(30)
                //* Start walking back cuz might have more than 2 at once
                .addDisplacementMarker(()->{
                    collector.runModeThrow();
                })
                .back(60)
                .splineToConstantHeading(new Vector2d(43.5,-38), Math.toRadians(0))
                .addDisplacementMarker(()->{
                    collector.runModeOff();
                })
                //* Get ready to place the white ones on the board
                .addDisplacementMarker(() -> {
                    liftSystem.setTarget(275);
                    liftSystem.runUntilDone();
                })
                .back(1)
                .waitSeconds(0.3)
                .addDisplacementMarker(() -> {
                    liftSystem.flipActivePos(5);
                    liftSystem.angleActivePos(6.5);
                })
                .back(2)
                .waitSeconds(0.2)
                .addDisplacementMarker(()->{
                    liftSystem.microSecondPos();
                })
                .back(2)
                .waitSeconds(0.2)
                .addDisplacementMarker(()->{
                    liftSystem.flipInitPos();
                    liftSystem.angleInitPos();
                })
                .forward(2)
                .waitSeconds(0.25)
                .addDisplacementMarker(()->{
                    liftSystem.toGround();
                    liftSystem.runUntilDone();
                })
                 */
                .forward(10)
                .strafeLeft(24)
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
        telemetry.addData("tip autonomie aleasa", caz);
        telemetry.update();

        sleep(10000);

        if (caz == 0) {
            drive.followTrajectorySequence(seqCaz0);
        } else if (caz == 1) {
            drive.followTrajectorySequence(seqCaz1);
        } else {
            drive.followTrajectorySequence(seqCaz2);
        }
    }
}
