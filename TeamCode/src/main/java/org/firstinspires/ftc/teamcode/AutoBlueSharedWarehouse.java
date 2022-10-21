package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotor.RunMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@Autonomous(name="RedWarehouseVisionShared")
public class AutoBlueSharedWarehouse extends LinearOpMode {
    //
    DcMotor frontleft;
    DcMotor frontright;
    DcMotor backleft;
    DcMotor backright;
    DcMotor Spinner;
    DcMotor InandOut;
    DcMotorEx UpandDown;

    Servo grabber;

    ModernRoboticsI2cGyro gyro;


    //28 * 20 / (2ppi * 4.125)
    Double width = 16.0; //inches
    Integer cpr = 28; //counts per rotation
    Integer gearratio = 40;
    Double diameter = 4.125;
    Double cpi = (cpr * gearratio)/(Math.PI * diameter); //counts per inch, 28cpr * gear ratio / (2 * pi * diameter (in inches, in the center))
    Double bias = 0.8;//default 0.8
    Double meccyBias = 0.9;//change to adjust only strafing movement
    double amountError = 2;

    static final double HEADING_THRESHOLD = 1;      // As tight as we can make it with an integer gyro
    static final double P_TURN_COEFF = 0.1;     // Larger is more responsive, but also less stable
    static final double P_DRIVE_COEFF = 0.07;     // Larger is more responsive, but also less stable

    double DRIVE_SPEED = 0.4;
    double TURN_SPEED = 0.4;

    Double conversion = cpi * bias;
    Boolean exit = false;

    BNO055IMU imu;
    Orientation angles;
    Acceleration gravity;

    public void runOpMode() throws InterruptedException {

        //initGyro();

        frontleft = hardwareMap.dcMotor.get("frontleft");
        frontright = hardwareMap.dcMotor.get("frontright");
        backleft = hardwareMap.dcMotor.get("backleft");
        backright = hardwareMap.dcMotor.get("backright");
//        InandOut = hardwareMap.get(DcMotor.class, "InandOut");
//        UpandDown = hardwareMap.get(DcMotorEx.class, "UpandDown");
//        DcMotor[] motors = {frontleft, frontright, backleft, backright};
//        Spinner = hardwareMap.dcMotor.get("Car");
//        gyro = hardwareMap.get(ModernRoboticsI2cGyro.class, "Gyro");
//        grabber = hardwareMap.get(Servo.class, "Grabber");

        frontright.setDirection(DcMotorSimple.Direction.REVERSE);
        backright.setDirection(DcMotorSimple.Direction.REVERSE);

        InandOut.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        UpandDown.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        InandOut.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        UpandDown.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        Vision detector = new Vision(this);


        telemetry.addLine("Start Gyro");
        telemetry.update();
        gyro.calibrate();
        while (gyro.isCalibrating()) ;
        telemetry.addLine("Gyro Calibrated");
        telemetry.addData("Angle: ", gyro.getIntegratedZValue());
        telemetry.update();

        grabber.setPosition(1);

        waitForStart();

        telemetry.addData("Duck Left", detector.boxLeft);
        telemetry.addData("Duck Center", detector.boxCenter);
        telemetry.addData("Duck Right", detector.boxRight);

        telemetry.addData("leftduckdetected", detector.left_avg);
        telemetry.addData("leftduckdetected", detector.left_avg);
        telemetry.update();



    }
}