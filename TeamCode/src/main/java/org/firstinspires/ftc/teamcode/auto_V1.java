package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@Autonomous(name = "autoV1", group = "Linear Opmode")

public class auto_V1 extends LinearOpMode {


    private final double max_power = 0.5;
    BNO055IMU gyro;
    int state = 0;
    int goldPosition = -1;
    int count = 0;
    int block = 0;


    private double catcherPos = 0.5;
    private ElapsedTime runtime = new ElapsedTime();
    private AutoWheelBase autoWheelBase1;
    private DcMotor LF, LB, RF, RB, rider = null;
    private Servo catcher;

    public void resetEncoder() {
        LF.setMode(DcMotor.RunMode.RESET_ENCODERS);
        LB.setMode(DcMotor.RunMode.RESET_ENCODERS);
        RF.setMode(DcMotor.RunMode.RESET_ENCODERS);
        RB.setMode(DcMotor.RunMode.RESET_ENCODERS);


        LF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    @Override
    public void runOpMode() {

        //checker = new check (UpDown, inout, holder,hanger);

        LF = hardwareMap.get(DcMotor.class, "LF");
        RB = hardwareMap.get(DcMotor.class, "RB");
        RF = hardwareMap.get(DcMotor.class, "RF");
        LB = hardwareMap.get(DcMotor.class, "LB");
        catcher = hardwareMap.get(Servo.class, "catcher");

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = false;
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();


        gyro = hardwareMap.get(BNO055IMU.class, "imu");//hardwareMap.get(AdafruitBNO055IMU.class,"imu");
        gyro.initialize(parameters);
        LF.setDirection(DcMotor.Direction.FORWARD);
        LB.setDirection(DcMotor.Direction.FORWARD);
        RF.setDirection(DcMotor.Direction.REVERSE);
        RB.setDirection(DcMotor.Direction.REVERSE);

//        hanger.setPower(-1);


        StoneReconization stoneReconization = new StoneReconization(hardwareMap);
        autoWheelBase1 = new AutoWheelBase(LF, LB, RF, RB, gyro, 0.6, 0, 0, 0);
        catcher.setPosition(0);
        resetEncoder();


        telemetry.addData(">", "Press Play to start tracking");

        telemetry.update();
        state = 1;
        goldPosition = 2;
        waitForStart();


        if (opModeIsActive()) {


//            if (stoneReconization.tfod != null) {
//                stoneReconization.tfod.activate();
//            }
            while (opModeIsActive()) {
                autoWheelBase1.compute();
                telemetry.addData("base state", autoWheelBase1.state);
                telemetry.addData("state", state);
                telemetry.addData("s", autoWheelBase1.s);
                telemetry.addData("sn", autoWheelBase1.sn);
                telemetry.addData("z: ", autoWheelBase1.getZ());
                telemetry.addData("position: ", stoneReconization.startReconizing(telemetry));
                telemetry.addData("Position", goldPosition);
                telemetry.addData("LF", LF.getCurrentPosition());
                telemetry.addData("LB", LB.getCurrentPosition());
                telemetry.addData("RF", RF.getCurrentPosition());
                telemetry.addData("RB", RB.getCurrentPosition());
                telemetry.addData("y1", autoWheelBase1.y1);
                telemetry.addData("getY", autoWheelBase1.getY());

                telemetry.addData("catcher: ",catcher.getPosition());
                telemetry.addData("count: ",count);
                telemetry.addData("block: ",block);


                telemetry.update();
                state = 1;
                waitForStart();



                if (state == 1) {
                    resetEncoder();
                    autoWheelBase1.compute();
                    autoWheelBase1.forward(-133);
                    state = 2;
                } else if (state == 2) {
                    autoWheelBase1.forwardUpdate();
                    if (autoWheelBase1.state == 4) {
                        state = 3;
                    }
                } else if (state == 3) {
                    resetEncoder();
                    autoWheelBase1.compute();
                    autoWheelBase1.sideway(-333);
                    state = 4;
                } else if (state == 4) {
                    autoWheelBase1.sidewayUpdate();
                    if (autoWheelBase1.state == 4) {
                        count = 0;
                        state = 5;
                    }
                } else if (state == 5) {

                    resetEncoder();
                    autoWheelBase1.compute();
                    autoWheelBase1.sideway(-133);
                    state = 6;
                } else if (state == 6) {
                    autoWheelBase1.sidewayUpdate();
                    if (autoWheelBase1.state == 4) {
                        state = 7;
                    }
                } else if (state == 7) {
                    if (stoneReconization.startReconizing(telemetry) == false) {
                        state = 5;
                    } else {
                        state = 8;
                    }
                    count += 1;
                } else if (state == 8) {
                    //make up the servo
                    catcher.setPosition(0.65);

                    state = 9;
                } else if (state == 9) {
                    resetEncoder();
                    autoWheelBase1.compute();
                    autoWheelBase1.forward(-50);
                    state = 10;
                } else if (state == 10) {
                    autoWheelBase1.forwardUpdate();
                    if (autoWheelBase1.state == 4) {
                        state = 11;
                    }
                } else if (state == 11) {
                    //make the catcher down
                    catcher.setPosition(0);
                    state = 12;
                } else if (state == 12) {
                    resetEncoder();
                    autoWheelBase1.compute();
                    autoWheelBase1.forward(50);
                    state = 13;
                } else if (state == 13) {
                    autoWheelBase1.forwardUpdate();
                    if (autoWheelBase1.state == 4) {
                        state = 14;
                    }
                } else if (state == 14) {
                    //distance per block = constant*count(number of blocks)
                    //or just make the car on the wall violently
                    resetEncoder();
                    autoWheelBase1.compute();
                    autoWheelBase1.sideway((count * 13) + 200);
                    state = 15;
                } else if (state == 15) {
                    autoWheelBase1.sidewayUpdate();
                    if (autoWheelBase1.state == 4) {
                        state = 16;
                    }
                } else if (state == 16) {
                    resetEncoder();
                    autoWheelBase1.compute();
                    autoWheelBase1.forward(-15);
                    state = 17;
                } else if (state == 17) {
                    autoWheelBase1.forwardUpdate();
                    if (autoWheelBase1.state == 4) {
                        state = 18;
                    }
                } else if (state == 18) {
                    //make the catcher down(put down the sky-stone)
                    catcher.setPosition(0.5);
                    block += 1;
                    state = 19;
                } else if (state == 19) {
                    resetEncoder();
                    autoWheelBase1.compute();
                    autoWheelBase1.forward(15);
                    state = 20;
                } else if (state == 20) {
                    autoWheelBase1.forwardUpdate();
                    if (autoWheelBase1.state == 4) {
                        state = 21;
                    }
                } else if (state == 21) {
                    if (block == 1) {
                        state = 3;
                    } else if (block == 2) {
                        state = 22;
                    }
                } else if (state == 22) {
                    resetEncoder();
                    autoWheelBase1.compute();
                    autoWheelBase1.sideway(-100);
                    state = 23;
                } else if (state == 23) {
                    autoWheelBase1.sidewayUpdate();
                    if (autoWheelBase1.state == 4) {
                        state = 24;
                    }
                }

            }


        }

    }

}
