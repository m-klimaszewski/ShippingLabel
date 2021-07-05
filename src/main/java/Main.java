import org.opencv.core.Core;
import java.io.IOException;
public class Main {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) throws IOException {
        Scanner_Lib scanner_lib = new Scanner_Lib();
        //scanner_lib.displayImg("src/main/resources/img.jpg");
        scanner_lib.grayScaleImg("src/main/resources/sl1.jpg");
        System.out.println(Core.VERSION);
    }
}
