package com.example.manageasset.infrastructure.shared.configs;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import com.google.firebase.FirebaseApp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@Configuration
public class FirebaseStorageConfig {
    private static final String LOCATION_BUCKET = "upload-file-b718c.appspot.com";

    @Bean
    public Bucket getBucket() throws IOException {
        ClassPathResource serviceAccount = new ClassPathResource("service_account.json");

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount.getInputStream()))
                .setStorageBucket(LOCATION_BUCKET)
                .build();
        FirebaseApp.initializeApp(options);

        return StorageClient.getInstance().bucket();
    }

    public static URL getURL(Blob blob, String blobName) throws IOException {
        ClassPathResource serviceAccount = new ClassPathResource("service_account.json");

        return blob.getStorage().signUrl(BlobInfo.newBuilder(LOCATION_BUCKET,
                        blobName).build(),
                365, TimeUnit.DAYS,
                Storage.SignUrlOption.signWith(ServiceAccountCredentials.fromStream(serviceAccount.getInputStream())));
    }
}
