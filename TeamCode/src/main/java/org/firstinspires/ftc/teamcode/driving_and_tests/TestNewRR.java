package org.firstinspires.ftc.teamcode.driving_and_tests;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.RoadRunner.trajectorysequence.TrajectorySequence;import org.firstinspires.ftc.teamcode.components.Collector;
import org.firstinspires.ftc.teamcode.components.LiftSystem;

@Autonomous(name="test roadrunner", group = "main")
public class TestNewRR extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        LiftSystem liftSystem = new LiftSystem(hardwareMap);

        Pose2d startPose = new Pose2d(0, 0, 0);
        drive.setPoseEstimate(startPose);

        TrajectorySequence trajSeq = drive.trajectorySequenceBuilder(startPose)
                .addDisplacementMarker(()->{
                    liftSystem.setTarget(100);
                    liftSystem.runUntilDone();
                })
                .back(.5)
                .waitSeconds(2.5)
                .addDisplacementMarker(()->{
                    liftSystem.toGround();
                    liftSystem.run();
                })
                .back(.5)
                .waitSeconds(1.5)
                .build();

        telemetry.addData("Ready!", "");
        telemetry.update();

        waitForStart();
        if (isStopRequested()) return;

        drive.followTrajectorySequence(trajSeq);
    }
}
