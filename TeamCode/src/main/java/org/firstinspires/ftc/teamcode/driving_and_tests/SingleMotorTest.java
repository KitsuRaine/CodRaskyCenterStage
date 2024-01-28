package org.firstinspires.ftc.teamcode.driving_and_tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.utilities.Button;

/**
 * Class meant for testing a single motor.
 *
 * @author Lucian
 * @version 1.0
 */
@TeleOp(name = "Single Motor Test", group = "Main")
public class SingleMotorTest extends LinearOpMode {

    Gamepad gamepad;
    DcMotorEx motor;

    @Override
    public void runOpMode() throws InterruptedException {
        gamepad = gamepad1;
        motor = hardwareMap.get(DcMotorEx.class, "motor");

        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //This while loop will run after initialization until the program starts or until stop
        //is pressed
        while (!isStopRequested() && !opModeIsActive()) {
            telemetry.addLine("Initialization Ready");
            telemetry.addLine("Instructions:");
            telemetry.addLine("Use DPad Up/Down to Increment/Decrement Speed, " +
                    "Hold X/Square to power the motor");
            telemetry.update();
        }

        //This catches the stop button before the program starts
        if (isStopRequested()) return;

        double power = 0;
        Button incrementSpeed = new Button();
        Button decrementSpeed = new Button();
        Button enableMotor = new Button();
        //Main while loop that runs during the match
        while (opModeIsActive() && !isStopRequested()) {

            incrementSpeed.updateButton(gamepad.dpad_up);
            decrementSpeed.updateButton(gamepad.dpad_down);
            enableMotor.updateButton(gamepad.x);

            if (incrementSpeed.toggle()) {
                power += 0.1;
            }
            if (decrementSpeed.toggle()) {
                power -= 0.1;
            }
            if (enableMotor.press()) {
                motor.setPower(power);
            } else {
                motor.setPower(0);
            }

            telemetry.addData("Is Motor Powered?: ", motor.isMotorEnabled());
            telemetry.addData("Theoretical Power: ", power);
            telemetry.addData("Actual Power: ", motor.getPower());
            telemetry.addData("Motor Encoder: ", motor.getCurrentPosition());
            telemetry.update();
        }

    }
}
