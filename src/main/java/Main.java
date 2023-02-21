
import java.util.*;


public class Main {
    static {
        nu.pattern.OpenCV.loadLocally();
    }
    public static String [] modes = {"test", "presentation"};
    public static void main(String[] args) throws Exception {

        App app = new App();
        String mode = modes[0];
        if (Objects.equals(mode, "presentation")) {
            App.presentationApp();
        } else if (Objects.equals(mode, "test")) {
            App.generateScoreApp();
        }
    }


}
