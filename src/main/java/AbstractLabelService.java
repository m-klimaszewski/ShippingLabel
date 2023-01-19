import org.opencv.core.Mat;
import org.opencv.core.Rect;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractLabelService {
    Map<String, Rect> itemsCoords = new HashMap<String, Rect>();
    Mat imgToCut;
    Map<String, String> identifiedLabels = new HashMap<String, String>();
    Scanner_Lib openCv = new Scanner_Lib();
    String tmpImgPath = "src/main/resources/tmp.png";

    public Map<String, String> identifyLabels() {
        this.itemsCoords.forEach((label, coords) -> {
            System.out.println(this.imgToCut);
            Mat cutedImg = new Mat(this.imgToCut, coords);
            openCv.saveToPng(cutedImg, this.tmpImgPath);
            Text_Detection detect_txt = new Text_Detection();
            String textDetected;
            try {
                textDetected = detect_txt.detectText(this.tmpImgPath).toLowerCase();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            this.identifiedLabels.put(label, textDetected);
        });

        return this.identifiedLabels;
    }
}
