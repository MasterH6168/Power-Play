package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name="TeleOP")
public class teleOP extends OpMode {


    public DcMotor backleft;
    public DcMotor frontleft;
    public DcMotor backright;
    public DcMotor frontright;
    public DcMotor SpinnerMotor;
    public DcMotor InandOut;
    public DcMotor UpandDown;

    public Servo Grabber;

    public double speedMode = 1;

    public boolean xIsHeld = false;
    public boolean bIsHeld = false;
    public boolean dpadLeftIsHeld = false;
    public boolean dpadRightIsHeld = false;



    @Override
    public void init() {
        telemetry.clearAll();
        telemetry.addData("Status", "TeleOP Initialization In Progress");
        telemetry.update();

        //Hardware map
        backleft = hardwareMap.get(DcMotor.class, "Backleft");
        frontleft = hardwareMap.get(DcMotor.class, "Frontleft");
        backright = hardwareMap.get(DcMotor.class, "Backright");
        frontright = hardwareMap.get(DcMotor.class, "Frontright");
        SpinnerMotor = hardwareMap.get(DcMotor.class, "Car");
        InandOut = hardwareMap.get(DcMotor.class, "InandOut");
        UpandDown = hardwareMap.get(DcMotor.class, "UpandDown");
        Grabber = hardwareMap.get(Servo.class, "Grabber");


        backleft.setDirection(DcMotor.Direction.FORWARD);
        frontleft.setDirection(DcMotor.Direction.FORWARD);
        backright.setDirection(DcMotor.Direction.REVERSE);
        frontright.setDirection(DcMotor.Direction.REVERSE);
        SpinnerMotor.setDirection(DcMotor.Direction.FORWARD);
        InandOut.setDirection(DcMotor.Direction.FORWARD);
        UpandDown.setDirection(DcMotor.Direction.FORWARD);
        Grabber.setDirection(Servo.Direction.FORWARD);


        backleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        InandOut.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        UpandDown.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        backleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        InandOut.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        UpandDown.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        SpinnerMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        backleft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontleft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backright.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontright.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        InandOut.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        UpandDown.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        backleft.setPower(0);
        frontleft.setPower(0);
        backright.setPower(0);
        frontright.setPower(0);
        SpinnerMotor.setPower(0);
        InandOut.setPower(0);
        UpandDown.setPower(0);
        Grabber.setPosition(1);





        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
        telemetry.update();

    }


    @Override
    public void loop() {

        //Slow Mode Code for a and b keys
        if (gamepad1.a == true) {
            speedMode = .4;
        } else if (gamepad1.b == true) {
            speedMode = 1;
        }
        //Slow Mode Code for a and b keys




        //Slow Mode Code for bumpers
        if (gamepad1.right_bumper == true && speedMode > .2) {
            speedMode -= .05;
        } else if (gamepad1.right_trigger >= .5 && speedMode < 2) {
            speedMode += .05;
        }
        //Slow Mode Code for bumpers


        double stopBuffer = 0; //Not currently Implemented



        //Drive Train Code
        double forward = speedMode * Math.pow(gamepad1.left_stick_y, 3);
        double right = -speedMode * Math.pow(gamepad1.left_stick_x, 3);
        double turn = -speedMode * Math.pow(gamepad1.right_stick_x,3);

        double leftFrontPower = forward + right + turn;
        double leftBackPower = forward - right + turn;
        double rightFrontPower = forward - right - turn;
        double rightBackPower = forward + right - turn;
        double[] powers = {leftFrontPower, leftBackPower, rightFrontPower, rightBackPower};

        boolean needToScale = false;
        for (double power : powers){
            if(Math.abs(power) > 1){
                needToScale = true;
                break;
            }
        }
        if (needToScale){
            double greatest = 0;
            for (double power : powers){
                if (Math.abs(power) > greatest){
                    greatest = Math.abs(power);
                }
            }
            leftFrontPower /= greatest;
            leftBackPower /= greatest;
            rightFrontPower /= greatest;
            rightBackPower /= greatest;
        }

        boolean stop = true;
        for (double power : powers){
            if (Math.abs(power) > stopBuffer){
                stop = false;
                break;
            }
        }
        if (stop){
            leftFrontPower = 0;
            leftBackPower = 0;
            rightFrontPower = 0;
            rightBackPower = 0;
        }

        frontleft.setPower(leftFrontPower);
        backleft.setPower(leftBackPower);
        frontright.setPower(rightFrontPower);
        backright.setPower(rightBackPower);
        //Drive Train Code





        //Grabber Code for Rev Smart Servo
        if (gamepad2.dpad_left && !dpadLeftIsHeld) {
            dpadLeftIsHeld = true;
            Grabber.setPosition(1);

        } else if (gamepad2.dpad_right && !dpadRightIsHeld) {
            dpadRightIsHeld = true;
            Grabber.setPosition(.7);
        } else {
            //Grabber.setPosition(.5);  //If uncommented, this will make it so you need to hold down the grabber button to keep the grabber closed
        }
        if(!gamepad1.dpad_right){
            dpadRightIsHeld = false;
        }
        if(!gamepad2.dpad_left){
            dpadLeftIsHeld = false;
        }
        //Grabber Code for Rev Smart Servo




        //InandOut Code
        if (gamepad2.right_stick_x >= 0.3) {
            InandOut.setPower(1);
        } else if (gamepad2.right_stick_x <= -0.3) {
            InandOut.setPower(-1);
        }else{
            InandOut.setPower(0);
        }
        //InandOut Code




        //UpandDown Code
        if (gamepad2.left_stick_y >= 0.3) {
            UpandDown.setPower(1);
        } else if (gamepad2.left_stick_y <= -0.3) {
            UpandDown.setPower(-1);
        }else{
            UpandDown.setPower(0);
        }
        //UpandDown Code




        //Level 3= -700
        //Level 2= -400
        //Level 1= -150

        telemetry.addData("Lift Encoders: ", UpandDown.getCurrentPosition());
        telemetry.update();






        //Carosel Spinner Code
        if (gamepad2.left_bumper) {
            SpinnerMotor.setPower(-.6);
        }else if (gamepad2.right_bumper) {
            SpinnerMotor.setPower(.6);
        }else {
            SpinnerMotor.setPower(0);
        }
        //Carosel Spinner Code




        //Blue Carousel Button
        if (gamepad2.x  && !xIsHeld){
            xIsHeld = true;
            SpinnerMotor.setPower(-.55);
            double target = this.getRuntime() + 1;
            while(target > this.getRuntime());
            SpinnerMotor.setPower(-1);
            double target2 = this.getRuntime() + .1;
            while(target2 > this.getRuntime());
        }
        if(!gamepad2.x){
            xIsHeld = false;
        }
        //Blue Carousel Button




        //Red Carousel Button
        if (gamepad2.b  && !bIsHeld){
            bIsHeld = true;
            SpinnerMotor.setPower(.55);
            double target = this.getRuntime() + 1;
            while(target > this.getRuntime());
            SpinnerMotor.setPower(1);
            double target2 = this.getRuntime() + .1;
            while(target2 > this.getRuntime());
        }
        if(!gamepad2.b){
            bIsHeld = false;
        }
        //Red Carousel Button



    }
}
