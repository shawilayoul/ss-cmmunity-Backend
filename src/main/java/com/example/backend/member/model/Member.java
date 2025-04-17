package com.example.backend.member.model;

import lombok.*; // Lombok annotations
import org.springframework.data.annotation.Id; // MongoDB ID annotation
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
import java.util.List;

@Document(collection = "members")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
    @Id
    private String id;
    private String name;
    private String email;
    private String phone;
    private String role;
    private String region;
    private String profilePhoto;
    private List<String> skills;
    private Date joinedDate;
    private String bio;
    private Boolean isActive;
}

