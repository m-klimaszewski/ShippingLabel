import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.multi.GenericMultipleBarcodeReader;
import com.google.zxing.multi.MultipleBarcodeReader;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.Word;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ocr_Scanner_Lib {
    public Tesseract tesseract() {
        Tesseract tess = new Tesseract();
        tess.setDatapath("src/main/resources/tessdata");
        tess.setOcrEngineMode(1);
        tess.setPageSegMode(1);
        tess.setHocr(true);
        tess.setLanguage("eng+fra+deu+pol");
        return tess;
    }

    public void runApp(String path) throws TesseractException, IOException, NotFoundException {
        BufferedImage bffImg = getBufferedImage(path);
        List<Word> words = getWordFromImg(bffImg);
        String[] wordsTable = getSplitTable(words);
        for (String word: wordsTable){
            System.out.println(word);
        }
        List<String> phoneNumbers = getPhoneNumber(wordsTable);
        List<String> mailAdress = getMailAddress(wordsTable);
       //List<String> barcodes = getBarcode(bffImg);
        /*
        for (String mail : barcodes) {
            System.out.println(mail);
        }

         */

        getAddress(wordsTable);


    }

    public BufferedImage getBufferedImage(String path) throws IOException {
        File img = new File(path);
        return ImageIO.read(img);
    }


    public List<Word> getWordFromImg(BufferedImage bffImg) {
        List<Word> result = tesseract().getWords(bffImg, 1);
        return result;
    }

    public String[] getSplitTable(List<Word> wordList) {
        String words = null;
        for (Word word : wordList) {
            String tmp = word.getText();
            if (!tmp.isEmpty()) {
                words += tmp.toUpperCase();
            }
        }

        return words.split("\\s+");
    }

    public List<String> getBarcode(BufferedImage bffImg) throws NotFoundException {
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer((new BufferedImageLuminanceSource(bffImg))));
        Reader reader = new MultiFormatReader();
        MultipleBarcodeReader bcReader = new GenericMultipleBarcodeReader(reader);
        Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();
        hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
        List<String> barcodes = new ArrayList<>();

        for (Result result : bcReader.decodeMultiple(bitmap, hints)) {
            barcodes.add(result.getText());
        }
        return barcodes;
    }

    private List<String> getCountriesName (){
        Locale.setDefault(new Locale("en"));
        String[] locales = Locale.getISOCountries();
        List<String> countries = new ArrayList<>();
        for (String countryCode : locales){
            Locale obj = new Locale("",countryCode);
            countries.add(obj.getDisplayName().toUpperCase());
        }
       // countries.forEach(System.out::println);
        return countries;
    }

    private List<String> getCountriesCode (){
        Locale.setDefault(new Locale("en"));
        String[] locales = Locale.getISOCountries();
        List<String> countryCodes = new ArrayList<>();
        for (String countryCode : locales){
            Locale obj = new Locale("",countryCode);
            countryCodes.add(obj.getCountry().toUpperCase(Locale.ROOT));
        }
       // countryCodes.forEach(System.out::println);
        return countryCodes;
    }
    public void getAddress(String[] words){
        List<String> countryCodes = getCountriesCode();
        List<String> countries = getCountriesName();
        int cnt = 0;
        int pos1 = 0;
        int pos2 = 0;
        boolean trigger = true;

        for(String word : words){
            if (countries.contains(word)||countryCodes.contains(word)){
                if (trigger){
                    pos1 = cnt;
                    trigger = false;
                } else {
                    pos2 = cnt;
                    break;
                }
            }
            cnt++;
        }

        System.out.println("Pos 1 kraju: " + words[pos1] + " \nPos 2 kraju: " + words[pos2]);
    }



    public List<String> getPhoneNumber(String[] words) {
        Pattern pattern = Pattern.compile("^(\\d{9}|\\d{11,13})$");
        //Pattern pattern1 = Pattern.compile("^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{2,3})[- .]?\\d{3}[- .]?\\d{4}$");
        List<String> phoneNumbers = new ArrayList<>();

        for (String word : words) {

            Matcher matcher = pattern.matcher(word);
            //Matcher matcher1 = pattern1.matcher(word);
            if (matcher.matches()) {
                phoneNumbers.add(word);
            }
        }
        if (phoneNumbers.size() >= 1) {
            return phoneNumbers;
        } else {
            phoneNumbers.add("Nie znaleziono numeru");
            return phoneNumbers;
        }
    }

    public List<String> getMailAddress(String[] words) {
        Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        List<String> mailAdress = new ArrayList<>();

        for (String word : words) {
            Matcher matcher = pattern.matcher(word);
            if (matcher.matches()) {
                mailAdress.add(word);
            }
        }
        if (mailAdress.size() >= 1) {
            return mailAdress;
        } else {
            mailAdress.add("Nie znaleziono adresu mail");
            return mailAdress;
        }
    }

    //////////////////////////////////////////// valid regex section
    public String[] validMail() {
        String[] mails = {"mara.rothe@gmx.de", "kr3b15szg6bmv7w@marketplace.amazon.de", "pfb9d63g3mb8n52@marketplace.amazon.de", "fibvi@icloud.com",
                "7affc8f543c9fd77e356@members.ebay.com", "wikunkel@aol.com", "Lena.namockel@outlook.de", "00ef0d7e3ec099fe8c3a@members.ebay.com",
                "m1m8b7wmrxzt4nh@marketplace.amazon.de", "qcpftlw2mmk1hdq1@marketplace.amazon.de", "philina1999@gmail.com", "adlk.com", "fds213,"
                , "lasdd$sdf.com"};
        return mails;
    }

    public void validateMailAdress() {
        Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        String[] mails = validMail();

        for (String mail : mails) {
            Matcher matcher = pattern.matcher(mail);
            if (matcher.matches()) {
                System.out.println("True mail " + mail);
            } else {
                System.out.println("Wrong mail " + mail);
            }
        }
    }


    public String[] validPhoneNumber() {
        String[] validPhoneNumbers = {"3332839838", "15124241260", "17678011160", "33624330356", "49880360633",
                "4965229330906", "49171945758", "3436232347", "4917647900312", "8246617", "49358732070", "+49 40 8509569",
                "436502844450", "7250331600", "491727836407", "7736516835", "17672894106", "41765817688", "33618432183", "1739082316",
                "306937285815", "491794141428", "491742014798", "491742014798", "497124583", "491629877181", "4960434742", "41289412285", "93589700656",
                "+49 731 7130716", "515121564156412653541", "45464"};
        return validPhoneNumbers;
    }


    public void validatePhoneNumber() {

        Pattern pattern = Pattern.compile("\\d{10}\\d{11,13}");
        Pattern pattern1 = Pattern.compile("^(\\+\\d{1,4}( )?)?((\\(\\d{3}\\))|\\d{2,3})[- .]?\\d{3}[- .]?\\d{4}$");
        String[] phoneNumbers = validPhoneNumber();

        for (String num : phoneNumbers) {
            Matcher matcher = pattern.matcher(num);
            Matcher matcher1 = pattern1.matcher(num);
            if (matcher.matches() || matcher1.matches()) {
                //System.out.println("True number " + num);
            } else {
                //System.out.println("Wrong number " + num);
            }
        }


    }
}
