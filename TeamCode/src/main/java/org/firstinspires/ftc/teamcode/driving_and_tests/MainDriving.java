package org.firstinspires.ftc.teamcode.driving_and_tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.components.Airplane;
import org.firstinspires.ftc.teamcode.components.Collector;
import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.components.LiftSystem;
import org.firstinspires.ftc.teamcode.components.SistemAgatare;
import org.firstinspires.ftc.teamcode.utilities.Buton;
import org.firstinspires.ftc.teamcode.utilities.DrivingMotors;
import org.firstinspires.ftc.teamcode.utilities.DrivingMotorsCentric;
import org.firstinspires.ftc.teamcode.utilities.Gyroscope;
import org.firstinspires.ftc.teamcode.utilities.Motor;
import org.firstinspires.ftc.teamcode.utilities.PositionSystem;
import org.firstinspires.ftc.teamcode.utilities.RoadRunner;
import org.firstinspires.ftc.teamcode.utilities.Servo;

/**
 * Main driving class <br/>
 * Handles everything for the drivers
 */
@TeleOp(name = "Main Driving", group = "Main")
public class MainDriving extends LinearOpMode {

    Gyroscope gyroscope;
    DrivingMotors drive;
    DrivingMotorsCentric centric;
    LiftSystem liftSystem;
    Collector collector;
    PositionSystem positionSystem;
    RoadRunner roadRunner;
    SistemAgatare agatare;
    Airplane avion;

    VoltageSensor voltageSensor;

    Buton up, down;
    Buton forward_servo, backward_servo, zeroAngle;
    boolean toZeroAngle = false;
    boolean roadRunnerInited = false;

    Gamepad.RumbleEffect endgameRumble;

    ElapsedTime runtime = new ElapsedTime();

