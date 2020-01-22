package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


/* TODO
 *   *1. Rider(DCMotor) -- analog, constant power with two opposite direction (gamepad1.dpad_up and gamepad1.dpad_down)
 *
 *    3. arm (servo) -- analog, (gamepad1.x and y)
 *
 *
 *  Hint: use boolean button for analog (small change per pressed dt)
 */

/* TODO
 *   control the wheels using pad
 *   (servo)catcher position gamepad1.y
 *   rider = left_trigger-right_trigger
 */

@TeleOp(name = "Wheel Base test", group = "Iterative Opmode")

public class WheelBaseTest_MANUAL extends OpMode {
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    private DcMotor LF, RB, RF, LB = null;

    private DcMotor rider = null;

    private boolean up = true;

    boolean changed = false, on = false;


    private Servo catcher = null;

    private double catcherPos = 0.5;

    private final double max_power = 0.7;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {

        LF = hardwareMap.get(DcMotor.class, "LF");
        RB = hardwareMap.get(DcMotor.class, "RB");
        RF = hardwareMap.get(DcMotor.class, "RF");
        LB = hardwareMap.get(DcMotor.class, "LB");

        rider = hardwareMap.get(DcMotor.class, "rider");


//        LF.setDirection(DcMotor.Direction.FORWARD);
//        LB.setDirection(DcMotor.Direction.FORWARD);
//        RF.setDirection(DcMotor.Direction.REVERSE);
//        RB.setDirection(DcMotor.Direction.REVERSE);
        LF.setDirection(DcMotor.Direction.REVERSE);
        LB.setDirection(DcMotor.Direction.REVERSE);
        RF.setDirection(DcMotor.Direction.FORWARD);
        RB.setDirection(DcMotor.Direction.FORWARD);

        rider.setDirection(DcMotor.Direction.FORWARD);


        LF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LB.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RB.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rider.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        catcher = hardwareMap.get(Servo.class, "catcher");

        catcher.setPosition(catcherPos);

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

        double riderPower = 0;


        LFPower = Range.clip(LFPower, -max_power, max_power);
        RFPower = Range.clip(RFPower, -max_power, max_power);
        LBPower = Range.clip(LBPower, -max_power, max_power);
        RBPower = Range.clip(RBPower, -max_power, max_power);

//        if(gamepad1.b) {
//            LFPower = 0.5;
//        }


        LF.setPower(LFPower);
        RF.setPower(RFPower);
        LB.setPower(LBPower);
        RB.setPower(RBPower);

        float LTRT = gamepad1.left_trigger - gamepad1.right_trigger;
        if (LTRT > 0) {
            riderPower += 1.0;
        } else if (LTRT < 0) {
            riderPower -= 1.0;
        } else {
            riderPower = 0;
        }

        riderPower = Range.clip(riderPower, -1.0, 1.0);

        rider.setPower(riderPower);


        if (up) {
            if (gamepad1.x) {

                catcherPos = 0;
                catcher.setPosition(catcherPos);
                if (catcher.getPosition() == 0) {
                    up = false;
                }
            }
        } else {
            if (gamepad1.y) {
                catcherPos = 0.65;
                catcher.setPosition(catcherPos);
                if (catcher.getPosition() == 0.65) {
                    up = true;
                }
            }
        }


        //catcherPos = Range.clip(catcherPos, 0.0, 1.0);


        //catcher.setPosition(catcherPos);


        telemetry.addData("Catcher Pos: ", catcher.getPosition());
        telemetry.addData("Gamepad: y", gamepad1.left_stick_y);
        telemetry.addData("Gamepad: X", gamepad1.left_stick_x);
        telemetry.addData("rider power:", riderPower);
        telemetry.addData("Leftpower", LFPower);
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