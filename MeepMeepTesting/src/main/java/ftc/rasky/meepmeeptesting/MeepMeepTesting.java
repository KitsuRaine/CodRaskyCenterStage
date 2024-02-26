package ftc.rasky.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeBlueDark;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeRedDark;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(640);
        FakeLiftsystem liftSystem = new FakeLiftsystem();
        FakeCollector collector = new FakeCollector();

        // bot size is 18x18 by default
        double width = 16.5;
        double length = 17.25;
        // TODO: constraints
        double maxVel = 49.376;
        double maxAccel = 48;
        double maxAngVel = 1.5; // TODO
        double maxAngAccel = 1.5; // TODO
        double trackWidth = 17.7;

        //* precalculated positions for CENTERSTAGE 2024
        //* when updated, copy to `/RoadRunner/util/StartPos.java` too
        Pose2d redClose = new Pose2d(48 - width/2, -70 + length /2, Math.toRadians(90));
        Pose2d redFar = new Pose2d(-71 + 24 + width/2, -70 + length /2, Math.toRadians(90));
        Pose2d blueClose = new Pose2d(48 - width/2, 70 - length /2, Math.toRadians(-90));
        Pose2d blueFar = new Pose2d(-71 + 24 + width/2, 70 - length /2, Math.toRadians(-90));

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeBlueDark())
                .setDimensions(width, length)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(maxVel, maxAccel, maxAngVel, maxAngAccel, trackWidth)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(blueClose)
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
                                .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}