    public void runOpMode() throws InterruptedException {
        boolean usedStack = false;
        boolean dropped = false;

        int farValue = 0;
        boolean farMode = false;

        boolean lastIterationStack = false;
        boolean lastIterationReset = false;
        boolean lastIterationFarMode = false;
        boolean lastIterationDropPixel = false;

        boolean resetFlip = false;
        boolean lowFlip = true;
        boolean midFlip = true;
        boolean highFlip = true;
        boolean sentGround = false;

        boolean flipped = false;

        endgameRumble = new Gamepad.RumbleEffect.Builder()
                .addStep(1.0, 1.0, 500)  //  Rumble right motor 100% for 500 mSec
                .addStep(0.0, 0.0, 500)
                .build();

        gyroscope = new Gyroscope(hardwareMap);
        drive = new DrivingMotors(hardwareMap);
        centric = new DrivingMotorsCentric(gyroscope, drive);
        collector = new Collector(hardwareMap);
        liftSystem = new LiftSystem(hardwareMap);
        positionSystem = new PositionSystem(hardwareMap, gyroscope, drive.getLeftEncoder(), drive.getFrontEncoder());
        roadRunner = new RoadRunner(drive, centric, positionSystem, telemetry);

        agatare = new SistemAgatare(hardwareMap);
        avion = new Airplane(hardwareMap);

//        voltageSensor = hardwareMap.voltageSensor.get();

        liftSystem.microInitPos();
        liftSystem.flipInitPos();
        collector.runModeOff();
        collector.stackServoInit();

        zeroAngle = new Buton();
        up = new Buton();
        down = new Buton();
        forward_servo = new Buton();
        backward_servo = new Buton();

        agatare.closeServo();
        avion.holdPlane();

        telemetry.addLine("Ready!");
        telemetry.update();

        waitForStart();
        if (isStopRequested())
            return;

        telemetry.update();

        ElapsedTime opModeTimer = new ElapsedTime();

        while (opModeIsActive() && !isStopRequested()) {
            //* Rumble effect for endgame, to inform the players
            if (opModeTimer.seconds() >= 85 && opModeTimer.seconds() <= 85.5) {
                gamepad1.runRumbleEffect(endgameRumble);
                gamepad2.runRumbleEffect(endgameRumble);
            }

            // ==================== STD MOVEMENT ====================
            if (!toZeroAngle) {
                if (gamepad1.dpad_up)
                    drive.run(0, 0.25, 0);
                else if (gamepad1.dpad_down)
                    drive.run(0, -0.25, 0);
                else if (gamepad1.dpad_right)
                    drive.run(0.25, 0, 0);
                else if (gamepad1.dpad_left)
                    drive.run(-0.25, 0, 0);
                else if (gamepad1.right_bumper)
                    drive.run(gamepad1.left_stick_x / 4, -gamepad1.left_stick_y / 4, gamepad1.right_stick_x / 4);
                else
                    drive.run(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x);
            }

            // ==================== COLLECTOR ====================
            if (gamepad1.right_trigger >= 0.2)
                collector.runModeCollect();
            else if (gamepad1.left_trigger >= 0.2)
                collector.runModeThrow();
            else
                collector.runModeOff();

            //* Stack servo control
            if (gamepad2.right_bumper && gamepad2.right_bumper != lastIterationStack) {
                usedStack = !usedStack;
                if (!usedStack)
                    collector.stackServoInit();
                else
                    collector.stackServoUse();
            }
            lastIterationStack = gamepad2.right_bumper;

            // ==================== LIFT ====================
            if (gamepad2.right_trigger >= 0.15)
                liftSystem.run(gamepad2.right_trigger);
            else if (gamepad2.left_trigger >= 0.15)
                liftSystem.run(-gamepad2.left_trigger);
            else
                liftSystem.run(0);

            //* Enable/disable far mode
            if (gamepad2.triangle && gamepad2.triangle != lastIterationFarMode) {
                farMode = !farMode;
                if(farMode)
                    farValue = 5;
                else
                    farValue = 0;
            }
            lastIterationFarMode = gamepad2.triangle;


            if (gamepad2.cross && gamepad2.cross != lastIterationReset) {
                flipped = !flipped;
                lowFlip = true;
                midFlip = true;
                highFlip = true;

                if (!flipped) {
                    liftSystem.flipInitPos();
                    liftSystem.angleInitPos();
                } else {
                    double currentPos = liftSystem.getPosition();
                    if (currentPos <= liftSystem.lowLevel) {
                        liftSystem.flipActivePos(LiftSystem.Flip.low);
                        liftSystem.angleActivePos(LiftSystem.Angle.low);
                    } else if (currentPos <= liftSystem.midLevel) {
                        liftSystem.flipActivePos(LiftSystem.Flip.mid);
                        liftSystem.angleActivePos(LiftSystem.Angle.mid);
                    } else {
                        liftSystem.flipActivePos(LiftSystem.Flip.high);
                        liftSystem.angleActivePos(LiftSystem.Angle.high);
                    }
                }
            }
            lastIterationReset = gamepad2.cross;

            //* Workaround for the flipping mechanism
            //* Need to wait 0.4s without hanging thread
            if (runtime.milliseconds() >= 400) {
                if (!lowFlip) {
                    flipped = true;
                    lowFlip = true;
                    liftSystem.flipActivePos(LiftSystem.Flip.low + farValue);
                    liftSystem.angleActivePos(LiftSystem.Angle.low + farValue);
                } else if (!midFlip) {
                    flipped = true;
                    midFlip = true;
                    liftSystem.flipActivePos(LiftSystem.Flip.mid + farValue);
                    liftSystem.angleActivePos(LiftSystem.Angle.mid + farValue);
                } else if (!highFlip) {
                    flipped = true;
                    highFlip = true;
                    liftSystem.flipActivePos(LiftSystem.Flip.high + farValue);
                    liftSystem.angleActivePos(LiftSystem.Angle.high + farValue);
                }
            }

            //* Handling the lift pos commands
            if (gamepad2.dpad_down) {
                liftSystem.toLow();
                lowFlip = false;
                midFlip = true;
                highFlip = true;
                runtime.reset();
            }
            if (gamepad2.dpad_left) {
                liftSystem.toMid();
                midFlip = false;
                lowFlip = true;
                highFlip = true;
                runtime.reset();
            }
            if (gamepad2.dpad_up) {
                liftSystem.toHigh();
                highFlip = false;
                lowFlip = true;
                midFlip = true;
                runtime.reset();
            }

            //* Reset lift
            if (gamepad2.circle){
                liftSystem.angleInitPos();
                liftSystem.flipInitPos();
                sentGround=true;

                // overwrite any other movement
                lowFlip = true;
                midFlip = true;
                highFlip = true;
                runtime.reset();
            }
            if(runtime.milliseconds()>=550 && sentGround)
            {
                sentGround=false;
                liftSystem.toGround();
                liftSystem.microInitPos();
            }

            //* fix for when the lift starts way too high
            if(gamepad2.left_bumper)
                liftSystem.UnderGround();

            //* drop pixel
            if (gamepad2.dpad_right && gamepad2.dpad_right != lastIterationDropPixel) {
                dropped = !dropped;
                if(!dropped)
                    liftSystem.microInitPos();
                else
                    liftSystem.microSecondPos();
            }
            lastIterationDropPixel=gamepad2.dpad_right;

            //* sistemul de agatare
            if (gamepad1.triangle)
                agatare.push();
            else if (gamepad1.cross)
                agatare.pull();
            else
                agatare.stop();

            //* makes the servo unlock bucata cu care se agata sus
            if (gamepad1.circle)
                agatare.openServo();

            //* sends the paper airplane flying
            if (gamepad2.square && !avion.released/* && opModeTimer.seconds() >= 90*/)
                avion.releasePlane();

            // ==================== CENTER ROBOT ====================
            if (zeroAngle.update(gamepad1.left_bumper)) {
                toZeroAngle = true;
                roadRunnerInited = false;
            }

            if (toZeroAngle) {
                if (!roadRunnerInited) {
                    roadRunnerInited = true;
                    roadRunner.init(positionSystem.getPosition().first, positionSystem.getPosition().second, 0, true, 0, 1);
                }

                if (Math.abs(gamepad1.left_stick_x) >= 0.2 || Math.abs(gamepad1.left_stick_y) >= 0.2 || Math.abs(gamepad1.right_stick_x) >= 0.2 || !roadRunner.isRunning)
                    toZeroAngle = false;
                else
                    roadRunner.run();
            }

            // ==================== END ====================
            positionSystem.run();

            telemetry.addData("x", positionSystem.getPosition().first);
            telemetry.addData("y", positionSystem.getPosition().second);
            telemetry.addData("angle", positionSystem.getAngle());
            telemetry.addLine();
            telemetry.addData("lift pos", liftSystem.getPosition());
            telemetry.addData("send ground", sentGround);
            telemetry.addData("low flip", !lowFlip);
            telemetry.addData("mid flip", !midFlip);
            telemetry.addData("high flip", !highFlip);
            telemetry.addData("flipped", flipped);
            telemetry.addData("airplane released?", avion.released);
            telemetry.addData("time spent in driving (in seconds)", opModeTimer.seconds());

            telemetry.update();
        }
    }
}