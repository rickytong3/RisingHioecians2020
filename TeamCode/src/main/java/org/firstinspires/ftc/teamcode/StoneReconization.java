package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

public class StoneReconization {
    /**
     * This 2019-2020 OpMode illustrates the basics of using the TensorFlow Object Detection API to
     * determine the position of the Skystone game elements.
     * <p>
     * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
     * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list.
     * <p>
     * IMPORTANT: In order to use this OpMode, you need to obtain your own Vuforia license key as
     * is explained below.
     */
    private static final String TFOD_MODEL_ASSET = "Skystone.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Stone";
    private static final String LABEL_SECOND_ELEMENT = "Skystone";

    List<Recognition> updatedRecognitions = null;



    private static final String VUFORIA_KEY =
            "AROocgD/////AAABmYF6uNHYnUCBtJWncEUKbrCCJv88PHYGJAirCL3MgpJvPX3uSg0Jq7iLy5/QPGL/o8ISUDrKBOQWNl2waSAuXczbEA7NMMuBnkIIF450K2cShMSVbdJnpudYICiIfUx2OfHVaBVcAag5g60z/JWEt6i4I1Jo9XZ8h+IFCdy2SclquBuQdI4Iu9/uqM/uDYs+hjpqhZtzgOGoLE6He4pwWc/W/EZACj1R+lC/szathKG6SC/yh4fip7f1rkNPV62QmP0hvyGSYpRjCFLrmwJ70L/Bq5V5F9hu37yxar/qiq7ChQIqkYz0v8CvSOSk8YC9PftnvdShTmsnzQYdusF9eAF3KOjlbQuM+nzktAJN+MnS";

    public VuforiaLocalizer vuforia;

    public TFObjectDetector tfod;

    public StoneReconization(HardwareMap hardwareMap) {
        initVuforia();

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod(hardwareMap);
        }

        if (tfod != null) {
            tfod.activate();
        }

    }

    public void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the TensorFlow Object Detection engine.
    }

    public void initTfod(HardwareMap hardwareMap) {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minimumConfidence = 0.8;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
    }

    public void endDetect() {
        tfod.deactivate();
        tfod.shutdown();
    }


    public boolean startReconizing(Telemetry telemetry) {
        if (tfod != null) {
            // getUpdatedRecognitions() will return null if no new information is available since
            // the last time that call was made.
            updatedRecognitions = tfod.getUpdatedRecognitions();
            if (updatedRecognitions != null) {


                // step through the list of recognitions and display boundary info.
                    int i = 0;
                    for (Recognition recognition : updatedRecognitions) {
                        telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                        telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                                recognition.getLeft(), recognition.getTop());
                        telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                                recognition.getRight(), recognition.getBottom());
                    }
                    telemetry.update();
            }
        }

        return false;
    }


}


