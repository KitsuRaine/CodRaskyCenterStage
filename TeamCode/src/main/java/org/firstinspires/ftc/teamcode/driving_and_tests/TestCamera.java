package org.firstinspires.ftc.teamcode.driving_and_tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.utilities.camera;
import org.opencv.core.Mat;
import org.openftc.easyopencv.OpenCvPipeline;

//* Empty pipeline that spits out the input it gets
class TestPipeline extends OpenCvPipeline {
    @Override
    public Mat processFrame(Mat input) {
        return input;
    }
}

@Disabled
@Autonomous(group="test")
public class TestCamera extends LinearOpMode {
    TestPipeline pipeline = new TestPipeline();
    @Override
    public void runOpMode() throws InterruptedException {
        camera.createWithPipeline(hardwareMap, pipeline);
        waitForStart();
    }
}
