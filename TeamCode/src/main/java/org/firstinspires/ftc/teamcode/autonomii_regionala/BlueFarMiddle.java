package org.firstinspires.ftc.teamcode.autonomii_regionala;

import static org.firstinspires.ftc.teamcode.RoadRunner.util.StartPos.blueFar;
import static org.firstinspires.ftc.teamcode.RoadRunner.util.StartPos.redFar;

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
public class BlueFarMiddle extends LinearOpMode {
    DetectBlueObj pipeline = new DetectBlueObj();
    OpenCvWebcam webcam;
    LiftSystem liftSystem;
    Collector collector;
    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        webcam = camera.createWithPipeline(hardwareMap, pipeline);
        liftSystem = new LiftSystem(hardwareMap);
        collector = new Collector(hardwareMap);

        collector.stackServoUse();
        liftSystem.microInitPos();
        liftSystem.flipInitPos();
        liftSystem.angleInitPos();

        drive.setPoseEstimate(blueFar);

        //! =================================== CASE 0 =============================================
        TrajectorySequence seqCaz0 = drive.trajectorySequenceBuilder(blueFar)
                //* Place the purple pixel
                .splineToSplineHeading(new Pose2d(-31,32, Math.toRadians(0)), Math.toRadians(0))
                .addDisplacementMarker(()->{
                    collector.stackServoInit();
                })
                .back(5)
                //* Collect one white
                .lineToLinearHeading(new Pose2d(-52,12, Math.toRadians(180)))
                .addDisplacementMarker(()->{
                    collector.runModeCollect();
                })
                .forward(13)
                .addDisplacementMarker(()->{
                    collector.stackServoUse();
                    collector.runModeCollect();
                })
                .forward(0.1)
                .waitSeconds(0.7)
                .addDisplacementMarker(()->{
                    collector.stackServoInit();
                    collector.runModeCollect();
                })
                .back(0.1)
                .waitSeconds(0.9)
                .addDisplacementMarker(()->{
                    collector.runModeThrow();
                })
                //* Go towards the Backdrop and raise lift
                .lineToConstantHeading(new Vector2d(40,12))
                .addDisplacementMarker(() -> {
                    collector.runModeOff();
                    liftSystem.setTarget(250);
                    liftSystem.runUntilDone();
                })
                .setTangent(Math.toRadians(-45))
                .splineToConstantHeading(new Vector2d(53, 43), Math.toRadians(0))
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
                .back(0.1)
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
                .forward(12)
                .waitSeconds(0.1)
                .strafeRight(12)
                 */
                .build();

        //! =================================== CASE 1 =============================================
        TrajectorySequence seqCaz1 = drive.trajectorySequenceBuilder(blueFar)
                //* Place the purple pixel
                .splineToLinearHeading(new Pose2d(-38, 17, Math.toRadians(75)), Math.toRadians(-90))
                //.forward(4)
                .addDisplacementMarker(()->{
                    collector.stackServoInit();
                })
                .back(5)
                //* Collect one white
                .lineToLinearHeading(new Pose2d(-52,12, Math.toRadians(180)))
                .addDisplacementMarker(()->{
                    collector.runModeCollect();
                })
                .forward(13)
                .addDisplacementMarker(()->{
                    collector.stackServoUse();
                    collector.runModeCollect();
                })
                .forward(0.1)
                .waitSeconds(0.7)
                .addDisplacementMarker(()->{
                    collector.stackServoInit();
                    collector.runModeCollect();
                })
                .back(0.1)
                .waitSeconds(0.9)
                .addDisplacementMarker(()->{
                    collector.runModeThrow();
                })
                //* Go towards the Backdrop and raise lift
                .lineToConstantHeading(new Vector2d(40,12))
                .addDisplacementMarker(() -> {
                    collector.runModeOff();
                    liftSystem.setTarget(250);
                    liftSystem.runUntilDone();
                })
                .setTangent(Math.toRadians(-45))
                .splineToConstantHeading(new Vector2d(53, 38.5), Math.toRadians(0))
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
                .back(0.1)
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
                .forward(12)
                .waitSeconds(0.1)
                .strafeRight(12)
                 */
                .build();

        //! =================================== CASE 2 =============================================
        TrajectorySequence seqCaz2 = drive.trajectorySequenceBuilder(blueFar)
                //* Place the purple pixel
                .setTangent(Math.toRadians(180))
                .splineToLinearHeading(new Pose2d(-49, 28, Math.toRadians(90)),Math.toRadians(180))
                .back(6)
                .addDisplacementMarker(()->{
                    // let go of purple pixel
                    collector.stackServoInit();
                })
                .back(10)
                //* Collect one white
                .lineToLinearHeading(new Pose2d(-52,12, Math.toRadians(180)))
                .addDisplacementMarker(()->{
                    collector.runModeCollect();
                })
                .forward(13)
                .addDisplacementMarker(()->{
                    collector.stackServoUse();
                    collector.runModeCollect();
                })
                .forward(0.1)
                .waitSeconds(0.7)
                .addDisplacementMarker(()->{
                    collector.stackServoInit();
                    collector.runModeCollect();
                })
                .back(0.1)
                .waitSeconds(0.9)
                .addDisplacementMarker(()->{
                    collector.runModeThrow();
                })
                //* Go towards the Backdrop and raise lift
                .lineToConstantHeading(new Vector2d(40,12))
                .addDisplacementMarker(() -> {
                    collector.runModeOff();
                    liftSystem.setTarget(250);
                    liftSystem.runUntilDone();
                })
                .setTangent(Math.toRadians(-45))
                .splineToConstantHeading(new Vector2d(53, 31), Math.toRadians(0))
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
                .back(0.1)
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
                .forward(12)
                .waitSeconds(0.1)
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

        if (caz == 0) {
            drive.followTrajectorySequence(seqCaz0);
        } else if (caz == 1) {
            drive.followTrajectorySequence(seqCaz1);
        } else {
            drive.followTrajectorySequence(seqCaz2);
        }
    }
}
