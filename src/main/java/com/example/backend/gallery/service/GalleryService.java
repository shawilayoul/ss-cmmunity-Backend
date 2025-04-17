package com.example.backend.gallery.service;

import com.example.backend.gallery.model.GalleryItem;
import com.example.backend.gallery.repository.GalleryRepository;
import com.example.backend.service.FirebaseStorageService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GalleryService {

    private  final GalleryRepository galleryRepository;
    private final FirebaseStorageService firebaseStorageService;
    public List<GalleryItem> getAllGalleryItems(){
        return galleryRepository.findAll();
    }

    public Optional<GalleryItem> getGalleryItemById(String id){
        return  galleryRepository.findById(id);
    }

    public  GalleryItem createGalleryItem(
        @RequestPart("gallery")GalleryItem item, @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) throws IOException{

            if( imageFile != null && !imageFile.isEmpty()){
                 // Debug: Check if imageFile is not null
        if (imageFile != null && !imageFile.isEmpty()) {
            // Upload file to Firebase and get URL
            String imageUrl = firebaseStorageService.uploadFile(imageFile);
            
            // Debug: Check the image URL returned by Firebase
            System.out.println("Image uploaded to Firebase, URL: " + imageUrl);
    
            if (imageUrl != null) {
                item.setImageUrl(imageUrl);
            } else {
                // Handle the case when the image URL is null (failed upload)
                System.out.println("Error: Image URL is null.");
            }
        } else {
            System.out.println("No image file uploaded.");
        }
    
        // Debug: Check the event object before saving
        System.out.println("Event to be saved: " + item);
        
            }
        return galleryRepository.save(item);
    }

    public  GalleryItem updateGalleryItem(String id, GalleryItem item){
        item.setId(id);
        return  galleryRepository.save(item);
    }

    public  void  deleteGalleryItem(String id){
        galleryRepository.deleteById(id);
    }
}
