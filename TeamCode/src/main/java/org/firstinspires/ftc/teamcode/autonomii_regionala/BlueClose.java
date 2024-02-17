package org.firstinspires.ftc.teamcode.autonomii_regionala;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.RoadRunner.trajectorysequence.TrajectorySequence;

@Autonomous(name = "BlueClose", group = "main")
public class BlueClose extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        Pose2d start = new Pose2d(12, 70 - 17.25/2, Math.toRadians(-90));

        drive.setPoseEstimate(start);
        TrajectorySequence seq = drive.trajectorySequenceBuilder(start)
                .splineTo(new Vector2d(17,39),Math.toRadians(300))
                .setTangent(-5)
                .splineToLinearHeading(new Pose2d(46,36,Math.toRadians(180)),Math.toRadians(0))
                .setTangent(0)
                .strafeTo(new Vector2d(28,11))
                .strafeTo(new Vector2d(-57,11))
                .strafeTo(new Vector2d(28,11))
                .strafeTo(new Vector2d(45,35))
                .strafeTo(new Vector2d(28,11))
                .strafeTo(new Vector2d(-57,11))
                .strafeTo(new Vector2d(28,11))
                .strafeTo(new Vector2d(45,35))
                .strafeTo(new Vector2d(28,11))
                .strafeTo(new Vector2d(-57,11))
                .strafeTo(new Vector2d(28,11))
                .strafeTo(new Vector2d(45,35))
                .build();
        waitForStart();
        if (isStopRequested()) return;
        drive.followTrajectorySequence(seq);
    }
}
