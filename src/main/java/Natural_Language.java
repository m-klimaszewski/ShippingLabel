import com.google.cloud.language.v1.*;
import providers.CredentialsProvider;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Natural_Language {
    public static Hashtable<String, ArrayList<String>> analyzeTextEntities(String text) throws Exception {

        try (LanguageServiceClient language = LanguageServiceClient.create(CredentialsProvider.getGoogleLss())) {
            Document doc = Document.newBuilder().setContent(text).setType(Document.Type.PLAIN_TEXT).build();
            AnalyzeEntitiesResponse response = language.analyzeEntities(doc);
            Hashtable<String, ArrayList<String>> hashTable = new Hashtable<>();

            for (int i = 0; i < response.getEntitiesCount(); i++) {
                Entity entity = response.getEntities(i);
                if (hashTable.containsKey(entity.getType().toString())) {
                    hashTable.get(entity.getType().toString()).add(entity.getName());
                } else {
                    ArrayList<String> names = new ArrayList<String>();
                    names.add(entity.getName());
                    hashTable.put(entity.getType().toString(), names);
                }
            }

            return hashTable;
        }
    }
}

