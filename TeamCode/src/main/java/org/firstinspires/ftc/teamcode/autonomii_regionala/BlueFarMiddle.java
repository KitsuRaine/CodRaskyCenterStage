package org.firstinspires.ftc.teamcode.autonomii_regionala;

import static org.firstinspires.ftc.teamcode.RoadRunner.util.StartPos.blueFar;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.RoadRunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.components.Collector;
import org.firstinspires.ftc.teamcode.components.LiftSystem;
import org.openftc.easyopencv.OpenCvWebcam;

@Autonomous(name = "BlueFarMiddle", group = "main")
public class BlueFarMiddle extends LinearOpMode {
    //DetectBlueObj pipeline = new DetectBlueObj();
    OpenCvWebcam webcam;
    LiftSystem liftSystem;
    Collector collector;
    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
//        webcam = camera.createWithPipeline(hardwareMap, pipeline);
        liftSystem = new LiftSystem(hardwareMap);
        collector = new Collector(hardwareMap);

        collector.stackServoUse();
        liftSystem.microInitPos();
        liftSystem.flipInitPos();
        liftSystem.angleInitPos();

        drive.setPoseEstimate(blueFar);

        TrajectorySequence seqCaz0 = drive.trajectorySequenceBuilder(blueFar)
                //* pune pixel si ocoleste
                .splineToSplineHeading(new Pose2d(-31,32, Math.toRadians(0)), Math.toRadians(0))
                .addDisplacementMarker(()->{
                    collector.stackServoInit();
                })
                .setTangent(Math.toRadians(190))
                .splineToLinearHeading(new Pose2d(-45, 13, Math.toRadians(180)), Math.toRadians(0))
                //* grab pixels
                .addDisplacementMarker(()->{
                    // collector
                    collector.runModeCollect();
                })
                .forward(17)
                .addDisplacementMarker(()->{
                    collector.stackServoUse();
                })
                .forward(.15)
                .waitSeconds(.5)
                //* run fuga fuga
                .splineToLinearHeading(new Pose2d(-50, 13, Math.toRadians(180)), Math.toRadians(0))
                .back(55)
                .addDisplacementMarker(()->{
                    collector.runModeOff();
                    // lift
                    liftSystem.setTarget(250);
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
                //* place pixels
                .splineToConstantHeading(new Vector2d(51, 40), Math.toRadians(0))
                .addDisplacementMarker(()->{
                    liftSystem.microSecondPos();
                })
                .back(0.15)
                .waitSeconds(.75)
                .addDisplacementMarker(()->{
                    liftSystem.microInitPos();
                    liftSystem.flipInitPos();
                    liftSystem.angleInitPos();
                })
                .forward(.15)
                .waitSeconds(.5)
                //* leave and get new pixels
                .setTangent(Math.toRadians(180))
                .addDisplacementMarker(()->{
                    liftSystem.toGround();
                    liftSystem.run();
                })
                .splineToLinearHeading(new Pose2d(30, 13, Math.toRadians(180)), Math.toRadians(180))
                .addDisplacementMarker(()->{
                    collector.runModeCollect();
                })
                .forward(30)
                .splineToLinearHeading(new Pose2d(-50, 13, Math.toRadians(180)), Math.toRadians(0))
                .forward(15)
                .addDisplacementMarker(()->{
                    collector.stackServoUse();
                })
                .waitSeconds(.5)
                //* go back and place
                .splineToLinearHeading(new Pose2d(-50, 13, Math.toRadians(180)), Math.toRadians(0))
                .back(55)
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
                .splineToConstantHeading(new Vector2d(51, 40), Math.toRadians(0))
                .addDisplacementMarker(()->{
                    liftSystem.microSecondPos();
                })
                .back(.15)
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
                .back(.15)
                .build();

        TrajectorySequence seqCaz1 = drive.trajectorySequenceBuilder(blueFar)
                //* impinge pixel
                .splineToLinearHeading(new Pose2d(-38, 17, Math.toRadians(75)), Math.toRadians(-90))
                .addDisplacementMarker(()->{
                    collector.stackServoInit();
                })
                .setTangent(Math.toRadians(-90))
                .splineToLinearHeading(new Pose2d(-40, 13, Math.toRadians(180)), Math.toRadians(-90))
                //* grab pixels
                .addDisplacementMarker(()->{
                    // collector
                    collector.runModeCollect();
                })
                .forward(22)
                .addDisplacementMarker(()->{
                    collector.stackServoUse();
                })
                .waitSeconds(.5)
                .forward(1)
                .addDisplacementMarker(()->{
                    collector.stackServoInit();
                })
                .back(2)
                .addDisplacementMarker(()->{
                    collector.stackServoUse();
                })
                .forward(.5)
                //* run fuga fuga
                .splineToLinearHeading(new Pose2d(-40, 14, Math.toRadians(180)), Math.toRadians(0))
                .back(40)
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
                //* place pixels
                .splineToConstantHeading(new Vector2d(54, 40), Math.toRadians(0))
                .addDisplacementMarker(()->{
                    liftSystem.microSecondPos();
                })
                .back(0.5)
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
                .splineToLinearHeading(new Pose2d(30, 14, Math.toRadians(180)), Math.toRadians(180))
                .addDisplacementMarker(()->{
                    collector.runModeCollect();
                })
                .forward(30)
                .splineToLinearHeading(new Pose2d(-40, 13, Math.toRadians(180)), Math.toRadians(0))
                .forward(23)
                .addDisplacementMarker(()->{
                    collector.stackServoUse();
                })
                .waitSeconds(.5)
                //* go back and place
                .splineToLinearHeading(new Pose2d(-40, 14, Math.toRadians(180)), Math.toRadians(0))
                .back(40)
                .addDisplacementMarker(()->{
                    collector.runModeOff();
                    // lift
                    liftSystem.setTarget(300);
                    liftSystem.run();
                    collector.runModeOff();
                    collector.stackServoInit();
                })
                .back(32)
                .addDisplacementMarker(()->{
                    // flip the flipper and angle the angler
                    liftSystem.flipActivePos(4);
                    liftSystem.angleActivePos(5);
                })
                .splineToConstantHeading(new Vector2d(54, 40), Math.toRadians(0))
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

        TrajectorySequence seqCaz2 = drive.trajectorySequenceBuilder(blueFar)
                //* place pixel
                .setTangent(Math.toRadians(-90))
                .splineToLinearHeading(new Pose2d(-40, 35, Math.toRadians(-150)),Math.toRadians(180))
                .addDisplacementMarker(()->{
                    // let go of purple pixel
                    collector.stackServoInit();
                })
                .setTangent(Math.toRadians(-65))
                .splineToLinearHeading(new Pose2d(-40, 13, Math.toRadians(180)), Math.toRadians(-155))
                //* get pixel
                .addDisplacementMarker(()->{
                    // collector
                    collector.runModeCollect();
                })
                .forward(22)
                .addDisplacementMarker(()->{
                    collector.stackServoUse();
                })
                .waitSeconds(.5)
                .forward(1)
                .addDisplacementMarker(()->{
                    collector.stackServoInit();
                })
                .back(2)
                .addDisplacementMarker(()->{
                    collector.stackServoUse();
                })
                .forward(.5)
                //* run fuga fuga
                .splineToLinearHeading(new Pose2d(-40, 14, Math.toRadians(180)), Math.toRadians(0))
                .back(40)
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
                //* place pixels
                .splineToConstantHeading(new Vector2d(54, 40), Math.toRadians(0))
                .addDisplacementMarker(()->{
                    liftSystem.microSecondPos();
                })
                .back(0.5)
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
                .splineToLinearHeading(new Pose2d(30, 14, Math.toRadians(180)), Math.toRadians(180))
                .addDisplacementMarker(()->{
                    collector.runModeCollect();
                })
                .forward(30)
                .splineToLinearHeading(new Pose2d(-40, 13, Math.toRadians(180)), Math.toRadians(0))
                .forward(23)
                .addDisplacementMarker(()->{
                    collector.stackServoUse();
                })
                .waitSeconds(.5)
                //* go back and place
                .splineToLinearHeading(new Pose2d(-40, 14, Math.toRadians(180)), Math.toRadians(0))
                .back(40)
                .addDisplacementMarker(()->{
                    collector.runModeOff();
                    // lift
                    liftSystem.setTarget(300);
                    liftSystem.run();
                    collector.runModeOff();
                    collector.stackServoInit();
                })
                .back(32)
                .addDisplacementMarker(()->{
                    // flip the flipper and angle the angler
                    liftSystem.flipActivePos(4);
                    liftSystem.angleActivePos(5);
                })
                .splineToConstantHeading(new Vector2d(54, 40), Math.toRadians(0))
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

        int caz = 0;//pipeline.gen_tip_autonomie();
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
