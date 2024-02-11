//package org.firstinspires.ftc.teamcode.autonomii_regionala;
//
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//
//import org.firstinspires.ftc.teamcode.components.Collector;
//import org.firstinspires.ftc.teamcode.components.LiftSystem;
//import org.firstinspires.ftc.teamcode.detection.DetectRedObj;
//import org.firstinspires.ftc.teamcode.utilities.camera;
//import org.firstinspires.ftc.teamcode.utilities.BasicAutonomy;
//import org.firstinspires.ftc.teamcode.utilities.Geometry;
//import org.openftc.easyopencv.OpenCvWebcam;
//
//@Autonomous(name = "Red_Far", group = "main")
//public class Red_Far extends BasicAutonomy {
//    private LiftSystem liftSystem;
//    private Collector collector;
//    OpenCvWebcam webcam;
//    DetectRedObj redPipeline = new DetectRedObj();
//    @Override
//    public void initialize() throws InterruptedException {
//        webcam = camera.createWithPipeline(hardwareMap, redPipeline);
//        liftSystem = new LiftSystem(hardwareMap);
//        collector = new Collector(hardwareMap);
//        collector.closeStackServo();
//        liftSystem.microInitPos();
//        liftSystem.flipInitPos();
//        liftSystem.angleInitPos();
//        super.initialize();
//    }
//
//    @Override
//    public void runCode() throws InterruptedException {
//        int tipAutonomie = redPipeline.gen_tip_autonomie();
//        roadRunner.setPrecisionMode(true);
//
//        liftSystem.microInitPos();
//
//        log("autonomie: " + tipAutonomie);
//        // moving the purple pixel
//        if (tipAutonomie == 0) {
//            walk(-30, 55);
//            walk(-2, 20);
//            walk(-2, 130);
//            //TODO: left
//        } else if (tipAutonomie == 1) {
//            walk(5, 65);
//            walk(-32, 46);
//            walk(-30, 120);
//            walk(-2, 130);
//            //TODO: middle
//        } else if (tipAutonomie == 2) {
//            walk(-2, 37);
//            walk(-30, 80, -Geometry.pi/2);
//            walk(-7, 80, -Geometry.pi/2);
//
//            walk(-2, 130);
//            //TODO: right
//        }
//
//        log("finished purple?");
//
//        // COLLECT
//        collector.closeStackServo();
//        walk(-49, 130, Geometry.pi/2);
//        collector.runModeCollect();
//        collector.openStackServo();
//        sleep(100);
//        walk(-25, 130, Geometry.pi/2);
//        collector.closeStackServo();
//        walk(-47,130, Geometry.pi/2);
//        collector.openStackServo();
//        log("Collected!");
//
//        // RUN
//        roadRunner.setPrecisionMode(false);
//        walk(165, 135, Geometry.pi/2);
//        collector.runModeOff();
//
//        // put
////        roadRunner.setPrecisionMode(true);
//        roadRunner.init(200, 70, 0, true, Geometry.pi/2, 1);
//        liftSystem.setTarget(525);
//        roadRunner.setPrecisionMode(true);
//
//        while (roadRunner.isRunning){
//            liftSystem.run(0);
//            roadRunner.run();
//        }
//
//        if (tipAutonomie == 0) {
//            walk(215, 65, Geometry.pi/2);
//        } else if (tipAutonomie == 1) {
//            //TODO:
//        } else if (tipAutonomie == 2) {
//            //TODO:
//        }
//        sleep(150);
//        liftSystem.flipActivePos();
//        liftSystem.angleActivePos();
//        sleep(1000);
//        liftSystem.microSecondPos();
//        sleep(1000);
//
//        // go back
//        liftSystem.angleInitPos();
//        liftSystem.flipInitPos();
//        roadRunner.setPrecisionMode(false);
//        roadRunner.init(165, 135, 0, true, -Geometry.pi/2, 1);
//        sleep(100); // just to be sure pls
//        liftSystem.toGround();
//        while(liftSystem.isRunning) liftSystem.run();
//        roadRunner.run();
//
//        while (roadRunner.isRunning) roadRunner.run();
//
//        walk(0, 135, -Geometry.pi/2);
//        roadRunner.setPrecisionMode(true);
//
//        // COLLECT
//        collector.closeStackServo();
//        walk(-47, 130, Geometry.pi/2);
//        collector.runModeCollect();
//        collector.openStackServo();
//        walk(-25, 130, Geometry.pi/2);
//        collector.closeStackServo();
//        walk(-47, 130, Geometry.pi/2);
//        collector.openStackServo();
//        log("Collected!");
//
//
//        sleep(15000);
//
//
//        // RUN
//        walk(165, 135, Geometry.pi/2);
//        collector.runModeOff();
//
//        // put
//        roadRunner.init(200, 70, 0, true, Geometry.pi/2, 1);
//        liftSystem.setTarget(525);
//
//        while (roadRunner.isRunning){
//            liftSystem.run(0);
//            roadRunner.run();
//        }
//
//        if (tipAutonomie == 0) {
//            walk(226, 65, Geometry.pi/2);
//        } else if (tipAutonomie == 1) {
//            //TODO:
//        } else if (tipAutonomie == 2) {
//            //TODO:
//        }
//        liftSystem.flipActivePos();
//        liftSystem.angleActivePos();
//        sleep(1000);
//        liftSystem.microSecondPos();
//        sleep(15000);
//
//
//
//        log("DONE");
//        liftSystem.angleInitPos();
//        liftSystem.flipInitPos();
//        sleep(200);
//        liftSystem.toGround();
//    }
//}
