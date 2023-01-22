import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.highgui.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

import java.io.IOException;
import java.util.*;
import java.util.List;

public class Scanner_Lib {

    public void runApp(String path, String savePath) throws IOException {
        Mat inputImg = Imgcodecs.imread(path);

        Mat wrppedImg = waybillDetector(inputImg);
        saveToPng(wrppedImg, savePath);
    }

    public void saveToPng(Mat img, String path) {
        Imgcodecs.imwrite(path, img);
    }

    public Mat getImg(String path) {
        return Imgcodecs.imread(path);
    }


    public Mat waybillDetector(Mat img) throws IOException {

        // #1 preprocessing
        Mat gray = preprocessingImg(img);
        // #2 get contours of preprocessed img
        List<MatOfPoint> contours = getContour(gray);
        // #3 find biggest contour, sort maxarea corners
        List<MatOfPoint2f> biggestContour = biggestContour(contours);
        MatOfPoint2f sortedCorners = sortPoint(biggestContour);
        // #4 get outline of img
        MatOfPoint2f outline = getOutline(img);
        //transoform img
        Mat transformImg = Mat.zeros(img.size(), img.type());
        Mat perspectiveTransform = Imgproc.getPerspectiveTransform(sortedCorners, outline);
        Imgproc.warpPerspective(img, transformImg, perspectiveTransform, img.size());

        return transformImg;
    }

    public void showImg(String name, Mat img) {
        HighGui.imshow(name, img);
        HighGui.waitKey();
    }


    public List<MatOfPoint> getContour(Mat img) {
        //find contours
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(img, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_NONE);

        /*
        Drawing contours to display effect of preprocessing

        Mat drawingContours = Mat.zeros(gray.size(), CvType.CV_8U);
        Imgproc.drawContours(drawingContours, contours, -1, new Scalar(255), 2); */

        return contours;
    }

    public Mat preprocessingImg(Mat img) {
        resizeImg(img);
        Mat grayScaleImg = new Mat();
        //blur img
        Imgproc.medianBlur(img, gray, 9);
        //swap to gray scale
        Imgproc.cvtColor(gray, gray, Imgproc.COLOR_RGB2GRAY);
        //canny detector
        Imgproc.Canny(gray, gray, 85, 150);
        return grayScaleImg;
    }

    public MatOfPoint2f sortPoint(List<MatOfPoint2f> biggestContour) {
        //find of the center "mass" of our contour
        Moments moment = Imgproc.moments(biggestContour.get(0));
        int x = (int) (moment.get_m10() / moment.get_m00());
        int y = (int) (moment.get_m01() / moment.get_m00());
        double[] data;
        Point[] sortedPoints = new Point[4];
        int cnt = 0;
        for (int i = 0; i < biggestContour.get(0).rows(); i++) {
            data = biggestContour.get(0).get(i, 0);
            double dataX = data[0];
            double dataY = data[1];
            if (dataX < x && dataY < y) {
                sortedPoints[0] = new Point(dataX, dataY);
                cnt++;
            } else if (dataX > x && dataY < y) {
                sortedPoints[1] = new Point(dataX, dataY);
                cnt++;
            } else if (dataX < x && dataY > y) {
                sortedPoints[2] = new Point(dataX, dataY);
                cnt++;
            } else if (dataX > x && dataY > y) {
                sortedPoints[3] = new Point(dataX, dataY);
                cnt++;
            }
        }
        MatOfPoint2f corners = new MatOfPoint2f(
                sortedPoints[0],
                sortedPoints[1],
                sortedPoints[2],
                sortedPoints[3]);
        return corners;
    }

    //this function detect only 16:9 or 4:3 resolution!!!
    public Mat resizeImg(Mat img) {
        int height = img.height();
        int width = img.width();
        float propotion;
        boolean isHorizontal = true;
        if (height > width) {
            propotion = (float) height / width;
            isHorizontal = false;
        } else {
            propotion = (float) width / height;
        }

        if (Math.abs(propotion - 1.33) < 0.01) {
            if (isHorizontal) {
                Imgproc.resize(img, img, new Size(1152, 864));
            } else {
                Imgproc.resize(img, img, new Size(864, 1152));
            }
        } else {
            if (isHorizontal) {
                Imgproc.resize(img, img, new Size(1280, 720));
            } else {
                Imgproc.resize(img, img, new Size(720, 1280));
            }
        }


        return img;
    }

    //get lineframe of img
    public MatOfPoint2f getOutline(Mat img) {
        Point topLeft = new Point(0, 0);
        Point topRight = new Point(img.cols(), 0);
        Point bottomRight = new Point(img.cols(), img.rows());
        Point bottomLeft = new Point(0, img.rows());

        Point[] points = {topLeft, topRight, bottomLeft, bottomRight};
        MatOfPoint2f result = new MatOfPoint2f();
        result.fromArray(points);
        return result;
    }

    //find biggest of contours
    public List<MatOfPoint2f> biggestContour(List<MatOfPoint> contours) {
        List<MatOfPoint2f> biggestArea = new ArrayList<>();
        double maxArea = 0;

        for (MatOfPoint contour : contours) {
            //arcLenght calculates a contour perimeter or a curve length.
            MatOfPoint2f c2f = new MatOfPoint2f(contour.toArray());
            double peri = Imgproc.arcLength(c2f, true);
            // approximates a contour shape to antoher with less num of vertivles
            MatOfPoint2f approx = new MatOfPoint2f();
            Imgproc.approxPolyDP(c2f, approx, 0.02 * peri, true);
            Point[] vertex = approx.toArray();
            if ((Imgproc.contourArea(contour) > maxArea) && (vertex.length == 4)) {
                biggestArea.add(approx);
                maxArea = Imgproc.contourArea(contour);
            }
        }
        return biggestArea;
    }

    public Mat getSharpenImg(Mat img) {
        //Swap to gray scale
        Imgproc.cvtColor(img, img, Imgproc.COLOR_RGB2GRAY);
        //Imgproc.medianBlur(img, img,3);
        //Imgproc.threshold(img,img,0,255,Imgproc.THRESH_OTSU);

        return img;
    }


}
