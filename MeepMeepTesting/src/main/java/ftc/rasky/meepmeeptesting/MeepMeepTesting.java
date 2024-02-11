package ftc.rasky.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeBlueDark;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(640);

        // bot size is 18x18 by default
        double width = 16.5;
        double height = 17.25;

        //* precalculated positions for CENTERSTAGE 2024
        Pose2d redClose = new Pose2d(12, -70 + height/2, Math.toRadians(90));
        Pose2d redFar = new Pose2d(-36, -70 + height/2, Math.toRadians(90));
        Pose2d blueClose = new Pose2d(12, 70 - height/2, Math.toRadians(-90));
        Pose2d blueFar = new Pose2d(-36, 70 - height/2, Math.toRadians(-90));

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeBlueDark())
                .setDimensions(width, height)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(blueFar)
                                .splineTo(new Vector2d(10, 10), 0)
                                .splineTo(new Vector2d(25, -15), 0)
                                .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}