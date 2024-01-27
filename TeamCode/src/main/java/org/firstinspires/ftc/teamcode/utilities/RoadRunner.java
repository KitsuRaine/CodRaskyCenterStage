package org.firstinspires.ftc.teamcode.utilities;

import org.firstinspires.ftc.teamcode.utilities.*;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.utilities.DrivingMotors;
import org.firstinspires.ftc.teamcode.utilities.DrivingMotorsCentric;
import org.firstinspires.ftc.teamcode.utilities.Gyroscope;
import org.firstinspires.ftc.teamcode.utilities.PositionSystem;

// version 3.1

public class RoadRunner {

    private DrivingMotors drive;
    private DrivingMotorsCentric centric;
    private PositionSystem positionSystem;
    private double h, b, k1, k2, xc, yc, r;
    private double x, y;
    private Matrix A, B;
    private Pair<Double, Double> move;
    private Telemetry telemetry;
    private double sgn, finalRobotAngle, rotatingPower;
    public boolean isRunning;
    private boolean out, positionIsUpdating, angleIsUpdating, ok, positionFinalUpdate;
    private double power = 1;
    private boolean under30cm, right;
    private double angleModif;
    private final double degree = (Geometry.pi / 180);
    private final double angleCorrectionDelta = degree * 5;
    private double precision;

    /*
        x, y - target point
        Xc, Yc - center of circle edge
        b - base of triangle ( (0, 0), (x, y), (Xc, Yc) )
        h - height of triangle
        r - radius
        k1, k2, constants for every iteration
    */

    public RoadRunner(DrivingMotors drive, DrivingMotorsCentric centric, PositionSystem positionSystem, Telemetry telemetry) {

        this.telemetry = telemetry;
        this.drive = drive;
        this.centric = centric;
        this.positionSystem = positionSystem;

        A = new Matrix();
        B = new Matrix();

        move = new Pair<>();
        isRunning = false;

        precision = 1;
    }

    private void calc(double x, double y, double angle, double sgn) {

        if (angle == 0)
            angle = 1e-6;

        b = Geometry.distance(0, 0, x, y);
        r = (Geometry.pi / 2 * b) / angle;
        h = Math.sqrt(r * r - (b / 2) * (b / 2));
        k1 = (x * x + y * y) / 2;
        k2 = sgn * h * b;

        A.set(2);
        B.set(2);

        A.set(1, 1, x);
        A.set(1, 2, y);
        A.set(2, 1, y);
        A.set(2, 2, -x);

        if (A.determinant() == 0)
            return;

        A = A.inverse();

        B.set(1, 1, k1);
        B.set(2, 1, k2);

        A = A.multi(B);

        xc = A.get(1, 1);
        yc = A.get(2, 1);
    }

    public void init(double x, double y, double edgeAngle, boolean right, double robotAngle, double power) {

        this.x = x;
        this.y = y;
        this.finalRobotAngle = robotAngle;
        under30cm = false;
        this.power = power;
        ok = true;
        this.right = right;

        // get (xc, yc)
        calc(x - positionSystem.getPosition().first, y - positionSystem.getPosition().second, edgeAngle, (right ? 1 : -1));

        double sgn = (right ? 1 : -1);

        xc += positionSystem.getPosition().first;
        yc += positionSystem.getPosition().second;
        this.sgn = sgn;
        positionFinalUpdate = false;

        isRunning = true;
        positionIsUpdating = true;
        if (robotAngle == -1)
            angleIsUpdating = false;
        else
            angleIsUpdating = true;

        out = true;

        double m = Math.atan2(y - positionSystem.getPosition().first, x - positionSystem.getPosition().second);
        move.first = Math.cos(m) * power;
        move.second = Math.sin(m) * power;
        angleModif = 0;
    }

    public void setPrecision(double precision) {

        this.precision = precision;
    }

    public double getDistanceFromDestination() {

        return Geometry.distance(x, y, positionSystem.getPosition().first, positionSystem.getPosition().second);
    }

    // async function
    public void run() {

        //telemetry.addData("x: ", positionSystem.getPosition().first);
        //telemetry.addData("y: ", positionSystem.getPosition().second);

        // ======================= POSITION UPDATE =======================

        if (positionIsUpdating && !positionFinalUpdate && Geometry.distance(x, y, positionSystem.getPosition().first, positionSystem.getPosition().second) > 5) {

            double m = Math.atan2(yc - positionSystem.getPosition().second, xc - positionSystem.getPosition().first);
            double distanceFromCenter = Geometry.distance(positionSystem.getPosition().first, positionSystem.getPosition().second, xc, yc);

            if (right)
                m += Geometry.pi / 2;
            else
                m -= Geometry.pi / 2;

            m -= sgn * angleCorrectionDelta * (distanceFromCenter - r);
            m = Gyroscope.fixAngle(m);

            double auxPower = Math.min(power, (Geometry.distance(positionSystem.getPosition().first, positionSystem.getPosition().second, x, y) / 100));
            auxPower = Math.max(auxPower, Math.min(0.4, power));

            move.first = Math.cos(m) * auxPower;
            move.second = Math.sin(m) * auxPower;

            if (Geometry.distance(positionSystem.getPosition().first, positionSystem.getPosition().second, x, y) <= 30)
                under30cm = true;
        }
        else if (positionIsUpdating && Geometry.distance(x, y, positionSystem.getPosition().first, positionSystem.getPosition().second) > precision) {

            positionFinalUpdate = true;
            double m = Math.atan2(y - positionSystem.getPosition().second, x - positionSystem.getPosition().first);
            move.first = Math.cos(m) * 0.2;
            move.second = Math.sin(m) * 0.2;
        }
        else {

            positionFinalUpdate = false;
            positionIsUpdating = false;
            move.first = 0.0;
            move.second = 0.0;
        }

        // ======================= ROBOT ANGLE UPDATE =======================

        if (angleIsUpdating) {

            if (positionFinalUpdate && false)
                rotatingPower = 0;
            else {

                double dif;

                if (Gyroscope.fixAngle(positionSystem.getAngle() - finalRobotAngle) > Gyroscope.fixAngle(finalRobotAngle - positionSystem.getAngle())) {

                    // case when rotate to left

                    dif = Gyroscope.fixAngle(finalRobotAngle - positionSystem.getAngle());
                    rotatingPower = - Math.min(0.5, (Geometry.toDegrees(dif) * 2) / 100);
                    rotatingPower = Math.min(rotatingPower, - 0.15);
                }
                else {

                    // case when rotate to right

                    dif = Gyroscope.fixAngle(positionSystem.getAngle() - finalRobotAngle);
                    rotatingPower = Math.min(0.5, (Geometry.toDegrees(dif) * 2) / 100);
                    rotatingPower = Math.max(0.15, rotatingPower);
                }

                if (!positionIsUpdating && dif <= degree / 2)
                    angleIsUpdating = false;
            }
        }

        centric.run(move.first, move.second, rotatingPower*0.7);
        positionSystem.run();
        telemetry.update();

        if (!positionIsUpdating && !angleIsUpdating) {

            isRunning = false;
            centric.run(0, 0, 0);
        }
    }

    public void STOP() {

        centric.run(0, 0, 0);
    }
}
