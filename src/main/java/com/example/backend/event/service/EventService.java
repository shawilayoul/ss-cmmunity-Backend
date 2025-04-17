package com.example.backend.event.service;

import com.example.backend.event.model.Event;
import com.example.backend.event.repository.EventRepository;
import com.example.backend.service.FirebaseStorageService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final FirebaseStorageService firebaseStorageService;

    public EventService(EventRepository eventRepository, FirebaseStorageService firebaseStorageService) {
        this.eventRepository = eventRepository;
        this.firebaseStorageService = firebaseStorageService;
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Optional<Event> getEventById(String id) {
        return eventRepository.findById(id);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Event createEvent(
            @RequestPart("event") Event event,  // Spring will automatically deserialize
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile)
            throws IOException {
        
        // Debug: Check if imageFile is not null
        if (imageFile != null && !imageFile.isEmpty()) {
            // Upload file to Firebase and get URL
            String imageUrl = firebaseStorageService.uploadFile(imageFile);
            
            // Debug: Check the image URL returned by Firebase
            System.out.println("Image uploaded to Firebase, URL: " + imageUrl);
    
            if (imageUrl != null) {
                event.setImageUrl(imageUrl);
            } else {
                // Handle the case when the image URL is null (failed upload)
                System.out.println("Error: Image URL is null.");
            }
        } else {
            System.out.println("No image file uploaded.");
        }
    
        // Debug: Check the event object before saving
        System.out.println("Event to be saved: " + event);
        
        // Save the event to the repository
        return eventRepository.save(event);
    }
    
    public Event updateEvent(String id, Event event, MultipartFile newImageFile) throws IOException {
        // Get existing event
        Event existingEvent = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        // Handle image update
        if (newImageFile != null && !newImageFile.isEmpty()) {
            // Delete old image if exists
            if (existingEvent.getImageUrl() != null) {
                firebaseStorageService.deleteFile(existingEvent.getImageUrl());
            }
            // Upload new image
            String newImageUrl = firebaseStorageService.uploadFile(newImageFile);
            event.setImageUrl(newImageUrl);
        } else {
            // Keep the existing image if no new image is provided
            event.setImageUrl(existingEvent.getImageUrl());
        }

        event.setId(id);
        return eventRepository.save(event);
    }

    public void deleteEvent(String id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        // Delete image from Firebase if exists
        if (event.getImageUrl() != null) {
            firebaseStorageService.deleteFile(event.getImageUrl());
        }

        eventRepository.deleteById(id);
    }

    public Event save(Event event) {
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }
}