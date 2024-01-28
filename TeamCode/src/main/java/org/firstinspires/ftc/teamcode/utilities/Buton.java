package org.firstinspires.ftc.teamcode.utilities;

public class Buton {
    boolean last; // last value
    boolean pressed;
    public Buton() { last = pressed = false; }

    public boolean update(boolean b) {
        if (last)
            pressed = false;
        else if (b)
            pressed = true;

        last = b;
        return pressed;
    }
}