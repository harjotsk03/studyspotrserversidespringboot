package com.example.springbootbackend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Document(collection = "studyspots")
public class StudySpot {
    @Id
    private String id;

    private String key;
    private String name;
    private String createdById;

    private boolean IDRequired;
    private boolean wifi;
    private boolean outlets;
    private boolean whiteboards;

    private Map<String, Object> location;
    private String openHours;
    private double rating;
    private int ratingCount;
    private String image;
    private String imageUrl;

    private Map<String, Object> spotLocationInfo;

    private List<String> comments = new ArrayList<>();


    public StudySpot() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCreatedById() { return createdById; }
    public void setCreatedById(String createdById) { this.createdById = createdById; }

    public boolean isIDRequired() { return IDRequired; }
    public void setIDRequired(boolean IDRequired) { this.IDRequired = IDRequired; }

    public boolean isWifi() { return wifi; }
    public void setWifi(boolean wifi) { this.wifi = wifi; }

    public boolean isOutlets() { return outlets; }
    public void setOutlets(boolean outlets) { this.outlets = outlets; }

    public boolean isWhiteboards() { return whiteboards; }
    public void setWhiteboards(boolean whiteboards) { this.whiteboards = whiteboards; }

    public Map<String, Object> getLocation() { return location; }
    public void setLocation(Map<String, Object> location) { this.location = location; }

    public String getOpenHours() { return openHours; }
    public void setOpenHours(String openHours) { this.openHours = openHours; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public int getRatingCount() { return ratingCount; }
    public void setRatingCount(int ratingCount) { this.ratingCount = ratingCount; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Map<String, Object> getSpotLocationInfo() { return spotLocationInfo; }
    public void setSpotLocationInfo(Map<String, Object> spotLocationInfo) { this.spotLocationInfo = spotLocationInfo; }

    public List<String> getComments() { return comments; }

    public void addComment(String comment) {
        if (!comments.contains(comment)) comments.add(comment);
    }

    public void removeComment(String comment) {
        comments.remove(comment);
    }
}
