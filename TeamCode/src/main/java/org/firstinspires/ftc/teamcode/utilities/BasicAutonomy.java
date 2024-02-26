package org.firstinspires.ftc.teamcode.utilities;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.components.Collector;
import org.firstinspires.ftc.teamcode.components.LiftSystem;

/**
 * This class is a replacement for LinearOpMode, simplifying stuff
 * and removing the need to copy and paste parts of the code everywhere
 * This was written before migrating to AcmeRobotic's RoadRunner
 * @author mikey
 */
abstract public class BasicAutonomy extends LinearOpMode {
    public EasyDrive easyDrive;
    public RoadRunner roadRunner;

    //* these just make it faster to write down `easyDrive`'s methods lol
    public void walk(double x, double y) {
        walk(x, y, 0);
    }
    public void walk(double x, double y, double rot) {
        walk(x, y, rot, 1);
    }
    public void walk(double x, double y, double rot, double power) {
        easyDrive.Drive(x, y, rot, power);
    }

    //* initializes easydrive and roadrunner

    /**
     * Initialize `<code>EasyDrive</code>` and driving-related stuff
     */
    public void initDrive() {
        easyDrive = EasyDrive.createFromHWMap(hardwareMap, telemetry);
        roadRunner = easyDrive.getRoadRunner();
    }

    //* faster way to just log text w/o value
    /**
     * Send something to telemetry quickly
     * @param text what to show
     */
    public void log(String text) {
        telemetry.addData(text, "");
        telemetry.update();
    }

    /**
     * Here you should put your initialization code <br/><br/>
     * Although its not required to override this function, you probably should if you have
     * anything else to initialize besides `<code>RoadRunner</code>` and driving-related stuff
     */
    public void initialize() throws InterruptedException {
        this.initDrive();
        log("Ready!");
    }

    /**
     * Here you should put the code that runs once the robot starts
     */
    abstract public void runCode() throws InterruptedException;

    //* this is the method required by LinearOpMode which
    //* runs the init and code at the appropriate times
    public void runOpMode() throws InterruptedException {
        this.initialize();

        // Wait until a button is pressed
        while (!opModeIsActive() && !isStopRequested());
        if (isStopRequested()) return;

        this.runCode();
    }
}
