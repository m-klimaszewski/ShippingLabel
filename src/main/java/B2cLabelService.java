import org.opencv.core.Rect;

public class B2cLabelService extends AbstractLabelService {
    B2cLabelService(String path) {
        imgToCut = openCv.getImg(path);
        providerName = "B2C";

        itemsCoords.put("sender", new Rect(40,420,390,215));
        itemsCoords.put("receiver", new Rect(40,618,390,215));
        itemsCoords.put("shippingMethod", new Rect(500,220,325,120));
    }
}
