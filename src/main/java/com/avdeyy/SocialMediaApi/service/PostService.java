package com.avdeyy.SocialMediaApi.service;

import com.avdeyy.SocialMediaApi.entity.Post;
import com.avdeyy.SocialMediaApi.entity.User;
import com.avdeyy.SocialMediaApi.repository.PostRepository;
import com.avdeyy.SocialMediaApi.repository.UserRepository;
import com.avdeyy.SocialMediaApi.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;


@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;

    public ResponseEntity<?> addPostToUser(Principal principal,@RequestBody Post post) {
        return userService.findByPrincipal(principal, currentUser -> {
            post.setUser(currentUser);
            post.setHeader(post.getHeader());
            post.setText(post.getText());
            postRepository.save(post);
            return ResponseEntity.ok(HttpStatus.CREATED);
        });
    }
    public ResponseEntity<?> deletePost(@PathVariable Long id,Principal principal) {
       return userService.findByPrincipal(principal, currentUser -> {
            postRepository.deleteById(id);
        return ResponseEntity.ok(HttpStatus.OK);
        });
    }

    public ResponseEntity<?> updatePost(Principal principal, @PathVariable Long id) {
        return userService.findByPrincipal(principal, currentUser -> {
            Post post = postRepository.findById(id).orElseThrow();
            post.setHeader(post.getHeader());
            post.setText(post.getText());
            postRepository.save(post);
        return ResponseEntity.ok(HttpStatus.OK);
    });
    }
}
