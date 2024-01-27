package org.firstinspires.ftc.teamcode.utilities;

public class Button {
    boolean last; // last value
    boolean pressed;
    public Button() { last = pressed = false; }

    public boolean update(boolean b) {
        if (last)
            pressed = false;
        else if (b)
            pressed = true;

        last = b;
        return pressed;
    }
}
