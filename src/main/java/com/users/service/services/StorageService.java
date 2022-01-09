package com.users.service.services;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.ReadChannel;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.cloud.StorageClient;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;


@Service
public class StorageService {
    private Bucket bucket;
    private Firestore db;
    StorageService() {
        this.firebaseInit();
        this.bucket = StorageClient.getInstance().bucket("overchain-72ddf.appspot.com");
        this.db = FirestoreClient.getFirestore();
    }

    public void createFolder(String folderName) {
        try {
            File newDirectory = new File("./"+ folderName);

            FileUtils.forceMkdir(newDirectory);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void downloadFile(String path ,String filename, String destinationFolder) throws IOException {
        Blob file = this.bucket.get(path+"/"+filename);

        ReadChannel reader = file.reader();

        InputStream inputStream = Channels.newInputStream(reader);

        byte[] content = null;

        content = IOUtils.toByteArray(inputStream);

        FileUtils.writeByteArrayToFile(new File("./"+ destinationFolder + "/" + filename), content);
    }


    public void donwloadUserFolder(String uid) throws ExecutionException, InterruptedException, IOException {
        String[] folders = {"IDCARD-FRONT", "IDCARD-BACK", "TAXES-BILL"};
        this.createFolder(uid);
        this.createFolder(uid + "/properties");
        ApiFuture<DocumentSnapshot> userDocFuture = this.db.collection("users").document(uid).get();
        DocumentSnapshot userDoc = userDocFuture.get();
        for(String folder: folders) {
            String filename = userDoc.get(folder).toString();
            this.createFolder(uid+"/"+folder);
            String filePath = uid + "/" + folder;
            this.downloadFile(filePath,filename,uid + "/" + folder);

        }

    }

    public void downloadPropertyFolder(String uid, String code, int optionals) throws ExecutionException, InterruptedException, IOException {
        String[] folders = {"REGISTRATION-LICENSE", "FRONT-IMAGE", "TAXES-BILL"};
        ApiFuture<DocumentSnapshot> propertyDocFuture = this.db.collection("users").document(uid).collection("properties").document(code).get();
        DocumentSnapshot propertyDoc = propertyDocFuture.get();
        for(String folder:folders) {
            String filename = propertyDoc.get(folder).toString();
            String filePath = uid + "/properties/" + code + "/" + folder;
            this.downloadFile(filePath, filename, filePath);
        }

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
