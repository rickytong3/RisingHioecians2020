package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


@TeleOp(name="Wheel Base test", group="Iterative Opmode")

public class WheelBaseTest_MANUAL extends OpMode {
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    private DcMotor LF, RB, RF, LB = null;


    boolean catcher = false;
    private DcMotor catcherL, catcherR = null;

    private double max_power = 0.7;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {

        LF = hardwareMap.get(DcMotor.class, "LF");
        RB = hardwareMap.get(DcMotor.class, "RB");
        RF = hardwareMap.get(DcMotor.class, "RF");
        LB = hardwareMap.get(DcMotor.class, "LB");

        catcherL = hardwareMap.get(DcMotor.class, "catcherL");
        catcherR = hardwareMap.get(DcMotor.class, "catcherR");

        LF.setDirection(DcMotor.Direction.FORWARD);
        LB.setDirection(DcMotor.Direction.FORWARD);
        RF.setDirection(DcMotor.Direction.REVERSE);
        RB.setDirection(DcMotor.Direction.REVERSE);

        catcherL.setDirection(DcMotor.Direction.REVERSE);
        catcherR.setDirection(DcMotor.Direction.FORWARD);

        LF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        LF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        catcherL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        catcherR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        catcherL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        catcherR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        LF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LB.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RB.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime.reset();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {

        double LFPower = gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x;
        double LBPower = gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x;
        double RFPower = gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x;
        double RBPower = gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x;

        double catcherSpeed;



        if (gamepad1.y) {
            catcher = true;
        } else if (gamepad1.b){
            catcher = false;
        }

        if (catcher){
            catcherSpeed = 0.7;
        } else if (gamepad1.right_bumper) {
            catcherSpeed = -0.6;
        } else {
            catcherSpeed = (double) gamepad1.right_trigger;
        }


        LFPower = Range.clip(LFPower, -max_power, max_power);
        RFPower = Range.clip(RFPower, -max_power, max_power);
        LBPower = Range.clip(LBPower, -max_power, max_power);
        RBPower = Range.clip(RBPower, -max_power, max_power);

        LF.setPower(LFPower);
        RF.setPower(RFPower);
        LB.setPower(LBPower);
        RB.setPower(RBPower);

        catcherL.setPower(catcherSpeed);
        catcherR.setPower(catcherSpeed);


        telemetry.update();
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
        telemetry.addData("Remember: ", "Restart robot");
        telemetry.update();
    }

}