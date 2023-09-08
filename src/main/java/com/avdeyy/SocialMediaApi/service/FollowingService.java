package com.avdeyy.SocialMediaApi.service;

import com.avdeyy.SocialMediaApi.entity.User;
import com.avdeyy.SocialMediaApi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;


@Service
@RequiredArgsConstructor
public class FollowingService {
    private final UserRepository userRepository;
    private final UserService userService;

    public ResponseEntity<?> subscribe(Principal principal, User user) {
        return userService.findByPrincipal(principal, currentUser -> {
            user.getFollowers().add(currentUser);
            userRepository.save(currentUser);

            return ResponseEntity.ok(HttpStatus.OK);
        });

    }

    public ResponseEntity<?> unsubscribe(Principal principal, User user) {
        return userService.findByPrincipal(principal, currentUser -> {
            user.getFollowers().remove(currentUser);
            userRepository.save(currentUser);
            return ResponseEntity.ok(HttpStatus.OK);
        });
    }

}
