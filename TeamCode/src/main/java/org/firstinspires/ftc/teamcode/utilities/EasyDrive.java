package org.firstinspires.ftc.teamcode.utilities;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Implements some functions to easily use roadRunner, without copy-pasting stuff <br/>
 * TODO: Find out if <em>edgeangle</em> and <em>right</em> are useful for literally anything <br/>
 * ! Moved to AcmeRobotic's RoadRunner -> this is no longer useful
 *
 * @author mikey
 */

public class EasyDrive {
    private RoadRunner roadRunner;
    private double lastX = 0;
    private double lastY = 0;
    private double lastRotation = 0;
    public boolean waitForMovement = true;
    public boolean keepRotation = true;

    public EasyDrive(RoadRunner roadRunner, boolean waitToFinish) {
        this.roadRunner = roadRunner;
        this.waitForMovement = waitToFinish;
    }
    public EasyDrive(RoadRunner roadRunner) {
        this.roadRunner = roadRunner;
    }


    //* Driving functions
    public void Drive(double x, double y, double rotation, double power) {
        lastX = x;
        lastY = y;
        lastRotation = rotation;
        roadRunner.init(x, y, 0, true, rotation, power);
        if (!waitForMovement) return;
        while (roadRunner.isRunning) roadRunner.run();
    }
    public void Drive(double x, double y, double rotation) {
        Drive(x, y, rotation, 1);
    }
    public void Drive(double x, double y) {
        if (keepRotation)
            Drive(x, y, lastRotation, 1);
        else
            Drive(x, y, 0, 1);
    }


    //* This just restarts the bot to its original position
    public void Restart() {
        Drive(0, 0, 0, 1);
    }


    //* Turning functions, which turn the bot while still keeping its position
    public void Turn(double rotation, double power) {
        Drive(lastX, lastY, rotation, power);
    }
    public void Turn(double rotation) {
        Turn(rotation, 1);
    }
    // rotate -> turn, my dumbass keeps forgetting the word "turn"
    public void Rotate(double rotation, double power) { Turn(rotation, power); }
    public void Rotate(double rotation) { Turn(rotation); }


    //* Useful if you only need to move backwards and forwards again, without copypasting specific coords
    public void RelativeDrive(double x, double y, double rotation, double power) {
        Drive(lastX + x, lastY + y, lastRotation + rotation, power);
    }
    public void RelativeDrive(double x, double y, double rotation) {
        RelativeDrive(x, y, rotation, 1);
    }
    public void RelativeDrive(double x, double y) {
        RelativeDrive(x, y, 0, 1);
    }


    //* Easy way to initialize roadrunner through a hardwaremap
    public static EasyDrive createFromHWMap(HardwareMap hardwareMap, Telemetry telemetry) {
        Gyroscope gyro = new Gyroscope(hardwareMap);
        DrivingMotors drive = new DrivingMotors(hardwareMap);
        DrivingMotorsCentric centric = new DrivingMotorsCentric(gyro, drive);
        PositionSystem posSys = new PositionSystem(hardwareMap, gyro, drive.getLeftEncoder(), drive.getFrontEncoder());
        RoadRunner roadRunner1 = new RoadRunner(drive, centric, posSys, telemetry);
        return new EasyDrive(roadRunner1);
    }
    public RoadRunner getRoadRunner() {
        return this.roadRunner;
    }
}
