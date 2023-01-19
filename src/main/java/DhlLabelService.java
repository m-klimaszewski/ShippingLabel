import org.opencv.core.Mat;
import org.opencv.core.Rect;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DhlLabelService extends AbstractLabelService {
    DhlLabelService(String path) {
        imgToCut = openCv.getImg(path);
        System.out.println(imgToCut);

        itemsCoords.put("sender", new Rect(205,140,255,140));
        itemsCoords.put("receiver", new Rect(186,286,297,140));
        itemsCoords.put("phoneNumber", new Rect(512,220,352,105));
    }

}
