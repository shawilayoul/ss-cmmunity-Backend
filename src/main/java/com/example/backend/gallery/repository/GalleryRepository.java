package com.example.backend.gallery.repository;

import com.example.backend.gallery.model.GalleryItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GalleryRepository  extends MongoRepository<GalleryItem, String> {
    List<GalleryItem> findByCategory(String category);
}
