package org.firstinspires.ftc.teamcode.quick_movement;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.utilities.BasicAutonomy;
import org.firstinspires.ftc.teamcode.utilities.DrivingMotors;
import org.firstinspires.ftc.teamcode.utilities.DrivingMotorsCentric;
import org.firstinspires.ftc.teamcode.utilities.Gyroscope;
import org.firstinspires.ftc.teamcode.utilities.PositionSystem;
import org.firstinspires.ftc.teamcode.utilities.RoadRunner;


@Disabled
@Autonomous(name = "B-left-off", group = "main")
public class left_off extends BasicAutonomy {
    @Override
    public void runCode() throws InterruptedException {
        log("Going slowly left...");
        walk(-500, 0, 0, .5);
    }
}
