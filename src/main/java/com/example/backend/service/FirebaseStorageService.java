package com.example.backend.service;

import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;


@Service
public class FirebaseStorageService {

    public String uploadFile(MultipartFile file) throws IOException {
        // Generate a unique filename
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        // Get the bucket reference
        Bucket bucket = StorageClient.getInstance().bucket();

        // Create the blob and upload the file
        Blob blob = bucket.create(fileName, file.getBytes(), file.getContentType());

        blob.createAcl(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER)); // Make it public
        // Return the public URL (make sure your bucket permissions allow this)
        return String.format("https://storage.googleapis.com/%s/%s", bucket.getName(), fileName);
    }

    public void deleteFile(String fileUrl) {
        try {
            String bucketName = StorageClient.getInstance().bucket().getName();
            String fileName = fileUrl.replace(String.format("https://storage.googleapis.com/%s/", bucketName), "");
            StorageClient.getInstance().bucket().get(fileName).delete();
        } catch (Exception e) {
            // Handle error or log it
        }
    }
}
