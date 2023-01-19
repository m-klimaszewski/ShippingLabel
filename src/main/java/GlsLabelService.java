import org.opencv.core.Rect;

public class GlsLabelService extends AbstractLabelService{
    GlsLabelService(String path) {
        imgToCut = openCv.getImg(path);
        System.out.println(imgToCut);

        itemsCoords.put("sender", new Rect(620,480,200,550));
        itemsCoords.put("receiver", new Rect(64,700,462,200));
        //itemsCoords.put("phoneNumber", new Rect(512,220,352,105));
    }
}
