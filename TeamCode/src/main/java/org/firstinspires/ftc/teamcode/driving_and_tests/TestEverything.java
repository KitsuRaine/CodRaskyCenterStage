package org.firstinspires.ftc.teamcode.driving_and_tests;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.components.Airplane;
import org.firstinspires.ftc.teamcode.components.Collector;
import org.firstinspires.ftc.teamcode.components.LiftSystem;
import org.firstinspires.ftc.teamcode.components.SistemAgatare;
import org.firstinspires.ftc.teamcode.utilities.camera;
import org.openftc.easyopencv.OpenCvWebcam;

/**
 * This autonomous class exists so construction can't blame
 * our programmers for their stuff <br/>
 * Yes, we could use Main Driving for it, but this is written for
 * usage without any controllers
 * @author mikey
 */
//@Disabled
@Autonomous(group = "test")
public class TestEverything extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        boolean movementTest = true;
        boolean collectorTest = true;
        boolean stackServoTest = true;
        boolean liftTest = true;
        boolean airplaneServoTest = true;
        boolean agatareTest = true;

        telemetry.addLine("Initializing...");
        telemetry.update();

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Collector collector = new Collector(hardwareMap);
        LiftSystem liftSystem = new LiftSystem(hardwareMap);
        Airplane airplane = new Airplane(hardwareMap);
        SistemAgatare agatare = new SistemAgatare(hardwareMap);

        //* Enabling the camera so we can have a camera stream too
        TestPipeline pipeline = new TestPipeline();
        camera.createWithPipeline(hardwareMap, pipeline);

        telemetry.addLine("Please disable the 30 seconds timer before starting this test!");
        telemetry.addLine();
        telemetry.addLine("To skip certain tests, modify the booleans in this file!");
        telemetry.addLine("This test will do the following:");
        telemetry.addLine("- Move the robot back and forward 10 inches");
        telemetry.addLine("- Starts the collector in both directions");
        telemetry.addLine("- Move the stack servos");
        telemetry.addLine("- Raises the lift at all the positions");
        telemetry.addLine("- Flips the lift and angle servo");
        telemetry.addLine("- Opens and closes the microservo from the pixel cage");
        telemetry.addLine("- Moves the airplane servo");
        telemetry.addLine("- Tests the holding system");
        telemetry.update();

        waitForStart();
        if (isStopRequested()) return;

        if (movementTest){
            telemetry.addLine("Moving forward 10 inches...");
            telemetry.update();
            drive.followTrajectory(
                    drive.trajectoryBuilder(new Pose2d())
                            .forward(10)
                            .build()
            );
            telemetry.addLine("Moved forward 10 inches!");
            telemetry.update();
            sleep(3000);

            telemetry.addLine("Moving backwards 10 inches...");
            telemetry.update();
            drive.followTrajectory(
                    drive.trajectoryBuilder(new Pose2d())
                            .back(10)
                            .build()
            );
            telemetry.addLine("Moved backwards 10 inches!");
            telemetry.update();
            sleep(3000);
        }

        if (collectorTest) {
            telemetry.addLine("Collecting pixels...");
            telemetry.update();
            collector.runModeCollect();
            sleep(3000);

            telemetry.addLine("Spitting out...");
            telemetry.update();
            collector.runModeThrow();
            sleep(3000);

            collector.runModeOff();
        }

        if (stackServoTest) {
            telemetry.addLine("Grabbing pixel (w/ the claws)");
            telemetry.update();
            collector.stackServoInit();
            sleep(1000);

            telemetry.addLine("Leaving pixel...");
            telemetry.update();
            collector.stackServoUse();
            sleep(1000);
        }

        if (liftTest) {
            telemetry.addLine("Raising lift to low");
            telemetry.update();
            liftSystem.toLow();
            liftSystem.runUntilDone();
            sleep(1000);

            telemetry.addLine("Flipping and angling..");
            telemetry.update();
            liftSystem.flipActivePos(LiftSystem.Flip.low);
            liftSystem.angleActivePos(LiftSystem.Angle.low);
            sleep(1000);
            liftSystem.flipInitPos();
            liftSystem.angleInitPos();

            telemetry.addLine("Raising lift to mid");
            telemetry.update();
            liftSystem.toMid();
            liftSystem.runUntilDone();
            sleep(1000);

            telemetry.addLine("Flipping and angling..");
            telemetry.update();
            liftSystem.flipActivePos(LiftSystem.Flip.mid);
            liftSystem.angleActivePos(LiftSystem.Angle.mid);
            sleep(1000);
            liftSystem.flipInitPos();
            liftSystem.angleInitPos();

            telemetry.addLine("Raising lift to high");
            telemetry.update();
            liftSystem.toHigh();
            liftSystem.runUntilDone();
            sleep(1000);

            telemetry.addLine("Flipping and angling..");
            telemetry.update();
            liftSystem.flipActivePos(LiftSystem.Flip.high);
            liftSystem.angleActivePos(LiftSystem.Angle.high);
            sleep(1000);
            liftSystem.flipInitPos();
            liftSystem.angleInitPos();

            telemetry.addLine("Grounding lift");
            telemetry.update();
            liftSystem.toGround();
            liftSystem.runUntilDone();
            sleep(1000);
        }

        if (airplaneServoTest) {
            telemetry.addLine("Rotating airplane servo...");
            telemetry.update();
            airplane.releasePlane();
            sleep(1000);
        }

        if (agatareTest) {
            telemetry.addLine("Opening trap!");
            telemetry.update();
            agatare.openServo();
            sleep(1000);

            telemetry.addLine("Pulling for 3 seconds...");
            telemetry.update();
            agatare.pull();
            sleep(3000);
            agatare.stop();

            telemetry.addLine("Pushing for 3 seconds...");
            telemetry.update();
            agatare.push();
            sleep(3000);
            agatare.stop();
        }
    }
}
