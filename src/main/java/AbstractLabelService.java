import org.opencv.core.Mat;
import org.opencv.core.Rect;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractLabelService {
    Map<String, Rect> itemsCoords = new HashMap<>();
    String providerName = "";
    Mat imgToCut;
    Map<String, String> identifiedLabels = new HashMap<>();
    Scanner_Lib openCv = new Scanner_Lib();
    String tmpImgPath = "src/main/resources/tmp.png";

    public Map<String, String> identifyLabels(){
        System.out.println(this.imgToCut.width());
        if (this.imgToCut.width() <= 800) {
            this.identifiedLabels.put("Identified provider", providerName);
            this.identifiedLabels.put("Resolution", "no valid resolution");
            return this.identifiedLabels;
        }
        this.itemsCoords.forEach((label, coords) -> {
            Mat cutedImg = new Mat(this.imgToCut, coords);
            openCv.saveToPng(cutedImg, this.tmpImgPath);
            Text_Detection detect_txt = new Text_Detection();
            String textDetected;
            try {
                textDetected = detect_txt.detectText(this.tmpImgPath).replace("\n", " ");
                Integer trimDataIndex = textDetected.indexOf("From");
                if (trimDataIndex != -1) {
                    textDetected = textDetected.substring(trimDataIndex, textDetected.length());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            this.identifiedLabels.put(label, textDetected);
        });
        this.identifiedLabels.put("Identified provider", providerName);
        return this.identifiedLabels;
    }
}
