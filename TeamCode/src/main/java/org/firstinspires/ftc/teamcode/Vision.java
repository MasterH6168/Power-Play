package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;

public class Vision {
    OpMode opMode;
    OpenCvCamera camera;

    AddBoxesPipeline pipeline;

    private final Point centerBox_topLeft    = new Point(130,60);
    private final Point centerBox_bottomRight    = new Point(180, 110);

    private final Point leftBox_topLeft    = new Point(0,60);
    private final Point leftBox_bottomRight    = new Point(30, 110);

    private final Point rightBox_topLeft    = new Point(280,60);
    private final Point rightBox_bottomRight    = new Point(320, 110);

    Mat YCrCb = new Mat();
    Mat Cb = new Mat();
    Mat region_left_Cb, region_center_Cb, region_right_Cb;
    int left_avg, center_avg, right_avg;
    boolean boxLeft = false, boxRight = false, boxCenter = false;


    public Vision(OpMode op){

        opMode = op;

        int cameraMonitorViewId = opMode.hardwareMap.appContext.getResources().getIdentifier(
                "cameraMonitorViewId", "id", opMode.hardwareMap.appContext.getPackageName());

        camera = OpenCvCameraFactory.getInstance().createWebcam(opMode.hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        pipeline = new AddBoxesPipeline();
        camera.openCameraDevice();
        camera.setPipeline(pipeline);
        camera.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);

    }

    public void stopStreaming(){
        camera.stopStreaming();
    }

    //Add boxes to the image display
    class AddBoxesPipeline extends OpenCvPipeline {

        void inputToColors(Mat input)
        {
            Imgproc.cvtColor(input, YCrCb, Imgproc.COLOR_RGB2YCrCb);
            Core.extractChannel(YCrCb, Cb, 2);
        }

        @Override
        public void init(Mat firstFrame)
        {
            inputToColors(firstFrame);

            region_left_Cb = Cb.submat(new Rect(leftBox_topLeft,leftBox_bottomRight));
            region_center_Cb = Cb.submat(new Rect(centerBox_topLeft,centerBox_bottomRight));
            region_right_Cb = Cb.submat(new Rect(rightBox_topLeft,rightBox_bottomRight));
        }

        //This processes the visual output on the screen
        @Override
        public Mat processFrame(Mat input){

            inputToColors(input);

            left_avg = (int) Core.mean(region_left_Cb).val[0];
            center_avg = (int) Core.mean(region_center_Cb).val[0];
            right_avg = (int) Core.mean(region_right_Cb).val[0];

            //opMode.telemetry.addData("boxLeft: ", left_avg);
            if(left_avg <= center_avg && left_avg <= right_avg){
                boxLeft = true;
                boxCenter = false;
                boxRight = false;
            }

            //opMode.telemetry.addData("boxCenter: ", center_avg);
            else if(center_avg <= left_avg && center_avg <= right_avg){
                boxLeft = false;
                boxCenter = true;
                boxRight = false;
            }

            //opMode.telemetry.addData("boxRight: ", right_avg);
            //opMode.telemetry.update();
//            else if(right_avg <= left_avg && right_avg <= center_avg){
            else{
                boxLeft = false;
                boxCenter = false;
                boxRight = true;
            }

            int thickness = 3;
            Scalar leftColor = new Scalar(255,0,0);
            Scalar centerColor = new Scalar(255,0,0);
            Scalar rightColor = new Scalar(255,0,0);

            if (boxLeft){
                leftColor = new Scalar(0,255,0);
            }
            else if (boxCenter){
                centerColor = new Scalar(0,255,0);
            } else {
                rightColor = new Scalar(0,255,0);
            }

            Imgproc.rectangle(input, leftBox_topLeft, leftBox_bottomRight, leftColor, thickness);
            Imgproc.rectangle(input, centerBox_topLeft, centerBox_bottomRight, centerColor, thickness);
            Imgproc.rectangle(input, rightBox_topLeft, rightBox_bottomRight, rightColor, thickness);

            return input;
        }

    }
}