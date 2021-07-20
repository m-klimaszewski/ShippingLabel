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
        ocr_scanner_lib.runApp("src/main/resources/tess1.jpg");




       // ocr_scanner_lib.validateMailAdress();
        /*
        for (int x = 46; x<=51; x++){
            String numOfImg = String.valueOf(x);
            String path = "src/main/resources/sl ("+numOfImg+").jpg";
            scanner_lib.runApp(path);
            System.out.println(x);
        }
         */

       // scanner_lib.runApp("src/main/resources/sl (1).jpg");

         System.out.println(Core.VERSION);
    }
}
