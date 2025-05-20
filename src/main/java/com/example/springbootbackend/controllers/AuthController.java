package com.example.springbootbackend.controllers;

import com.example.springbootbackend.models.User;
import com.example.springbootbackend.repositories.UserRepository;
import com.example.springbootbackend.security.JwtUtil;
import com.example.springbootbackend.security.LogInDTO;
import com.example.springbootbackend.security.UserDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
public ResponseEntity<?> registerUser(@RequestBody User user) {
    if (user.getEmail() == null || user.getEmail().isBlank()) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email is required!");
    }
    if (user.getPassword() == null || user.getPassword().isBlank()) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password is required!");
    }
    if (user.getName() == null || user.getName().isBlank()) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Name is required!");
    }

    Optional<User> existingUserViaEmail = userRepository.findByEmail(user.getEmail());
    if (existingUserViaEmail.isPresent()) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", "Email is already registered!"));
    }

    Optional<User> existingUserViaUsername = userRepository.findByUsername(user.getUsername());
    if (existingUserViaUsername.isPresent()) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", "Username is already registered!"));
    }

    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setCreatedAt(new Date());

    // Initialize optional String fields
    if (user.getReferalLink() == null) user.setReferalLink("None");
    if (user.getProfilePhoto() == null) user.setProfilePhoto("https://studyspotr.s3.us-east-2.amazonaws.com/defaultProfile.jpg");
    if (user.getBio() == null) user.setBio("None");
    if (user.getSchool() == null) user.setSchool("None");
    if (user.getProgram() == null) user.setProgram("None");
    if (user.getSubscriptionPlan() == null) user.setSubscriptionPlan("None");
    if (user.getPreferredStudyTimes() == null) user.setPreferredStudyTimes("None");
    if (user.getLocation() == null) user.setLocation("None");
    if (user.getAuthProvider() == null) user.setAuthProvider("email");
    if (user.getRank() == null) user.setRank("None");

    // Initialize list fields
    if (user.getSavedLocations() == null) user.setSavedLocations(new ArrayList<>());
    if (user.getCreatedLocations() == null) user.setCreatedLocations(new ArrayList<>());
    if (user.getReferredUsers() == null) user.setReferredUsers(new ArrayList<>());
    if (user.getBadges() == null) user.setBadges(new ArrayList<>());
    if (user.getRoles() == null || user.getRoles().isEmpty()) user.setRoles(new ArrayList<>(List.of("user")));
    if (user.getFollowers() == null) user.setFollowers(new ArrayList<>());
    if (user.getFollowing() == null) user.setFollowing(new ArrayList<>());
    if (user.getSearchHistory() == null) user.setSearchHistory(new ArrayList<>());
    if (user.getFeedbackGiven() == null) user.setFeedbackGiven(new ArrayList<>());
    if (user.getNotifications() == null) user.setNotifications(new ArrayList<>());
    if (user.getVisitHistory() == null) user.setVisitHistory(new ArrayList<>());
    if (user.getPaymentMethods() == null) user.setPaymentMethods(new ArrayList<>());

    // Initialize maps
    if (user.getSettings() == null) user.setSettings(new HashMap<>());

    // Default flags
    user.setEmailVerified(false);
    user.setPremiumUser(false);

    // Initial points
    user.setActivePoints(0);
    user.setTotalPoints(0);

    user.setUpdatedAt(new Date());

    userRepository.save(user);
    String token = jwtUtil.generateToken(user.getEmail());

    return ResponseEntity.status(HttpStatus.CREATED).body(
            Map.of("message", "Account created successfully!", "token", token));
}



@PostMapping("/login")
public ResponseEntity<?> loginUser(@RequestBody LogInDTO loginRequest) {
    String loginId = loginRequest.getLoginId();
    Optional<User> existingUser;

    if (loginId == null || loginId.isBlank()) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email or username is required");
    }

    if (loginId.contains("@")) {
        existingUser = userRepository.findByEmail(loginId);
    } else {
        existingUser = userRepository.findByUsername(loginId);
    }

    if (existingUser.isEmpty()) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

    User user = existingUser.get();

    if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

    String token = jwtUtil.generateToken(user.getEmail());

    return ResponseEntity.ok().body(Map.of("token", token));
}


    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        if (!jwtUtil.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
        }

        String email = jwtUtil.extractEmail(token);
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        User userGet = user.get();

        UserDTO userDTO = new UserDTO(userGet);

        return ResponseEntity.ok(userDTO);
    }
}
