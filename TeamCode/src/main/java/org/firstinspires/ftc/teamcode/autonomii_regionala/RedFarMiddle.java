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

@Autonomous(name = "RedFarMiddle", group = "main")
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

        collector.stackServoUse();
        liftSystem.microInitPos();
        liftSystem.flipInitPos();
        liftSystem.angleInitPos();

        drive.setPoseEstimate(redFar);

        TrajectorySequence seqCaz0 = drive.trajectorySequenceBuilder(redFar)
                //* pune pixel si ocoleste
                .splineToLinearHeading(new Pose2d(-44,-18, Math.toRadians(-90)), Math.toRadians(90))
                .addDisplacementMarker(()->{
                    collector.stackServoInit();
                })
                .setTangent(Math.toRadians(45))
                .splineToLinearHeading(new Pose2d(-30, -11, Math.toRadians(180)), Math.toRadians(0))
                .forward(.5)
                //* go collect
                .addDisplacementMarker(()->{
                    // collector
                    collector.runModeCollect();
                })
                .forward(22)
                .addDisplacementMarker(()->{
                    collector.stackServoUse();
                })
                .forward(1)
                .waitSeconds(.5)
                //* run fuga fuga
                .back(50)
                .addDisplacementMarker(()->{
                    collector.runModeOff();
                    // lift
                    liftSystem.setTarget(300);
                    liftSystem.run();
                    collector.stackServoInit();
                    collector.runModeThrow();
                })
                .back(35)
                .addDisplacementMarker(()->{
                    // flip the flipper and angle the angler
                    liftSystem.flipActivePos(4);
                    liftSystem.angleActivePos(5);
                })
                .splineToConstantHeading(new Vector2d(54, -36), 0)
                .back(0.5)
                .addDisplacementMarker(()->{
                    liftSystem.microSecondPos();
                })
                .forward(0.5)
                .waitSeconds(.5)
                .addDisplacementMarker(()->{
                    liftSystem.microInitPos();
                    liftSystem.flipInitPos();
                    liftSystem.angleInitPos();
                })
                .forward(.5)
                .waitSeconds(.5)
                //* leave and get new pixels
                .setTangent(Math.toRadians(180))
                .addDisplacementMarker(()->{
                    liftSystem.toGround();
                    liftSystem.run();
                })
                .splineToLinearHeading(new Pose2d(30, -13, Math.toRadians(180)), Math.toRadians(180))
                .addDisplacementMarker(()->{
                    collector.runModeCollect();
                })
                .forward(80)
//                .addDisplacementMarker(()->{
//                    collector.runModeCollect();
//                })
                .forward(3)
                .addDisplacementMarker(()->{
                    collector.stackServoUse();
                })
                .waitSeconds(.5)
                //* go back and place
                .back(50)
                .addDisplacementMarker(()->{
                    collector.runModeOff();
                    // lift
                    liftSystem.setTarget(300);
                    liftSystem.run();
                    collector.runModeOff();
                    collector.stackServoInit();
                })
                .back(35)
                .addDisplacementMarker(()->{
                    // flip the flipper and angle the angler
                    liftSystem.flipActivePos(4);
                    liftSystem.angleActivePos(5);
                })
                .splineToConstantHeading(new Vector2d(55, -38), 0)
                .addDisplacementMarker(()->{
                    liftSystem.microSecondPos();
                })
                .back(1)
                .waitSeconds(.5)
                .addDisplacementMarker(()->{
                    liftSystem.microInitPos();
                    liftSystem.flipInitPos();
                    liftSystem.angleInitPos();
                    sleep(500);
                    liftSystem.toGround();
                    liftSystem.run();
                    while (liftSystem.isRunning) liftSystem.run();
                })
                .back(.5)
                .build();

        TrajectorySequence seqCaz1 = drive.trajectorySequenceBuilder(redFar)
                //* pune pixel si ocoleste
                .splineToLinearHeading(new Pose2d(-38, -17, Math.toRadians(-75)), Math.toRadians(90))
                .addDisplacementMarker(()->{
                    collector.stackServoInit();
                })
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(-30, -11, Math.toRadians(180)), Math.toRadians(0))
                //* go collect
                .addDisplacementMarker(()->{
                    // collector
                    collector.runModeCollect();
                })
                .forward(22)
                .addDisplacementMarker(()->{
                    collector.stackServoUse();
                })
                .forward(1)
                .waitSeconds(.5)
                //* run fuga fuga
                .back(50)
                .addDisplacementMarker(()->{
                    collector.runModeOff();
                    // lift
                    liftSystem.setTarget(300);
                    liftSystem.run();
                    collector.stackServoInit();
                    collector.runModeThrow();
                })
                .back(35)
                .addDisplacementMarker(()->{
                    // flip the flipper and angle the angler
                    liftSystem.flipActivePos(4);
                    liftSystem.angleActivePos(5);
                })
                .splineToConstantHeading(new Vector2d(54, -36), 0)
                .back(0.5)
                .addDisplacementMarker(()->{
                    liftSystem.microSecondPos();
                })
                .forward(0.5)
                .waitSeconds(.5)
                .addDisplacementMarker(()->{
                    liftSystem.microInitPos();
                    liftSystem.flipInitPos();
                    liftSystem.angleInitPos();
                })
                .forward(.5)
                .waitSeconds(.5)
                //* leave and get new pixels
                .setTangent(Math.toRadians(180))
                .addDisplacementMarker(()->{
                    liftSystem.toGround();
                    liftSystem.run();
                })
                .splineToLinearHeading(new Pose2d(30, -13, Math.toRadians(180)), Math.toRadians(180))
                .addDisplacementMarker(()->{
                    collector.runModeCollect();
                })
                .forward(80)
