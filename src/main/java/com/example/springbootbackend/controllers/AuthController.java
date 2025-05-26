package com.example.springbootbackend.controllers;

import com.example.springbootbackend.models.User;
import com.example.springbootbackend.models.VerificationData;
import com.example.springbootbackend.repositories.UserRepository;
import com.example.springbootbackend.security.JwtUtil;
import com.example.springbootbackend.security.LogInDTO;
import com.example.springbootbackend.security.UserDTO;
import com.example.springbootbackend.services.EmailService;
import com.example.springbootbackend.utils.VerificationTokenUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private EmailService emailService;

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

    String code = String.format("%06d", new Random().nextInt(999999));
    String jwt = VerificationTokenUtil.generateVerificationToken("empty", code);

    VerificationData verificationData = new VerificationData(code, jwt, System.currentTimeMillis());
    user.setVerificationData(verificationData);

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

    @GetMapping("/sendemailcode")
    public ResponseEntity<?> testEmail(@RequestParam String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        User user = userOpt.get();

        // Generate verification code and token
        String code = String.format("%06d", new Random().nextInt(999999));
        String jwt = VerificationTokenUtil.generateVerificationToken(user.getEmail(), code);

        // Store verification data on user
        VerificationData verificationData = new VerificationData(code, jwt, System.currentTimeMillis());
        user.setVerificationData(verificationData);
        userRepository.save(user);

        // Send email
        try {
            emailService.sendPasswordResetEmail(user.getEmail(), user.getUsername(), code);
            return ResponseEntity.ok(Map.of("message", "Verification email sent."));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Failed to send email."));
        }
    }

    @PostMapping("/verifycode")
    public ResponseEntity<?> verifyCode(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        String code = payload.get("code");
        System.out.println(email);
        System.out.println(code);

        if (email == null || code == null || email.isBlank() || code.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Email and code are required"));
        }


        Optional<User> userOpt = userRepository.findByEmail(email);
        System.out.println(userOpt);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "User not found"));
        }

        User user = userOpt.get();
        VerificationData data = user.getVerificationData();

        if (data == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No code has been requested.");
        }

        // Check if code matches
        if (!code.equals(data.getCode())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid code"));
        }

        // Validate JWT
        boolean isValid = VerificationTokenUtil.validateToken(data.getJwt(), email, code);
        if (!isValid) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Verification code has expired"));
        }

        return ResponseEntity.ok(Map.of("jwt", data.getJwt()));
    }

    @PostMapping("/validate-token")
    public ResponseEntity<?> validateVerificationToken(@RequestBody Map<String, String> payload) {
        String token = payload.get("token");
        if (token == null || token.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Token is required"));
        }

        try {
            boolean isValid = VerificationTokenUtil.isTokenValid(token);
            if (!isValid) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Token is invalid or expired"));
            }
            return ResponseEntity.ok(Map.of("message", "Token is valid"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Failed to validate token"));
        }
    }


}
