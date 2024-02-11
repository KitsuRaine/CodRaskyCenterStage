package org.firstinspires.ftc.teamcode.driving_and_tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.components.Collector;
import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.components.LiftSystem;
import org.firstinspires.ftc.teamcode.utilities.Buton;
import org.firstinspires.ftc.teamcode.utilities.DrivingMotors;
import org.firstinspires.ftc.teamcode.utilities.DrivingMotorsCentric;
import org.firstinspires.ftc.teamcode.utilities.Gyroscope;
import org.firstinspires.ftc.teamcode.utilities.Motor;
import org.firstinspires.ftc.teamcode.utilities.PositionSystem;
import org.firstinspires.ftc.teamcode.utilities.RoadRunner;
import org.firstinspires.ftc.teamcode.utilities.Servo;
import org.firstinspires.ftc.robotcontroller.external.samples.ConceptGamepadRumble;

@TeleOp(name = "Test Driving", group = "Main")
public class MainDriving extends LinearOpMode {

    Gyroscope gyroscope;
    DrivingMotors drive;
    DrivingMotorsCentric centric;
    LiftSystem liftSystem;
    Collector collector;
    PositionSystem positionSystem;
    RoadRunner roadRunner;
    Motor motor_ridicare;
    Servo servo_agatare;
    Servo avion;
    Buton up, down;
    Buton forward_servo, backward_servo, zeroAngle;
    boolean toZeroAngle = false;
    boolean roadRunnerInited = false;

    Gamepad drivingGamepad;
    Gamepad utilityGamepad;
    Gamepad.RumbleEffect endgameRumble;

    public void runOpMode() throws InterruptedException {

        int usedStack=0;
        int dropped=0;
        boolean farMode=false;
        boolean resetFlip=false;
        boolean lastIterationStack=false;
        boolean lastIterationReset=false;
        boolean lastIterationFarMode=false;
        boolean lastIterationFlip=false;
        boolean lastIterationDropPixel=false;

        drivingGamepad = gamepad1;
        utilityGamepad = gamepad2;
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

        motor_ridicare = new Motor(hardwareMap, "motor_ridicare", false, true, true);
        servo_agatare = new Servo(hardwareMap, "servo_agatare");
        avion = new Servo(hardwareMap, "avion");
        servo_agatare.setPowerRange(500, 2500);
        avion.setPowerRange(500, 2500);

        liftSystem.microInitPos();
        liftSystem.flipInitPos();
        collector.runModeOff();
        collector.closeStackServo();

        zeroAngle = new Buton();
        up = new Buton();
        down = new Buton();
        forward_servo = new Buton();
        backward_servo = new Buton();
        servo_agatare.setPosition(0);
        avion.setPosition(0);

        telemetry.addData("Ready!", "");
        telemetry.update();

        while (!opModeIsActive() && !isStopRequested()) {
        }

        if (isStopRequested())
            return;

        telemetry.update();

        ElapsedTime opModeTimer = new ElapsedTime();

        while (opModeIsActive() && !isStopRequested()) {
            if (opModeTimer.seconds() >= 85 && opModeTimer.seconds() <= 85.5) {
                drivingGamepad.runRumbleEffect(endgameRumble);
                utilityGamepad.runRumbleEffect(endgameRumble);

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

            if (gamepad2.right_bumper && gamepad2.right_bumper != lastIterationStack) {
                usedStack++;

                if (usedStack % 2 == 0)
                    collector.closeStackServo();
                else
                    collector.openStackServo();
            }
            lastIterationStack = gamepad2.right_bumper;

            // ==================== LIFT ====================

            if (gamepad2.right_trigger >= 0.15)
                liftSystem.run(gamepad2.right_trigger);
            else if (gamepad2.left_trigger >= 0.15)
                liftSystem.run(-gamepad2.left_trigger);
            else
                liftSystem.run(0);

            if (gamepad2.y && gamepad2.y != lastIterationFarMode) {
                if(farMode == true)
                    farMode=false;
                else
                    farMode=true;
            }
            lastIterationFarMode = gamepad2.y;

            if (gamepad2.a && gamepad2.a != lastIterationReset) {
                if(resetFlip == true)
                    resetFlip=false;
                else
                    resetFlip=true;
            }
            lastIterationReset = gamepad2.a;


            if(farMode == true) {
                if (resetFlip == true) {
                    liftSystem.flipInitPos();
                    liftSystem.angleInitPos();
                } else if (liftSystem.getReachedTarget() >= 300) {
                    liftSystem.flipActivePos(10);
                    liftSystem.angleActivePos(13.5);
                }
            }
            else if(farMode == false) {
                if (resetFlip == true) {
                    liftSystem.flipInitPos();
                    liftSystem.angleInitPos();
                } else if (liftSystem.getReachedTarget() >= 575) {
                    liftSystem.flipActivePos(0);
                    liftSystem.angleActivePos(0);
                } else if (liftSystem.getReachedTarget() >= 400) {
                    liftSystem.flipActivePos(2.5);
                    liftSystem.angleActivePos(3.25);
                } else if (liftSystem.getReachedTarget() >= 250) {
                    liftSystem.flipActivePos(5);
                    liftSystem.angleActivePos(6.5);
                }
            }
            lastIterationFlip = gamepad2.a;

            if (gamepad2.b){
                liftSystem.toGround();
            }
            if(gamepad2.left_bumper)
            {
                liftSystem.UnderGround();
            }


            if (gamepad2.dpad_down) {
                liftSystem.toLow();
            } else if (gamepad2.dpad_left) {
                liftSystem.toMid();
            } else if (gamepad2.dpad_up) {
                liftSystem.toHigh();
            }
            else if (gamepad2.dpad_right && gamepad2.dpad_right != lastIterationDropPixel) {
                ++ dropped;
                if(dropped % 2 == 0)
                    liftSystem.microInitPos();
                else
                    liftSystem.microSecondPos();
            }
            lastIterationDropPixel=gamepad2.dpad_right;

            if (gamepad1.y /*&& motor_ridicare.getPosition()<=*/)
                motor_ridicare.setPower(1);
            else if (gamepad1.a /*&& motor_ridicare.getPosition()<=*/)
                motor_ridicare.setPower(-1);
            else
                motor_ridicare.setPower(0);

            if (gamepad1.b)
                servo_agatare.setPosition(0.4);

            if (gamepad2.x)
                avion.setPosition(0.2);

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

            telemetry.addData("x:", positionSystem.getPosition().first);
            telemetry.addData("y:", positionSystem.getPosition().second);
            telemetry.addData("angle:", positionSystem.getAngle());

            telemetry.update();
        }
    }
}