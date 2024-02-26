package org.firstinspires.ftc.teamcode.driving_and_tests;

import static org.firstinspires.ftc.teamcode.RoadRunner.util.StartPos.blueClose;
import static org.firstinspires.ftc.teamcode.RoadRunner.util.StartPos.blueFar;
import static org.firstinspires.ftc.teamcode.RoadRunner.util.StartPos.redClose;
import static org.firstinspires.ftc.teamcode.RoadRunner.util.StartPos.redFar;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.RoadRunner.trajectorysequence.TrajectorySequence;

@Disabled
@Autonomous(name = "TestRR", group = "main")
public class TestNewRR extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        drive.setPoseEstimate(blueFar);

        TrajectorySequence trajSeq = drive.trajectorySequenceBuilder(blueFar)
                .splineToSplineHeading(new Pose2d(-33,33, Math.toRadians(0)),Math.toRadians(0))
                // place pixel
                .lineToSplineHeading(new Pose2d(-57,11,Math.toRadians(180)))
                // collect 0
                .back(90)
                .splineToConstantHeading(new Vector2d(47,35), Math.toRadians(0))
                // place 0
                .setTangent(3)
                .splineToConstantHeading(new Vector2d(33,11),Math.toRadians(180))
                .forward(90)
                // collect 1
                .back(90)
                .splineToConstantHeading(new Vector2d(47,35),Math.toRadians(0))
                // place 1
                .setTangent(3)
                .splineToConstantHeading(new Vector2d(33,11),Math.toRadians(180))
                .forward(90)
                // collect 2
                .back(90)
                .splineToConstantHeading(new Vector2d(47,35),Math.toRadians(0))
                // place 2 & park?
                .build();

        telemetry.addData("Ready!", "");
        telemetry.update();

        waitForStart();
        if (isStopRequested()) return;

        drive.followTrajectorySequence(trajSeq);
    }
}
