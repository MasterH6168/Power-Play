package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
//import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
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

@Autonomous(name="AutoBlueLeft")
public class AutoBlueLeft extends LinearOpMode {
    //
    DcMotor frontleft;
    DcMotor frontright;
    DcMotor backleft;
    DcMotor backright;
    DcMotor Spinner;
    DcMotor InandOut;
    DcMotorEx UpandDown;
//
//    Servo grabber;

    //ModernRoboticsI2cGyro gyro;


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
//        //gyro = hardwareMap.get(ModernRoboticsI2cGyro.class, "Gyro");
//        grabber = hardwareMap.get(Servo.class, "Grabber");

//        frontright.setDirection(DcMotorSimple.Direction.REVERSE);
//        backright.setDirection(DcMotorSimple.Direction.REVERSE);
//
//        InandOut.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        UpandDown.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//
//
//        InandOut.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        UpandDown.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        Vision detector = new Vision(this);


        telemetry.addLine("Start Gyro");
        telemetry.update();
        //gyro.calibrate();
        //while (gyro.isCalibrating()) ;
        telemetry.addLine("Gyro Calibrated");
        //telemetry.addData("Angle: ", gyro.getIntegratedZValue());
        telemetry.update();

//        grabber.setPosition(1);

        waitForStart();

        telemetry.addData("Red", detector.one);
        telemetry.addData("Green", detector.two);
        telemetry.addData("Blue", detector.red);

        if(detector.one = true){
            backleft.setPower(1);
        }

        else if(detector.two = true){
            frontleft.setPower(1);
        }
        else if(detector.three = true){
            backright.setPower(1);
        }





    }
