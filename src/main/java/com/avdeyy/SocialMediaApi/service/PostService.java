package com.avdeyy.SocialMediaApi.service;

import com.avdeyy.SocialMediaApi.dto.PostDto;
import com.avdeyy.SocialMediaApi.entity.Post;
import com.avdeyy.SocialMediaApi.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;


@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;

    @Autowired
    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }


    public ResponseEntity<?> addPostToUser(Principal principal, Post post) {
        return userService.findByPrincipal(principal, currentUser -> {
            post.setUser(currentUser);
            post.setHeader(post.getHeader());
            post.setText(post.getText());
            postRepository.save(post);
            return ResponseEntity.ok(HttpStatus.CREATED);
        });
    }

    public ResponseEntity<?> deletePost(Long id, Principal principal) {
        return userService.findByPrincipal(principal, currentUser -> {
            postRepository.deleteById(id);
            return ResponseEntity.ok(HttpStatus.OK);
        });

    }

    public ResponseEntity<?> updatePost(Principal principal, Long id, PostDto postDto) {
        return userService.findByPrincipal(principal, currentUser -> {
            Post post = postRepository.findById(id).orElseThrow();
            post.setHeader(postDto.getHeader());
            post.setText(postDto.getText());
            postRepository.save(post);
            return ResponseEntity.ok(HttpStatus.OK);
        });
    }

    public ResponseEntity<?> getPostByUser(Long userId) {
        postRepository.findPostsByUser(userId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    public ResponseEntity<?> getPostsByFollowing(Principal principal) {
        postRepository.findPostsByUser(userService.getFollowing(principal));
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
