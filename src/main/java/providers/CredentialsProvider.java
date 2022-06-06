package providers;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.Credentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.language.v1.LanguageServiceSettings;
import com.google.cloud.vision.v1.ImageAnnotatorSettings;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CredentialsProvider {

    final static String GOOGLE_CREDENTIALS = "src/config/google-credentials.json";

    public static ImageAnnotatorSettings getGoogleIas() throws IOException {
        return ImageAnnotatorSettings.newBuilder().setCredentialsProvider(getBaseGoogleCredentials()).build();
    }

    public static LanguageServiceSettings getGoogleLss() throws IOException {
        return LanguageServiceSettings.newBuilder().setCredentialsProvider(getBaseGoogleCredentials()).build();
    }

    private static FixedCredentialsProvider getBaseGoogleCredentials() throws IOException {
        Credentials googleCredentials = ServiceAccountCredentials.fromStream(
                new FileInputStream(GOOGLE_CREDENTIALS));

        return FixedCredentialsProvider.create(googleCredentials);
    }
}
