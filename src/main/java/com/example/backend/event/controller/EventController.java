package com.example.backend.event.controller;

import com.example.backend.event.model.Event;
import com.example.backend.event.service.EventService;

import java.util.Date;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EventController {
    private final EventService eventService;

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable String id) {
        Optional<Event> event = eventService.getEventById(id);
        return event.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Event createEvent(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam String date,
            @RequestParam String endDate,
            @RequestParam String category,
            @RequestParam double price,
            @RequestParam String organizer,
            @RequestParam String contactEmail,
            @RequestParam String registrationLink,
            @RequestParam int attendees,
            @RequestParam boolean isFeatured,
            @RequestPart(required = false) MultipartFile imageFile
    ) throws IOException {
        Event event = new Event();
        event.setTitle(title);
        event.setDescription(description);
        event.setDate(Date.from(Instant.parse(date)));
        event.setEndDate(Date.from(Instant.parse(endDate)));        
        event.setCategory(category);
        event.setPrice(price);
        event.setOrganizer(organizer);
        event.setContactEmail(contactEmail);
        event.setRegistrationLink(registrationLink);
        event.setAttendees(attendees);
        event.setIsFeatured(isFeatured);
    
        return eventService.createEvent(event, imageFile);
    }
    

    @PutMapping("/{id}")
    public Event updateEvent(@PathVariable String id,
                             @RequestPart Event event,
                             @RequestPart(required = false) MultipartFile imageFile) throws IOException {
        return eventService.updateEvent(id, event, imageFile);
    }
    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> deleteEvent(@PathVariable String id){
        eventService.deleteEvent(id);
        return  ResponseEntity.noContent().build();
    }
}
