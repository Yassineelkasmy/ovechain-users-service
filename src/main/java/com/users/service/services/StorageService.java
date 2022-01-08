package com.users.service.services;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.ReadChannel;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;

@Service
public class StorageService {
    private Bucket bucket;
    StorageService() {
        this.firebaseInit();
        this.bucket = StorageClient.getInstance().bucket("overchain-72ddf.appspot.com");
    }

    public void downloadFile(String path ,String filename) throws IOException {
        Blob file = bucket.get(path+"/"+filename);

        ReadChannel reader = file.reader();

        InputStream inputStream = Channels.newInputStream(reader);

        byte[] content = null;

        content = IOUtils.toByteArray(inputStream);

        FileUtils.writeByteArrayToFile(new File("./" + filename), content);
    }




    private void firebaseInit() {
        InputStream inputStream = null;
        try {
            inputStream = new ClassPathResource("firebase_config.json").getInputStream();
        } catch (IOException e3) {
            e3.printStackTrace();
        }
        try {

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(inputStream))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
            System.out.println("Firebase Initialize");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
