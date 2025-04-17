package com.example.backend.event.model; // Change according to your package structure

import lombok.*; // Lombok annotations (Data, NoArgsConstructor, AllArgsConstructor, Builder)
import org.springframework.data.annotation.Id; // MongoDB ID annotation
import org.springframework.data.mongodb.core.mapping.Document; // MongoDB Document annotation
import java.util.Date; // Java Date class

@Document(collection = "events") // Specifies the MongoDB collection
@Data // Lombok: Generates Getters/Setters
@NoArgsConstructor // Lombok: Generates a no-args constructor
@AllArgsConstructor // Lombok: Generates an all-args constructor
@Builder // Lombok: Provides a builder pattern for object creation
public class Event {
    @Id
    private String id;
    private String title;
    private String description;
    private Date date;
    private Date endDate;
    private  String imageUrl;
    private String category;
    private Double price;
    private String organizer;
    private String contactEmail;
    private String registrationLink;
    private Integer attendees;
    private Boolean isFeatured;
}
