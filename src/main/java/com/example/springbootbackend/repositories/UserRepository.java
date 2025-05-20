package com.example.springbootbackend.repositories;

import com.example.springbootbackend.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findByReferalLink(String referalLink);

    Optional<User> findByAuthProvider(String authProvider);

    List<User> findByIsPremiumUser(boolean isPremiumUser);

    List<User> findByRolesContaining(String role);

    List<User> findByCreatedAtAfter(Date date);

    List<User> findByFollowersContains(String userId);

    List<User> findByFollowingContains(String userId);

    List<User> findBySavedLocationsContains(String locationId);

    List<User> findByLastLoginBefore(Date date);

    List<User> findByUpdatedAtBetween(Date start, Date end);
}
