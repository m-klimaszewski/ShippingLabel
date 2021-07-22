import com.google.zxing.NotFoundException;
import net.sourceforge.tess4j.TesseractException;
import org.opencv.core.Core;
import java.io.IOException;
public class Main {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) throws IOException, TesseractException, NotFoundException {
        Scanner_Lib scanner_lib = new Scanner_Lib();
        Ocr_Scanner_Lib ocr_scanner_lib = new Ocr_Scanner_Lib();
        //ocr_scanner_lib.runApp("src/main/resources/tess2.jpg");



        for (int x = 1; x<=51; x++){
            String numOfImg = String.valueOf(x);
            String path = "src/main/resources/sl ("+numOfImg+").jpg";
            String savePath = "src/main/resources/"+numOfImg+".jpg";
            scanner_lib.runApp(path, savePath);
            System.out.println(x);
        }


      // scanner_lib.runApp("src/main/resources/sl ().jpg");

         System.out.println(Core.VERSION);
    }
}
