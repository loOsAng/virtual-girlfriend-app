package com.myproject.virtual_girlfriend_app.model; // 使用你的包名

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ChatMessage {
    // ... (内容同前一条回复)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long girlfriendId;

    private String sender;

    @Column(columnDefinition = "TEXT")
    private String message;

    private LocalDateTime timestamp;

    public ChatMessage() {}

    public ChatMessage(Long girlfriendId, String sender, String message, LocalDateTime timestamp) {
        this.girlfriendId = girlfriendId;
        this.sender = sender;
        this.message = message;
        this.timestamp = timestamp;
    }
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getGirlfriendId() { return girlfriendId; }
    public void setGirlfriendId(Long girlfriendId) { this.girlfriendId = girlfriendId; }
    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    @Override
    public String toString() { /* ... */ return "";}
}