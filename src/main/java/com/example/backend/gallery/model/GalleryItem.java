package com.example.backend.gallery.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "gallery")
@Data @NoArgsConstructor @AllArgsConstructor
public class GalleryItem {
    @Id
    private  String id;
    private  String title;
    private  String category;
    private  String imageUrl;
    private Date date;
    private  String description;
}
