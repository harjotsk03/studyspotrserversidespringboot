package com.example.springbootbackend.security;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;

import com.example.springbootbackend.models.User;

public class UserDTO {
    @Id
    private String id;
    private String name;
    private String email;
    private String username;
    private String profilePhoto;
    private Date createdAt;
    private boolean isEmailVerified;
    private String authProvider;
    private List<String> roles;
    private Date lastLogin;

    private String referalLink;
    private List<String> savedLocations;
    private List<String> createdLocations;
    private List<String> referredUsers;

    private Integer activePoints;
    private Integer totalPoints;

    private List<String> badges;
    private String rank;
    private List<Map<String, Object>> visitHistory;

    private List<String> feedbackGiven;
    private List<String> searchHistory;
    private List<String> notifications;

    private boolean isPremiumUser;
    private String subscriptionPlan;
    private List<String> paymentMethods;

    private List<String> followers;
    private List<String> following;
    private String bio;
    private String school;
    private String program;
    private String preferredStudyTimes;

    private String location;
    private Map<String, Object> settings;
    private Date updatedAt;
    private Date deletedAt;

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.profilePhoto = user.getProfilePhoto();
        this.createdAt = user.getCreatedAt();
        this.isEmailVerified = user.isEmailVerified();
        this.authProvider = user.getAuthProvider();
        this.roles = user.getRoles();
        this.lastLogin = user.getLastLogin();

        this.referalLink = user.getReferalLink();
        this.savedLocations = user.getSavedLocations();
        this.createdLocations = user.getCreatedLocations();
        this.referredUsers = user.getReferredUsers();

        this.activePoints = user.getActivePoints();
        this.totalPoints = user.getTotalPoints();

        this.badges = user.getBadges();
        this.rank = user.getRank();
        this.visitHistory = user.getVisitHistory();

        this.feedbackGiven = user.getFeedbackGiven();
        this.searchHistory = user.getSearchHistory();
        this.notifications = user.getNotifications();

        this.isPremiumUser = user.isPremiumUser();
        this.subscriptionPlan = user.getSubscriptionPlan();
        this.paymentMethods = user.getPaymentMethods();

        this.followers = user.getFollowers();
        this.following = user.getFollowing();
        this.bio = user.getBio();
        this.school = user.getSchool();
        this.program = user.getProgram();
        this.preferredStudyTimes = user.getPreferredStudyTimes();

        this.location = user.getLocation();
        this.settings = user.getSettings();
        this.updatedAt = user.getUpdatedAt();
        this.deletedAt = user.getDeletedAt();
    }

    // Generate getters for each field below (or use Lombok @Getter for brevity)
    // Example:
    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getUsername() { return username; }
    public String getProfilePhoto() { return profilePhoto; }
    public Date getCreatedAt() { return createdAt; }
    public boolean isEmailVerified() { return isEmailVerified; }
    public String getAuthProvider() { return authProvider; }
    public List<String> getRoles() { return roles; }
    public Date getLastLogin() { return lastLogin; }
    public String getReferalLink() { return referalLink; }
    public List<String> getSavedLocations() { return savedLocations; }
    public List<String> getCreatedLocations() { return createdLocations; }
    public List<String> getReferredUsers() { return referredUsers; }
    public Integer getActivePoints() { return activePoints; }
    public Integer getTotalPoints() { return totalPoints; }
    public List<String> getBadges() { return badges; }
    public String getRank() { return rank; }
    public List<Map<String, Object>> getVisitHistory() { return visitHistory; }
    public List<String> getFeedbackGiven() { return feedbackGiven; }
    public List<String> getSearchHistory() { return searchHistory; }
    public List<String> getNotifications() { return notifications; }
    public boolean isPremiumUser() { return isPremiumUser; }
    public String getSubscriptionPlan() { return subscriptionPlan; }
    public List<String> getPaymentMethods() { return paymentMethods; }
    public List<String> getFollowers() { return followers; }
    public List<String> getFollowing() { return following; }
    public String getBio() { return bio; }
    public String getSchool() { return school; }
    public String getProgram() { return program; }
    public String getPreferredStudyTimes() { return preferredStudyTimes; }
    public String getLocation() { return location; }
    public Map<String, Object> getSettings() { return settings; }
    public Date getUpdatedAt() { return updatedAt; }
    public Date getDeletedAt() { return deletedAt; }
}
