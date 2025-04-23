package com.example.backend.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void initialize() throws IOException {
        InputStream serviceAccount;
        
        // Check if the service account is available as an environment variable for production
        String firebaseConfigJson = System.getenv("FIREBASE_CONFIG_JSON");

        if (firebaseConfigJson != null && !firebaseConfigJson.isEmpty()) {
            // Use the environment variable (for cloud deployments like Render)
            serviceAccount = new ByteArrayInputStream(firebaseConfigJson.getBytes());
        } else {
            // Use the default classpath resource (for local development)
            serviceAccount = new ClassPathResource("serviceAccountKey.json").getInputStream();
        }

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setStorageBucket("sleekstyle-98723.appspot.com") // Replace with your bucket
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }
    }
}
