package org.firstinspires.ftc.teamcode.components;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.utilities.Servo;

/**
 * Airplane launching system
 * @author Raine
 */
public class Airplane {
    private Servo avion;
    public boolean released = false;
    public Airplane(HardwareMap hardwareMap) {
        avion = new Servo(hardwareMap, "avion");
        avion.setPowerRange(500, 2500);
    }
    public void holdPlane() {
        avion.setPosition(0.8);
        released = false;
    }
    public void releasePlane(){
        avion.setPosition(0.2);
        released = true;
    }
}
