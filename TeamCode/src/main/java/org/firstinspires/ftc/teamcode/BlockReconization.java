//package org.firstinspires.ftc.teamcode;
//
//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.hardware.HardwareMap;
//
//import org.firstinspires.ftc.robotcore.external.ClassFactory;
//import org.firstinspires.ftc.robotcore.external.Telemetry;
//import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
//import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
//import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
//import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
//import java.util.List;
//
//@TeleOp(name = "Concept: TensorFlow Object Detection", group = "Concept")
////@Disabled
//public class ConceptTensorFlowObjectDetection extends LinearOpMode {
//    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
//    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
//    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";
//    private static final String VUFORIA_KEY = "AVqo1bb/////AAABmVKiVARMvEt7t9+nrvcP4GQ364E8Re8prujYiXukla9CkvAhYd82qZigVI2LyXcgqS+/P21uz64+qkDt3/PyBNxTaziLm/fSKjSH/YIBwPUMqRXLsymB3ku/HPtFikIM8KQgTD3TzcEx7qQfikd5T9aHLwF2w/YBy9g7Cb/egNRQH1xMhO4tPnMu74U2iuMpxNIkf1p/Ek3W7nmuTSXtz66yA84Sknp+RlusehMB9yirtLggU3QxbwzQTmsG9OKAICyELhROsqen50XW4r1GrVl7YKLgyI5DOCUEpytl7kO9a/MWNDK5uZ+uYplfZjtmhm6xvQ7++OGSU331wZRAQvCxWfLEL4d0p+T/7LmxO0pj";
//    private VuforiaLocalizer vuforia;
//    private TFObjectDetector tfod;
//
//@Override
//public void runOpMode() {
//    // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that
//    // first.
////    initVuforia();
//
//    if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
//        initTfod();
//    } else {
//        telemetry.addData("Sorry!", "This device is not compatible with TFOD");
//    }
//
//    /** Wait for the game to begin */
//    telemetry.addData(">", "Press Play to start tracking");
//    telemetry.update();
//    waitForStart();
//
//    if (opModeIsActive()) {
//        /** Activate Tensor Flow Object Detection. */
//        if (tfod != null) {
//            tfod.activate();
//        }
//
//
////    public BlockReconization extends LinearOpMode {
////        private static final String TFOD_MODEL_ASSET = "Skystone.tflite";
////        private static final String LABEL_FIRST_ELEMENT = "Stone";
////        private static final String LABEL_SECOND_ELEMENT = "Skystone";
////        private static final String VUFORIA_KEY =
////                "AVqo1bb/////AAABmVKiVARMvEt7t9+nrvcP4GQ364E8Re8prujYiXukla9CkvAhYd82qZigVI2LyXcgqS+/P21uz64+qkDt3/PyBNxTaziLm/fSKjSH/YIBwPUMqRXLsymB3ku/HPtFikIM8KQgTD3TzcEx7qQfikd5T9aHLwF2w/YBy9g7Cb/egNRQH1xMhO4tPnMu74U2iuMpxNIkf1p/Ek3W7nmuTSXtz66yA84Sknp+RlusehMB9yirtLggU3QxbwzQTmsG9OKAICyELhROsqen50XW4r1GrVl7YKLgyI5DOCUEpytl7kO9a/MWNDK5uZ+uYplfZjtmhm6xvQ7++OGSU331wZRAQvCxWfLEL4d0p+T/7LmxO0pj";
////
////        private VuforiaLocalizer vuforia;
////        public TFObjectDetector tfod;
////
////
////        public BlockReconization(HardwareMap hardwareMap) {
////            initVuforia();
////
////            if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
////                initTfod(hardwareMap);
////            }
////        }
//
//        private void initVuforia() {
//            /*
//             * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
//             */
//            VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
//
//            parameters.vuforiaLicenseKey = VUFORIA_KEY;
//            parameters.cameraDirection = CameraDirection.BACK;
//
//            //  Instantiate the Vuforia engine
//            vuforia = ClassFactory.getInstance().createVuforia(parameters);
//
//            // Loading trackables is not necessary for the TensorFlow Object Detection engine.
//        }
//
//        private void initTfod(HardwareMap hardwareMap) {
//            int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
//                    "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
//            TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
//            tfodParameters.minimumConfidence = 0.8;
//            tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
//            tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
//        }
//
//        public void endDetect() {
//            tfod.deactivate();
//            tfod.shutdown();
//        }
//
//        public String startReconizing(Telemetry telemetry) {
//
//            /** Activate Tensor Flow Object Detection. */
//
//            if (opModeIsActive()) {
//                while (opModeIsActive()) {
//                    if (tfod != null) {
//                        // getUpdatedRecognitions() will return null if no new information is available since
//                        // the last time that call was made.
//                        List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
//                        if (updatedRecognitions != null) {
//                            telemetry.addData("# Object Detected", updatedRecognitions.size());
//                            // step through the list of recognitions and display boundary info.
//                            int i = 0;
//                            for (Recognition recognition : updatedRecognitions) {
//                                telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
//                                telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
//                                        recognition.getLeft(), recognition.getTop());
//                                telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
//                                        recognition.getRight(), recognition.getBottom());
//                            }
//                            telemetry.update();
//                        }
//                    }
//                }
//            }
//            return position;
//        }
//    }
//}
//}
//}