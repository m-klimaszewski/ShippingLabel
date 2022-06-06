
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
import java.util.ArrayList;
import java.util.Hashtable;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;


public class Main {
    static {
        nu.pattern.OpenCV.loadLocally();
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Test");
        System.out.println(Core.VERSION);

        Scanner_Lib scanner_lib = new Scanner_Lib();

//        for (int x = 1; x <= 51; x++) {
//            String numOfImg = String.valueOf(x);
//            String path = "src/main/resources/sl (" + numOfImg + ").jpg";
//            String savePath = "src/main/resources/" + numOfImg + ".jpg";
//            scanner_lib.runApp(path, savePath);
//            System.out.println(x);
//        }

        String path = "src/main/resources/beforePreprocessing/52.jpg";
        String savePath = "src/main/resources/49.jpg";
        //scanner_lib.runApp(path, savePath);

        Text_Detection detect_txt = new Text_Detection();
        String textDetected = detect_txt.detectText("src/main/resources/49.jpg");

//        textDetected ="DHL Retoure Kontakt Absender: Tel.: 01752747357 Von: Alexandra Gehrmann Untere Bachstr. 43 94315 Straubing GERMANY 1 Kontakt Empfänger: 7 An: ZAK/CRB ZAK/CRB Rauschwalder Str. 41 02826 Görlitz GERMANY Anzahl 1/1 Gewicht: Datum: Abrechnungsnr.: 62862564170701 Referenznr.: [1/1] 8552 nr.: 196 76 81 418 90";
        Hashtable<String, ArrayList<String>> textEntities = Natural_Language.analyzeTextEntities(textDetected);

        String jsonPath = "src/tmp/entities.json";

        JSONObject json = new JSONObject();
        try {
            json.put("Detected text", textDetected);
            json.put("Entities", textEntities);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try (PrintWriter out = new PrintWriter(new FileWriter(jsonPath))) {
            out.write(json.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
