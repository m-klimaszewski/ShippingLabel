import jdk.jfr.Threshold;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.highgui.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Core;
import org.opencv.objdetect.Objdetect;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Scanner_Lib  {

    public void displayImg(String path){
        Mat img = Imgcodecs.imread(path);
        HighGui.namedWindow("image", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("image", img);
        HighGui.waitKey();

    }
    public void grayScaleImg(String path) throws IOException {
        Mat src = Imgcodecs.imread(path);
        Mat gray = new Mat();
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_RGB2GRAY);

        Mat sobelx = new Mat();
        Imgproc.Sobel(gray,sobelx, CvType.CV_32F,1,0,-1);
        Mat sobely = new Mat();
        Imgproc.Sobel(gray,sobely, CvType.CV_32F,0,1,-1);
        Mat gradient = new Mat();
        Core.subtract(sobelx,sobely,gradient);
        Core.convertScaleAbs(gradient,gradient);
        Mat blur = new Mat();
        Imgproc.GaussianBlur(gradient,blur, new Size(9, 9), 0);
        Mat threshold = new Mat();
        Imgproc.threshold(blur, threshold,125,255,Imgproc.THRESH_BINARY);
        Mat closed = new Mat();
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(2, 2));
        Imgproc.morphologyEx(threshold, closed, Imgproc.MORPH_CLOSE,kernel );

        HighGui.imshow("xd",closed);

        HighGui.waitKey();

    }

}
