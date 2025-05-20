package com.example.springbootbackend.models;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;

public class Invite {
    @Id
    private String id;

    private String email;
    private String token;
    private LocalDateTime sentAt;

    public Invite() {}

    public Invite(String email, String companyId, String token) {
        this.email = email;
        this.token = token;
        this.sentAt = LocalDateTime.now();
    }

    // Getters
    public String getEmail() { return email; }
    public String getToken() { return token; }
    public LocalDateTime getSentAt() { return sentAt; }

    // Setters
    public void setEmail(String newEmail) { this.email = newEmail; }
}
