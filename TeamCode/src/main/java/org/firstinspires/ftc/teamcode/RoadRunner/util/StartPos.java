package org.firstinspires.ftc.teamcode.RoadRunner.util;


import com.acmerobotics.roadrunner.geometry.Pose2d;

/**
 * Precalculated start positions for CENTERSTAGE 2023-2024
 * Used for FTC's robot dashboard
 * @author mikey
 */
public class StartPos {
    private static double height = 17.25;

    public static Pose2d redClose = new Pose2d(12, -70 + height/2, Math.toRadians(90));
    public static Pose2d redFar = new Pose2d(-36, -70 + height/2, Math.toRadians(90));
    public static Pose2d blueClose = new Pose2d(12, 70 - height/2, Math.toRadians(-90));
    public static Pose2d blueFar = new Pose2d(-36, 70 - height/2, Math.toRadians(-90));
}
