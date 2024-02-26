package org.firstinspires.ftc.teamcode.detection;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class DetectRedObj extends OpenCvPipeline {
    Mat mat = new Mat();
    private int tipAutonomie;
    public boolean initialized = false;
    /*
     * tipul de autonomie e urmatoru:
     * 0 - stanga
     * 1 - mijloc
     * 2 - dreapta
     */

    private static final Rect zonaMijloc = new Rect(
            new Point(19, 0),
            new Point(206, 40)
    );

    private static final Rect zonaDreapta = new Rect(
            new Point(235, 0),
            new Point(300, 80)
    );


    static double procentObiectPeZona = 0.1;

    @Override
    public Mat processFrame(Mat input) {
        initialized = true;
        Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HSV);
        //35, 85, 94 - default
        //43, 62, 100 - brightness 100
        //35, 100, 69 - brightness 0

        Scalar lowHSV = new Scalar(0, 40, 60);
        Scalar highHSV = new Scalar(80, 250, 255);

        Core.inRange(mat, lowHSV, highHSV, mat);

        Mat mijloc = mat.submat(zonaMijloc);
        Mat dreapta = mat.submat(zonaDreapta);

        double valMijloc = Core.sumElems(mijloc).val[0] / zonaMijloc.area() / 255;
        double valDreapta = Core.sumElems(dreapta).val[0] / zonaDreapta.area() / 255;

        mijloc.release();
        dreapta.release();

        tipAutonomie = 0; //* presupunem ca e stanga

        if (valMijloc > procentObiectPeZona) tipAutonomie = 1;
        else if (valDreapta > procentObiectPeZona + .2) tipAutonomie = 2;

        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_GRAY2RGB);

        Scalar nuExista = new Scalar(255, 0, 0);
        Scalar exista = new Scalar(0, 255, 0);

        Imgproc.rectangle(mat, zonaMijloc, tipAutonomie == 1 ? exista : nuExista);
        Imgproc.rectangle(mat, zonaDreapta, tipAutonomie == 2 ? exista : nuExista);
        return mat;
    }

    public int getTipAutonomie() {
        return tipAutonomie;
    }
}