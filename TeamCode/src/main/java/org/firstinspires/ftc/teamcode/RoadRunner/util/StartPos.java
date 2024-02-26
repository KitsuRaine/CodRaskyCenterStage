package org.firstinspires.ftc.teamcode.RoadRunner.util;


import com.acmerobotics.roadrunner.geometry.Pose2d;

/**
 * Precalculated start positions for CENTERSTAGE 2023-2024
 * Used for FTC's robot dashboard
 * @author mikey
 */
public class StartPos {
    private static final double length = 17.25;
    private static final double width = 16.5;

    //TODO: Move from redClose to newRedClose; same with blue

    public static final Pose2d newRedClose = new Pose2d(48 - width/2, -70 + length /2, Math.toRadians(90));
    public static final Pose2d redClose = new Pose2d(12, -70 + length/2, Math.toRadians(90));
    public static final Pose2d redFar = new Pose2d(-71 + 24 + width/2, -70 + length /2, Math.toRadians(90));
    public static final Pose2d newBlueClose = new Pose2d(48 - width/2, 70 - length /2, Math.toRadians(-90));
    public static final Pose2d blueClose = new Pose2d(12, 70 - length/2, Math.toRadians(-90));
    public static final Pose2d blueFar = new Pose2d(-71 + 24 + width/2, 70 - length /2, Math.toRadians(-90));
}
