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
import org.firstinspires.ftc.teamcode.detection.DetectBlueObj;
import org.firstinspires.ftc.teamcode.utilities.camera;
import org.openftc.easyopencv.OpenCvWebcam;

@Autonomous(group = "main", preselectTeleOp = "Main Driving")
public class BlueCloseMiddle extends LinearOpMode {
    DetectBlueObj pipeline = new DetectBlueObj();
    Collector collector;
    LiftSystem liftSystem;
    OpenCvWebcam webcam;
    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        webcam = camera.createWithPipeline(hardwareMap, pipeline);

        collector = new Collector(hardwareMap);
        liftSystem = new LiftSystem(hardwareMap);

        drive.setPoseEstimate(blueClose);

        //! ============================= NEXT case ===============================================
        TrajectorySequence seqCaz0 = drive.trajectorySequenceBuilder(blueClose)
                //* Place the purple pixel
                .splineToLinearHeading(new Pose2d(26,32,Math.toRadians(180)), Math.toRadians(180))
                .addDisplacementMarker(() -> {
                    collector.stackServoInit();
                })
                .back(3)
                .waitSeconds(7)
                //* Place the yellow pixel
                .lineToConstantHeading(new Vector2d(45,45))
                .addDisplacementMarker(() -> {
                    liftSystem.setTarget(275);
                    liftSystem.runUntilDone();
                })
                .back(7)
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
                //* Reset the lift and cage
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
                .forward(9)
                .strafeLeft(12)
                /*
                //* Start the run towards the stacks
                .lineToSplineHeading(new Pose2d(20, 13, Math.toRadians(180)))
                .forward(50)
                //* Get the collector ready
                .addDisplacementMarker(()->{
                    collector.runModeCollect();
                })
                .forward(28)
                .addDisplacementMarker(()->{
                    collector.stackServoUse();
                })
                .forward(3)
                .addDisplacementMarker(()->{
                    collector.stackServoInit();
                })
                .back(0.1)
                .addDisplacementMarker(()->{
                    collector.stackServoUse();
                })
                .forward(5)
                .addDisplacementMarker(()->{
                    collector.stackServoInit();
                })
                .back(3)
                .waitSeconds(0.5)
                .addDisplacementMarker(()->{
                    collector.runModeCollect();
                })
                .back(30)
                //* Start walking back cuz might have more than 2 at once
                .addDisplacementMarker(()->{
                    collector.runModeThrow();
                })
                .back(60)
                .splineToConstantHeading(new Vector2d(45,35), Math.toRadians(0))
                .strafeRight(7)
                .addDisplacementMarker(()->{
                    collector.runModeOff();
                })
                //* Get ready to place the white ones on the board
                .addDisplacementMarker(() -> {
                    liftSystem.setTarget(275);
                    liftSystem.runUntilDone();
                })
                .back(2)
                .waitSeconds(0.3)
                .addDisplacementMarker(() -> {
                    liftSystem.flipActivePos(5);
                    liftSystem.angleActivePos(6.5);
                })
                .back(3)
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
                .strafeRight(12)
                 */
                .build();


        //! ============================= NEXT case ===============================================
        TrajectorySequence seqCaz1 = drive.trajectorySequenceBuilder(blueClose)
                //* Place purple pixel
                .forward(28)
                .addDisplacementMarker(() -> {
                    collector.stackServoInit();
                })
                .waitSeconds(7)
                //* Go place yellow one
                .lineToLinearHeading(new Pose2d(49, 35, Math.toRadians(180)))
                .addDisplacementMarker(() -> {
                    liftSystem.setTarget(275);
                    liftSystem.runUntilDone();
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
                //* Reset the lift and cage
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
                .forward(9)
                .strafeLeft(18)
                /*
                //* Start the run towards the stacks
                .lineToSplineHeading(new Pose2d(20, 13, Math.toRadians(180)))
                .forward(50)
                //* Get the collector ready
                .addDisplacementMarker(()->{
                    collector.runModeCollect();
                })
                .forward(28)
                .addDisplacementMarker(()->{
                    collector.stackServoUse();
                })
                .forward(3)
                .addDisplacementMarker(()->{
                    collector.stackServoInit();
                })
                .back(0.1)
                .addDisplacementMarker(()->{
                    collector.stackServoUse();
                })
                .forward(5)
                .addDisplacementMarker(()->{
                    collector.stackServoInit();
                })
                .back(3)
                .waitSeconds(0.5)
                .addDisplacementMarker(()->{
                    collector.runModeCollect();
                })
                .back(30)
                //* Start walking back cuz might have more than 2 at once
                .addDisplacementMarker(()->{
                    collector.runModeThrow();
                })
                .back(60)
                .splineToConstantHeading(new Vector2d(45,35), Math.toRadians(0))
                .strafeRight(7)
                .addDisplacementMarker(()->{
                    collector.runModeOff();
                })
                //* Get ready to place the white ones on the board
                .addDisplacementMarker(() -> {
                    liftSystem.setTarget(275);
                    liftSystem.runUntilDone();
                })
                .back(2)
                .waitSeconds(0.3)
                .addDisplacementMarker(() -> {
                    liftSystem.flipActivePos(5);
                    liftSystem.angleActivePos(6.5);
                })
                .back(3)
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
                .strafeRight(12)
                 */
                .build();

        //! ============================= NEXT case ===============================================
        TrajectorySequence seqCaz2 = drive.trajectorySequenceBuilder(blueClose)
                //* Place purple pixel
                .setTangent(-1.5)
                .splineToSplineHeading(new Pose2d(8,31, Math.toRadians(180)), Math.toRadians(180))
                .forward(5)
                .addDisplacementMarker(() -> {
                    collector.stackServoInit();
                })
                //* Get ready to place the yellow pixel
                .back(5)
                .lineToConstantHeading(new Vector2d(49, 32))
                .addDisplacementMarker(() -> {
                    liftSystem.setTarget(275);
                    liftSystem.runUntilDone();
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
                //* Reset the lift and cage
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
                .forward(10)
                .strafeRight(24)
                /*
                //* Start the run towards the stacks
                .lineToSplineHeading(new Pose2d(20, 13, Math.toRadians(180)))
                .forward(50)
                //* Get the collector ready
                .addDisplacementMarker(()->{
                    collector.runModeCollect();
                })
                .forward(28)
                .addDisplacementMarker(()->{
                    collector.stackServoUse();
                })
                .forward(3)
                .addDisplacementMarker(()->{
                    collector.stackServoInit();
                })
                .back(0.1)
                .addDisplacementMarker(()->{
                    collector.stackServoUse();
                })
                .forward(5)
                .addDisplacementMarker(()->{
                    collector.stackServoInit();
                })
                .back(3)
                .waitSeconds(0.5)
                .addDisplacementMarker(()->{
                    collector.runModeCollect();
                })
                .back(30)
                //* Start walking back cuz might have more than 2 at once
                .addDisplacementMarker(()->{
                    collector.runModeThrow();
                })
                .back(60)
                .splineToConstantHeading(new Vector2d(45,35), Math.toRadians(0))
                .addDisplacementMarker(()->{
                    collector.runModeOff();
                })
                //* Get ready to place the white ones on the board
                .addDisplacementMarker(() -> {
                    liftSystem.setTarget(275);
                    liftSystem.runUntilDone();
                })
                .back(2)
                .waitSeconds(0.3)
                .addDisplacementMarker(() -> {
                    liftSystem.flipActivePos(5);
                    liftSystem.angleActivePos(6.5);
                })
                .back(3)
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

        int caz = pipeline.getTipAutonomie();
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
