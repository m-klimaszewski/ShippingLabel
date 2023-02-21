import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Scanner;


public class App {
    static String tmpPath = "src/main/resources/tmp1.png";
    public static void presentationApp() throws IOException {
        System.out.println("System identyfikacji \n");
        System.out.println("Wgraj zdjęcie do katalogu src/main/resources/...\nPodaj nazwę pliku: ");
        String path = "src/main/resources/";
        Scanner input = new Scanner(System.in);
        path += input.nextLine();

        Scanner_Lib scanner_lib = new Scanner_Lib();

        scanner_lib.transformImg(path, tmpPath);

        Text_Detection detect_txt = new Text_Detection();
        String textDetected = detect_txt.detectText(tmpPath).toLowerCase();
        String jsonPath = "src/tmp/entities1.json";
        String[] splitTextDetected = textDetected.split(" ");
        Map<String, String> detectedLabels = detectLabels(splitTextDetected);
        saveAnalyzedTextToJson(detectedLabels, jsonPath);

    }

    private static Map<String, String> detectLabels(String[] splitTextDetected) {
        for(String text : splitTextDetected) {
            switch (text) {
                case "dhl" -> {
                    DhlLabelService dhlService = new DhlLabelService(tmpPath);
                    return dhlService.identifyLabels();
                }
                case "gls" -> {
                    GlsLabelService glsService = new GlsLabelService(tmpPath);
                    return glsService.identifyLabels();
                }
                case "b2c" -> {
                    B2cLabelService b2cService = new B2cLabelService(tmpPath);
                    return b2cService.identifyLabels();
                }
            }
        }
        return null;
    }

    public  static void generateScoreApp() throws IOException {
        String basePath = "src/main/resources/";
        long startTime = System.nanoTime();

        for (int i  = 51; i > 0; i--) {
            String path = basePath + i + ".jpg";
            System.out.println(path);
            Scanner_Lib scanner_lib = new Scanner_Lib();
            scanner_lib.transformImg(path, tmpPath);
            Text_Detection detect_txt = new Text_Detection();
            String textDetected = detect_txt.detectText(tmpPath).toLowerCase();
            String[] splitTextDetected = textDetected.split(" ");
            Map<String, String> detectedLabels = detectLabels(splitTextDetected);
            String jsonPath = "src/tmp/img" + i + ".json";
            saveAnalyzedTextToJson(detectedLabels, jsonPath);
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println(duration);
        //153324629500 = 153.324 sec
    }

    public static void saveAnalyzedTextToJson(Map<String, String> analyzedText, String savePath) {
        JSONObject json = new JSONObject();
        if (analyzedText == null) {
            return;
        }
        try {
            analyzedText.forEach(json::put);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try (PrintWriter out = new PrintWriter(new FileWriter(savePath))) {
            out.write(json.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
