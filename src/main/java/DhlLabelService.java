import org.opencv.core.Rect;

public class DhlLabelService extends AbstractLabelService {
    DhlLabelService(String path) {
        imgToCut = openCv.getImg(path);
        providerName = "DHL";
        itemsCoords.put("sender", new Rect(30,130,400,170));
        itemsCoords.put("receiver", new Rect(20,300,400,170));
        itemsCoords.put("phoneNumber", new Rect(512,220,352,105));
    }

}
