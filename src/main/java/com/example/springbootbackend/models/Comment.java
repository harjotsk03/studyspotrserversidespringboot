package com.example.springbootbackend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "comments")
public class Comment {
    @Id
    private String id;

    private String userId;
    private String studySpotId;
    private String parentId; // null if top-level comment

    private String content;
    private Date createdAt;
    private Date updatedAt;

    private List<String> replies = new ArrayList<>(); // direct child comment IDs
    private List<String> likes = new ArrayList<>();   // userIds who liked
    private List<String> dislikes = new ArrayList<>(); // userIds who disliked

    private String path; // e.g., root/child1/child2 for nested threading

    private String status = "active"; // active, deleted, flagged

    public Comment() {}

    // ID
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    // User
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    // Study Spot
    public String getStudySpotId() { return studySpotId; }
    public void setStudySpotId(String studySpotId) { this.studySpotId = studySpotId; }

    // Parent
    public String getParentId() { return parentId; }
    public void setParentId(String parentId) { this.parentId = parentId; }

    // Content
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    // Created / Updated
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }

    // Replies
    public List<String> getReplies() { return replies; }
    public void addReply(String replyId) {
        if (!replies.contains(replyId)) replies.add(replyId);
    }
    public void removeReply(String replyId) {
        replies.remove(replyId);
    }

    // Likes
    public List<String> getLikes() { return likes; }
    public void addLike(String userId) {
        if (!likes.contains(userId)) likes.add(userId);
    }
    public void removeLike(String userId) {
        likes.remove(userId);
    }

    // Dislikes
    public List<String> getDislikes() { return dislikes; }
    public void addDislike(String userId) {
        if (!dislikes.contains(userId)) dislikes.add(userId);
    }
    public void removeDislike(String userId) {
        dislikes.remove(userId);
    }

    // Path
    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }

    // Status
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
} 
