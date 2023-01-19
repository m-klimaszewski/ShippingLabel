
import com.google.cloud.language.v1.AnalyzeSentimentResponse;
import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.LanguageServiceClient;
import com.google.cloud.language.v1.Sentiment;

import com.google.zxing.NotFoundException;
import net.sourceforge.tess4j.TesseractException;
import org.opencv.core.Core;
import com.google.cloud.language.v1.Document.Type;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.PrintWriter;

import static com.lowagie.text.FontFactory.contains;


public class Main {
    static {
        nu.pattern.OpenCV.loadLocally();
    }
    public static String [] providers = {"dhl", "gls", "b2c" };
    public static void main(String[] args) throws Exception {
        System.out.println("System identyfikacji \n");
        System.out.println("Wgraj zdjęcie do katalogu src/main/resources/...\nPodaj nazwę pliku: ");
        String tmpPath = "src/main/resources/tmp1.png";
        String path = "src/main/resources/";
        Scanner input = new Scanner(System.in);
         path += input.nextLine();

        Scanner_Lib scanner_lib = new Scanner_Lib();



        scanner_lib.runApp(path, tmpPath);

        Text_Detection detect_txt = new Text_Detection();
        String textDetected = detect_txt.detectText(tmpPath).toLowerCase();


        String[] splitTextDetected = textDetected.split(" ");
        for(String text : splitTextDetected) {
            System.out.println(text);
            switch(text) {
                case "dhl":
                    DhlLabelService dhlService = new DhlLabelService("src/main/resources/1.jpg");
                    Map<String, String> detectedDhlLabels = dhlService.identifyLabels();
                    detectedDhlLabels.forEach((key, value) ->
                            System.out.println(key + ": " + value));
                    System.out.println("To dhl");
                    break;
                case "gls":
                    GlsLabelService glsService = new GlsLabelService("src/main/resources/wrappedImg/22.jpg");
                    Map<String, String> detectedGlsLabels = glsService.identifyLabels();
                    detectedGlsLabels.forEach((key, value) ->
                            System.out.println(key + ": " + value));
                    System.out.println("To gls");
                    break;
                case "b2c":
                    B2cLabelService b2cService = new B2cLabelService("src/main/resources/wrappedImg/40.jpg");
                    Map<String, String> detectedB2cLabels = b2cService.identifyLabels();
                    detectedB2cLabels.forEach((key, value) ->
                            System.out.println(key + ": " + value));
                    System.out.println("To b2c");
                    break;
            }
        }

//        Hashtable<String, ArrayList<String>> textEntities = Natural_Language.analyzeTextEntities(textDetected);
//
//        String jsonPath = "src/tmp/entities1.json";
//
//        JSONObject json = new JSONObject();
//        try {
//            json.put("Detected text", textDetected);
//            json.put("Entities", textEntities);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        try (PrintWriter out = new PrintWriter(new FileWriter(jsonPath))) {
//            out.write(json.toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
