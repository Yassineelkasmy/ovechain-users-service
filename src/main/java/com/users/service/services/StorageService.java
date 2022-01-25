package com.users.service.services;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.ReadChannel;
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

import java.io.*;
import java.nio.channels.Channels;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.ExecutionException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


@Service
public class StorageService {
    private final Bucket bucket;
    private final Firestore db;
    StorageService() {
        firebaseInit();
        bucket = StorageClient.getInstance().bucket("overchain-72ddf.appspot.com");
        db = FirestoreClient.getFirestore();
    }

    public void createFolder(String folderName) {
        try {
            File newDirectory = new File("./"+"/static/"+ folderName);

            FileUtils.forceMkdir(newDirectory);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void downloadFile(String path ,String filename, String destinationFolder) throws IOException {
        Blob file = bucket.get(path+"/"+filename);

        ReadChannel reader = file.reader();

        InputStream inputStream = Channels.newInputStream(reader);

        byte[] content = null;

        content = IOUtils.toByteArray(inputStream);

        FileUtils.writeByteArrayToFile(new File("./"+ destinationFolder + "/" + filename), content);
    }


    public void downloadUserFolder(String uid) throws ExecutionException, InterruptedException, IOException {
        String[] folders = {"IDCARD-FRONT", "IDCARD-BACK", "TAXES-BILL"};
        createFolder(uid);
        createFolder( "properties/" + uid);
        ApiFuture<DocumentSnapshot> userDocFuture = db.collection("users").document(uid).get();
        DocumentSnapshot userDoc = userDocFuture.get();
        for(String folder: folders) {
            String filename = userDoc.get(folder).toString();
            createFolder(uid+"/"+folder);
            String filePath = uid + "/" + folder;
            downloadFile(filePath,filename,"static/" + filePath);

        }

    }

    public void downloadPropertyFolder(String uid, String code, int optionals) throws ExecutionException, InterruptedException, IOException {
        String[] folders = {"REGISTRATION-LICENSE", "FRONT-IMAGE", "TAXES-BILL"};
        ApiFuture<DocumentSnapshot> propertyDocFuture = db.collection("users").document(uid).collection("properties").document(code).get();
        DocumentSnapshot propertyDoc = propertyDocFuture.get();
        for(String folder:folders) {
            String filename = propertyDoc.get(folder).toString();
            String filePath =  uid + "/properties/" + code + "/" + folder;
            downloadFile(filePath, filename, "static/properties/" + uid + "/" + code + "/" + folder);
        }

    }

   public void zipUserFolder(String uid) throws Exception {
        String folderToZIp = "./static/"+ uid;
        String zipName = "./static/"+uid+".zip";
        zipFolder(Paths.get(folderToZIp), Paths.get(zipName));
   }

   public void zipPropertyFolder(String uid, String propertyCode) throws Exception {
       String folderToZIp = "./static/properties/"+ uid + "/" + propertyCode;
       String zipName = "./static/properties/"+ uid + "/" + propertyCode +".zip";
       zipFolder(Paths.get(folderToZIp), Paths.get(zipName));
   }


   void  zipFolder(Path sourceFolderPath, Path zipPath) throws Exception {
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipPath.toFile()));
        Files.walkFileTree(sourceFolderPath, new SimpleFileVisitor<Path>() {
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                zos.putNextEntry(new ZipEntry(sourceFolderPath.relativize(file).toString()));
                Files.copy(file, zos);
                zos.closeEntry();
                return FileVisitResult.CONTINUE;
            }
        });
        zos.close();

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