//
//
//
//
//    public void liftup(double inches, double speed) {
//        int move =  -(int)(Math.round(inches*conversion));
//
//        UpandDown.setTargetPosition(UpandDown.getCurrentPosition() + move);
//        UpandDown.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        UpandDown.setPower(speed);
//    }
//
//    public void liftout(double inches, double speed) {
//        int move =  (int)(Math.round(inches*conversion));
//
//        InandOut.setTargetPosition(InandOut.getCurrentPosition() + move);
//        InandOut.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        InandOut.setPower(speed);
//    }
//
//    public void open(){
//        while(InandOut.isBusy() || UpandDown.isBusy()){
//        }
//        grabber.setPosition(.75);
//    }
//
//    public void close(){
//        grabber.setPosition(1);
//    }
//
//    public void turnWithGyro(double degrees, double speedDirection){
//        //<editor-fold desc="Initialize">
//        angles   = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
//        double yaw = -angles.firstAngle;//make this negative
////        telemetry.addData("Speed Direction", speedDirection);
////        telemetry.addData("Yaw", yaw);
////        telemetry.update();
////        //
////        telemetry.addData("stuff", speedDirection);
////        telemetry.update();
//        //
//        double first;
//        double second;
//        //</editor-fold>
//        //
//        if (speedDirection > 0){//set target positions
//            //<editor-fold desc="turn right">
//            if (degrees > 10){
//                first = (degrees - 10) + devertify(yaw);
//                second = degrees + devertify(yaw);
//            }else{
//                first = devertify(yaw);
//                second = degrees + devertify(yaw);
//            }
//            //</editor-fold>
//        }else{
//            //<editor-fold desc="turn left">
//            if (degrees > 10){
//                first = devertify(-(degrees - 10) + devertify(yaw));
//                second = devertify(-degrees + devertify(yaw));
//            }else{
//                first = devertify(yaw);
//                second = devertify(-degrees + devertify(yaw));
//            }
//            //
//            //</editor-fold>
//        }
//        //
//        //<editor-fold desc="Go to position">
//        Double firsta = convertify(first - 5);//175
//        Double firstb = convertify(first + 5);//-175
//        //
//        turnWithEncoder(speedDirection);
//        //
//        if (Math.abs(firsta - firstb) < 11) {
//            while (!(firsta < yaw && yaw < firstb) && opModeIsActive()) {//within range?
//                angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
//                gravity = imu.getGravity();
//                yaw = -angles.firstAngle;
////                telemetry.addData("Position", yaw);
////                telemetry.addData("first before", first);
////                telemetry.addData("first after", convertify(first));
//                //telemetry.update();
//            }
//        }else{
//            //
//            while (!((firsta < yaw && yaw < 180) || (-180 < yaw && yaw < firstb)) && opModeIsActive()) {//within range?
//                angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
//                gravity = imu.getGravity();
//                yaw = -angles.firstAngle;
////                telemetry.addData("Position", yaw);
////                telemetry.addData("first before", first);
////                telemetry.addData("first after", convertify(first));
//                //telemetry.update();
//            }
//        }
//        //
//        Double seconda = convertify(second - 5);//175
//        Double secondb = convertify(second + 5);//-175
//        //
//        turnWithEncoder(speedDirection / 3);
//        //
//        if (Math.abs(seconda - secondb) < 11) {
//            while (!(seconda < yaw && yaw < secondb) && opModeIsActive()) {//within range?
//                angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
//                gravity = imu.getGravity();
//                yaw = -angles.firstAngle;
////                telemetry.addData("Position", yaw);
////                telemetry.addData("second before", second);
////                telemetry.addData("second after", convertify(second));
//                //telemetry.update();
//            }
//            while (!((seconda < yaw && yaw < 180) || (-180 < yaw && yaw < secondb)) && opModeIsActive()) {//within range?
//                angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
//                gravity = imu.getGravity();
//                yaw = -angles.firstAngle;
//                //telemetry.addData("Position", yaw);
//                //telemetry.addData("second before", second);
//                //telemetry.addData("second after", convertify(second));
//                //telemetry.update();
//            }
//            frontleft.setPower(0);
//            frontright.setPower(0);
//            backleft.setPower(0);
//            backright.setPower(0);
//        }
//        //</editor-fold>
//        //
//        frontleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        frontright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        backleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        backright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        frontleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        frontright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        backleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        backright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//    }
//
//    public void strafeToPosition(double inches, double speed){
//        //
//        int move = (int)(Math.round(inches * cpi * meccyBias));
//        //
//        backleft.setTargetPosition(backleft.getCurrentPosition() - move);
//        frontleft.setTargetPosition(frontleft.getCurrentPosition() + move);
//        backright.setTargetPosition(backright.getCurrentPosition() + move);
//        frontright.setTargetPosition(frontright.getCurrentPosition() - move);
//        //
//        frontleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        frontright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        backleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        backright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        //
//        frontleft.setPower(speed);
//        backleft.setPower(speed);
//        frontright.setPower(speed);
//        backright.setPower(speed);
//        //
//        while (frontleft.isBusy() && frontright.isBusy() && backleft.isBusy() && backright.isBusy()){}
//        frontright.setPower(0);
//        frontleft.setPower(0);
//        backright.setPower(0);
//        backleft.setPower(0);
//        return;
//    }
//
//    public double devertify(double degrees){
//        if (degrees < 0){
//            degrees = degrees + 360;
//        }
//        return degrees;
//    }
//
//    public double convertify(double degrees){
//        if (degrees > 179){
//            degrees = -(360 - degrees);
//        } else if(degrees < -180){
//            degrees = 360 + degrees;
//        } else if(degrees > 360){
//            degrees = degrees - 360;
//        }
//        return degrees;
//    }
//
//    public void initGyro(){
//        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
//        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
//        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
//        //parameters.calibrationDataFile = "GyroCal.json"; // see the calibration sample opmode
//        parameters.loggingEnabled      = true;
//        parameters.loggingTag          = "IMU";
//        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
//        //
//        imu = hardwareMap.get(BNO055IMU.class, "imu");
//        imu.initialize(parameters);
//    }
//
//    public void turnWithEncoder(double input){
//        frontleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        backleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        frontright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        backright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        //
//        frontleft.setPower(input);
//        backleft.setPower(input);
//        frontright.setPower(-input);
//        backright.setPower(-input);
//    }
//
//    public void gyroDrive(double speed,
//                          double frontLeftInches, double frontRightInches, double backLeftInches,
//                          double backRightInches,
//                          double angle){
//
//        int newFrontLeftTarget;
//        int newFrontRightTarget;
//        int newBackLeftTarget;
//        int newBackRightTarget;
//
//        double HalfMaxOne;
//        double HalfMaxTwo;
//
//        double max;
//
//        double error;
//        double steer;
//        double frontLeftSpeed;
//        double frontRightSpeed;
//        double backLeftSpeed;
//        double backRightSpeed;
//
//        double ErrorAmount;
//        boolean goodEnough = false;
//
//        // Ensure that the opmode is still active
//        if (opModeIsActive()) {
//
//            // Determine new target position, and pass to motor controller
//            newFrontLeftTarget = frontleft.getCurrentPosition() + (int) (frontLeftInches * cpi);
//            newFrontRightTarget = frontright.getCurrentPosition() + (int) (frontRightInches * cpi);
//            newBackLeftTarget = backleft.getCurrentPosition() + (int) (backLeftInches * cpi);
//            newBackRightTarget = backright.getCurrentPosition() + (int) (backRightInches * cpi);
//
//
//            // Set Target and Turn On RUN_TO_POSITION
//            frontleft.setTargetPosition(newFrontLeftTarget);
//            frontright.setTargetPosition(newFrontRightTarget);
//            backleft.setTargetPosition(newBackLeftTarget);
//            backright.setTargetPosition(newBackRightTarget);
//
//            frontleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            frontright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            backleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            backright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//            // start motion.
//            speed = Range.clip(Math.abs(speed), 0.0, 1.0);
//            frontleft.setPower(Math.abs(speed));
//            frontright.setPower(Math.abs(speed));
//            backleft.setPower(Math.abs(speed));
//            backright.setPower(Math.abs(speed));
//            // keep looping while we are still active, and BOTH motors are running.
//            while (opModeIsActive() &&
//                    ((frontleft.isBusy() && frontright.isBusy()) && (backleft.isBusy() && backright.isBusy())) && !goodEnough) {
//
//
//                // adjust relative speed based on heading error.
//                error = getError(angle);
//                steer = getSteer(error, P_DRIVE_COEFF);
//
//                // if driving in reverse, the motor correction also needs to be reversed
//                if (frontLeftInches < 0 && frontRightInches < 0 && backLeftInches < 0 && backRightInches < 0)
//                    steer *= -1.0;
//
//                frontLeftSpeed = speed - steer;
//                backLeftSpeed = speed - steer;
//                backRightSpeed = speed + steer;
//                frontRightSpeed = speed + steer;
//
//                // Normalize speeds if either one exceeds +/- 1.0;
//                HalfMaxOne = Math.max(Math.abs(frontLeftSpeed), Math.abs(backLeftSpeed));
//                HalfMaxTwo = Math.max(Math.abs(frontRightSpeed), Math.abs(backRightSpeed));
//                max = Math.max(Math.abs(HalfMaxOne), Math.abs(HalfMaxTwo));
//                if (max > 1.0) {
//                    frontLeftSpeed /= max;
//                    frontRightSpeed /= max;
//                    backLeftSpeed /= max;
//                    backRightSpeed /= max;
//                }
//
//                frontleft.setPower(frontLeftSpeed);
//                frontright.setPower(frontRightSpeed);
//                backleft.setPower(backLeftSpeed);
//                backright.setPower(backRightSpeed);
//
//                // Display drive status for the driver.
////                telemetry.addData("Err/St", "%5.1f/%5.1f", error, steer);
////                telemetry.addData("Target", "%7d:%7d", newBackLeftTarget, newBackRightTarget, newFrontLeftTarget, newFrontRightTarget);
////                telemetry.addData("Actual", "%7d:%7d", backleft.getCurrentPosition(), backright.getCurrentPosition(), frontleft.getCurrentPosition(), frontright.getCurrentPosition());
////                telemetry.addData("Speed", "%5.2f:%5.2f", backLeftSpeed, backRightSpeed, frontLeftSpeed, frontRightSpeed);
////                telemetry.update();
//
//                ErrorAmount = ((Math.abs(((newBackLeftTarget) - (backleft.getCurrentPosition())))
//                        + (Math.abs(((newFrontLeftTarget) - (frontleft.getCurrentPosition()))))
//                        + (Math.abs((newBackRightTarget) - (backright.getCurrentPosition())))
//                        + (Math.abs(((newFrontRightTarget) - (frontright.getCurrentPosition()))))) / cpi);
//                if (ErrorAmount < amountError) {
//                    goodEnough = true;
//                }
//            }
//
//            // Stop all motion;
//            frontleft.setPower(0);
//            frontright.setPower(0);
//            backleft.setPower(0);
//            backright.setPower(0);
//
//            // Turn off RUN_TO_POSITION
//            frontleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//            frontright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//            backleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//            backright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        }
//    }
//
//    public void gyroTurn ( double speed, double angle){
//        frontleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        backleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        frontright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        backright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        // keep looping while we are still active, and not on heading.
//        while (opModeIsActive() && !onHeading(speed, angle, P_TURN_COEFF)) {
//            // Update telemetry & Allow time for other processes to run.
//            telemetry.update();
//        }
//        frontleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        backleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        frontright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        backright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//    }
//
//    public void gyroHold ( double speed, double angle, double holdTime){
//
//        ElapsedTime holdTimer = new ElapsedTime();
//
//        // keep looping while we have time remaining.
//        holdTimer.reset();
//        while (opModeIsActive() && (holdTimer.time() < holdTime)) {
//            // Update telemetry & Allow time for other processes to run.
//            onHeading(speed, angle, P_TURN_COEFF);
//            telemetry.update();
//
//
//        }
//
//        // Stop all motion;
//        frontleft.setPower(0);
//        backleft.setPower(0);
//        backright.setPower(0);
//        frontright.setPower(0);
//    }
//
//    boolean onHeading ( double speed, double angle, double PCoeff){
//        double error;
//        double steer;
//        boolean onTarget = false;
//        double leftSpeed;
//        double rightSpeed;
//
//        // determine turn power based on +/- error
//        error = getError(angle);
//
//        if (Math.abs(error) <= HEADING_THRESHOLD) {
//            steer = 0.0;
//            leftSpeed = 0.0;
//            rightSpeed = 0.0;
//            onTarget = true;
//        } else {
//            steer = getSteer(error, PCoeff);
//            rightSpeed = speed * steer;
//            leftSpeed = -rightSpeed;
//        }
//
//        // Send desired speeds to motors.
//        frontleft.setPower(leftSpeed);
//        backleft.setPower(leftSpeed);
//        backright.setPower(rightSpeed);
//        frontright.setPower(rightSpeed);
//
//        // Display it for the driver.
////        telemetry.addData("Target", "%5.2f", angle);
////        telemetry.addData("Err/St", "%5.2f/%5.2f", error, steer);
////        telemetry.addData("Speed.", "%5.2f:%5.2f", leftSpeed, rightSpeed);
//
//        return onTarget;
//    }
//
//    public double getError ( double targetAngle){
//
//        double robotError;
//
//        // calculate error in -179 to +180 range  (
//        robotError = targetAngle - gyro.getHeading();
//        while (robotError > 180) robotError -= 360;
//        while (robotError <= -180) robotError += 360;
//        return -robotError;
//    }
//
//    public double getSteer ( double error, double PCoeff){
//        return Range.clip(error * PCoeff, -DRIVE_SPEED, 1);
//    }
}