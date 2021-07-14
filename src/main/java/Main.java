import org.opencv.core.Core;
import java.io.IOException;
public class Main {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) throws IOException {
        Scanner_Lib scanner_lib = new Scanner_Lib();

        for (int x = 34; x<=40; x++){
            String numOfImg = String.valueOf(x);
            String path = "src/main/resources/sl ("+numOfImg+").jpg";
            scanner_lib.runApp(path);
            System.out.println(x);
        }


       // scanner_lib.runApp("src/main/resources/sl (33).jpg");

         System.out.println(Core.VERSION);
    }
}
