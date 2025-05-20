package com.example.springbootbackend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Document(collection = "users")
public class User {
    @Id
    private String id;

    private String email;
    private String password;
    private String name;
    private String username;
    private Date createdAt;

    private String referalLink;
    private ArrayList<String> savedLocations = new ArrayList<>();
    private ArrayList<String> createdLocations = new ArrayList<>();
    private ArrayList<String> referredUsers = new ArrayList<>();

    private Integer activePoints = 0;
    private Integer totalPoints = 0;

    private String profilePhoto = "https://studyspotr.s3.us-east-2.amazonaws.com/defaultProfile.jpg";

    private boolean isEmailVerified = false;
    private String authProvider; // e.g., "email", "google", etc.
    private List<String> roles = new ArrayList<>(Collections.singletonList("user"));
    private Date lastLogin;

    private List<String> badges = new ArrayList<>();
    private String rank; // e.g., Bronze, Silver
    private List<Map<String, Object>> visitHistory = new ArrayList<>();

    private List<String> feedbackGiven = new ArrayList<>();
    private List<String> searchHistory = new ArrayList<>();
    private List<String> notifications = new ArrayList<>();

    private boolean isPremiumUser = false;
    private String subscriptionPlan;
    private List<String> paymentMethods = new ArrayList<>();

    private List<String> followers = new ArrayList<>();
    private List<String> following = new ArrayList<>();
    private String bio;
    private String school;
    private String program;
    private String preferredStudyTimes;

    private String location;
    private Map<String, Object> settings = new HashMap<>();
    private Date updatedAt;
    private Date deletedAt;

    public User() {}

    public User(String email, String password, String name, String username, Date createdAt) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.username = username;
        this.createdAt = createdAt;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public String getProfilePhoto() { return profilePhoto; }
    public void setProfilePhoto(String profilePhoto) { this.profilePhoto = profilePhoto; }

    public String getReferalLink() { return referalLink; }
    public void setReferalLink(String referalLink) { this.referalLink = referalLink; }

    public List<String> getSavedLocations() { return savedLocations; }
    public void setSavedLocations(ArrayList<String> savedLocations) { this.savedLocations = savedLocations; }
    public void addSavedLocation(String spotId) {
        if (!savedLocations.contains(spotId)) savedLocations.add(spotId);
    }
    public void removeSavedLocation(String spotId) { savedLocations.remove(spotId); }

    public List<String> getCreatedLocations() { return createdLocations; }
    public void setCreatedLocations(ArrayList<String> createdLocations) { this.createdLocations = createdLocations; }
    public void addCreatedLocation(String spotId) {
        if (!createdLocations.contains(spotId)) createdLocations.add(spotId);
    }
    public void removeCreatedLocation(String spotId) { createdLocations.remove(spotId); }

    public List<String> getReferredUsers() { return referredUsers; }
    public void setReferredUsers(ArrayList<String> referredUsers) { this.referredUsers = referredUsers; }
    public void addReferredUser(String userId) {
        if (!referredUsers.contains(userId)) referredUsers.add(userId);
    }
    public void removeReferredUser(String userId) { referredUsers.remove(userId); }

    public Integer getActivePoints() { return activePoints; }
    public void setActivePoints(Integer value) { this.activePoints = value; }
    public void addActivePoints(Integer addPoints) { this.activePoints += addPoints; }
    public void useActivePoints(Integer usePoints) { this.activePoints -= usePoints; }

    public Integer getTotalPoints() { return totalPoints; }
    public void setTotalPoints(Integer value) { this.totalPoints = value; }
    public void addTotalPoints(Integer addPoints) { this.totalPoints += addPoints; }

    public boolean isEmailVerified() { return isEmailVerified; }
    public void setEmailVerified(boolean emailVerified) { isEmailVerified = emailVerified; }

    public String getAuthProvider() { return authProvider; }
    public void setAuthProvider(String authProvider) { this.authProvider = authProvider; }

    public List<String> getRoles() { return roles; }
    public void setRoles(List<String> roles) { this.roles = roles; }

    public Date getLastLogin() { return lastLogin; }
    public void setLastLogin(Date lastLogin) { this.lastLogin = lastLogin; }

    public List<String> getBadges() { return badges; }
    public void setBadges(List<String> badges) { this.badges = badges; }
    public void addBadge(String badge) { if (!badges.contains(badge)) badges.add(badge); }

    public String getRank() { return rank; }
    public void setRank(String rank) { this.rank = rank; }

    public List<Map<String, Object>> getVisitHistory() { return visitHistory; }
    public void setVisitHistory(List<Map<String, Object>> visitHistory) { this.visitHistory = visitHistory; }
    public void addVisit(Map<String, Object> visit) { this.visitHistory.add(visit); }

    public List<String> getFeedbackGiven() { return feedbackGiven; }
    public void setFeedbackGiven(List<String> feedbackGiven) { this.feedbackGiven = feedbackGiven; }
    public void addFeedback(String locationId) { if (!feedbackGiven.contains(locationId)) feedbackGiven.add(locationId); }

    public List<String> getSearchHistory() { return searchHistory; }
    public void setSearchHistory(List<String> searchHistory) { this.searchHistory = searchHistory; }
    public void addSearchQuery(String query) { searchHistory.add(query); }

    public List<String> getNotifications() { return notifications; }
    public void setNotifications(List<String> notifications) { this.notifications = notifications; }
    public void addNotification(String notification) { notifications.add(notification); }

    public boolean isPremiumUser() { return isPremiumUser; }
    public void setPremiumUser(boolean premiumUser) { isPremiumUser = premiumUser; }

    public String getSubscriptionPlan() { return subscriptionPlan; }
    public void setSubscriptionPlan(String subscriptionPlan) { this.subscriptionPlan = subscriptionPlan; }

    public List<String> getPaymentMethods() { return paymentMethods; }
    public void setPaymentMethods(List<String> paymentMethods) { this.paymentMethods = paymentMethods; }

    public List<String> getFollowers() { return followers; }
    public void setFollowers(List<String> followers) { this.followers = followers; }
    public void addFollower(String userId) { if (!followers.contains(userId)) followers.add(userId); }

    public List<String> getFollowing() { return following; }
    public void setFollowing(List<String> following) { this.following = following; }
    public void addFollowing(String userId) { if (!following.contains(userId)) following.add(userId); }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public String getSchool() { return school; }
    public void setSchool(String school) { this.school = school; }

    public String getProgram() { return program; }
    public void setProgram(String program) { this.program = program; }

    public String getPreferredStudyTimes() { return preferredStudyTimes; }
    public void setPreferredStudyTimes(String preferredStudyTimes) { this.preferredStudyTimes = preferredStudyTimes; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Map<String, Object> getSettings() { return settings; }
    public void setSettings(Map<String, Object> settings) { this.settings = settings; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }

    public Date getDeletedAt() { return deletedAt; }
    public void setDeletedAt(Date deletedAt) { this.deletedAt = deletedAt; }
}
