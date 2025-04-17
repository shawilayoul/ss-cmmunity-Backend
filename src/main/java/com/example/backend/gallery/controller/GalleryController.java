package com.example.backend.gallery.controller;

import com.example.backend.gallery.model.GalleryItem;
import com.example.backend.gallery.service.GalleryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/gallery")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class GalleryController {

    private  final GalleryService galleryService;

    @GetMapping
    public ResponseEntity<List<GalleryItem>> getAllGalleryItems() {
        return ResponseEntity.ok(galleryService.getAllGalleryItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GalleryItem> getGalleryItemById(@PathVariable String id) {
        Optional<GalleryItem> gallery = galleryService.getGalleryItemById(id);
        return gallery.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<GalleryItem> createGalleryItem(@RequestParam String title,
    @RequestParam String category,
    @RequestParam String date ,
    @RequestParam String description,
    @RequestParam(required = false) MultipartFile imageFile )  throws IOException{
        GalleryItem item = new GalleryItem();
        item.setTitle(title);
        item.setCategory(category);
        item.setDate(null);
        item.setDescription(description);
        return ResponseEntity.ok(galleryService.createGalleryItem(item, imageFile));
    }
    @PutMapping("/{id}")
    public  ResponseEntity<GalleryItem> updateGalleryItem(@PathVariable String id, @RequestBody GalleryItem item){
        GalleryItem updatedItem = galleryService.updateGalleryItem(id,item);
        return  ResponseEntity.ok(updatedItem);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> deleteItem(@PathVariable String id){
        galleryService.deleteGalleryItem(id);
        return  ResponseEntity.noContent().build();
    }
}
