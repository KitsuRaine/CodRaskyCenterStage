package ftc.rasky.meepmeeptesting;

/**
 * Dummy class that fakes the liftsystem so we don't have to
 * comment out the markers in MeepMeep when copying stuff over
 */
public class FakeLiftsystem {
    public boolean isRunning = false;

    public FakeLiftsystem() {}

    public void microInitPos() {}

    public void microFirstPos() {}

    public void microSecondPos() {}

    public void angleInitPos() {}

    public void angleActivePos(double height) {}

    public void flipInitPos() {}

    public void flipActivePos(double height) {}

    public void setPower(double power) {}

    public int getPosition() {return 0;}

    public double getPower() {return 0;}
    public void UnderGround() {}

    public void toGround() {}

    public void toLow() {}

    public void toMid() {}
    public void toHigh() {}

    public void setTarget(int target) {}

    public int getReachedTarget () {return -1;}
    public void runUntilDone() {
        while (isRunning) run();
    }

    public void run() {
        run(0);
    }
    public void run(double power) {}

    public void testServo(double val) {}
    public boolean holding() {return true;}
}