//                .addDisplacementMarker(()->{
//                    collector.runModeCollect();
//                })
                .forward(3)
                .addDisplacementMarker(()->{
                    collector.stackServoUse();
                })
                .waitSeconds(.5)
                //* go back and place
                .back(50)
                .addDisplacementMarker(()->{
                    collector.runModeOff();
                    // lift
                    liftSystem.setTarget(300);
                    liftSystem.run();
                    collector.runModeOff();
                    collector.stackServoInit();
                })
                .back(35)
                .addDisplacementMarker(()->{
                    // flip the flipper and angle the angler
                    liftSystem.flipActivePos(4);
                    liftSystem.angleActivePos(5);
                })
                .splineToConstantHeading(new Vector2d(55, -38), 0)
                .addDisplacementMarker(()->{
                    liftSystem.microSecondPos();
                })
                .back(1)
                .waitSeconds(.5)
                .addDisplacementMarker(()->{
                    liftSystem.microInitPos();
                    liftSystem.flipInitPos();
                    liftSystem.angleInitPos();
                    sleep(500);
                    liftSystem.toGround();
                    liftSystem.run();
                    while (liftSystem.isRunning) liftSystem.run();
                })
                .back(.5)
                .build();

        TrajectorySequence seqCaz2 = drive.trajectorySequenceBuilder(redFar)
                //* pune pixel
                .splineToLinearHeading(new Pose2d(-32, -33, Math.toRadians(0)), Math.toRadians(0))
                .addDisplacementMarker(()->{
                    collector.stackServoInit();
                })
                .setTangent(Math.toRadians(210))
                .splineToLinearHeading(new Pose2d(-30, -11, Math.toRadians(180)), Math.toRadians(0))
                //* go collect
                .addDisplacementMarker(()->{
                    // collector
                    collector.runModeCollect();
                })
                .forward(22)
                .addDisplacementMarker(()->{
                    collector.stackServoUse();
                })
                .forward(1)
                .waitSeconds(.5)
                //* run fuga fuga
                .back(50)
                .addDisplacementMarker(()->{
                    collector.runModeOff();
                    // lift
                    liftSystem.setTarget(300);
                    liftSystem.run();
                    collector.stackServoInit();
                    collector.runModeThrow();
                })
                .back(35)
                .addDisplacementMarker(()->{
                    // flip the flipper and angle the angler
                    liftSystem.flipActivePos(4);
                    liftSystem.angleActivePos(5);
                })
                .splineToConstantHeading(new Vector2d(54, -36), 0)
                .back(0.5)
                .addDisplacementMarker(()->{
                    liftSystem.microSecondPos();
                })
                .forward(0.5)
                .waitSeconds(.5)
                .addDisplacementMarker(()->{
                    liftSystem.microInitPos();
                    liftSystem.flipInitPos();
                    liftSystem.angleInitPos();
                })
                .forward(.5)
                .waitSeconds(.5)
                //* leave and get new pixels
                .setTangent(Math.toRadians(180))
                .addDisplacementMarker(()->{
                    liftSystem.toGround();
                    liftSystem.run();
                })
                .splineToLinearHeading(new Pose2d(30, -13, Math.toRadians(180)), Math.toRadians(180))
                .addDisplacementMarker(()->{
                    collector.runModeCollect();
                })
                .forward(80)
//                .addDisplacementMarker(()->{
//                    collector.runModeCollect();
//                })
                .forward(3)
                .addDisplacementMarker(()->{
                    collector.stackServoUse();
                })
                .waitSeconds(.5)
                //* go back and place
                .back(50)
                .addDisplacementMarker(()->{
                    collector.runModeOff();
                    // lift
                    liftSystem.setTarget(300);
                    liftSystem.run();
                    collector.runModeOff();
                    collector.stackServoInit();
                })
                .back(35)
                .addDisplacementMarker(()->{
                    // flip the flipper and angle the angler
                    liftSystem.flipActivePos(4);
                    liftSystem.angleActivePos(5);
                })
                .splineToConstantHeading(new Vector2d(55, -38), 0)
                .addDisplacementMarker(()->{
                    liftSystem.microSecondPos();
                })
                .back(1)
                .waitSeconds(.5)
                .addDisplacementMarker(()->{
                    liftSystem.microInitPos();
                    liftSystem.flipInitPos();
                    liftSystem.angleInitPos();
                    sleep(500);
                    liftSystem.toGround();
                    liftSystem.run();
                    while (liftSystem.isRunning) liftSystem.run();
                })
                .back(.5)
                .build();

        telemetry.addLine("Ready!");
        telemetry.update();
        waitForStart();
        if (isStopRequested()) return;

        int caz = 2;//pipeline.gen_tip_autonomie();
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
