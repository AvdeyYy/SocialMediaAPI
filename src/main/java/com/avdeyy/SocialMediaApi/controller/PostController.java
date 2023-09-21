package com.avdeyy.SocialMediaApi.controller;


import com.avdeyy.SocialMediaApi.entity.Post;
import com.avdeyy.SocialMediaApi.entity.User;
import com.avdeyy.SocialMediaApi.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.math.BigInteger;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
public class PostController {
    private final PostService postService;
    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/post/{id}/remove")
    public ResponseEntity<?> removePost(@PathVariable Long id,Principal principal){
        return postService.deletePost(id, principal);
    }
    @PostMapping("/post/add")
    public ResponseEntity<?> addPost(Principal principal,@RequestBody Post post){
        return postService.addPostToUser(principal,post);
    }
    @PostMapping("/post/{id}/update")
    public ResponseEntity<?> updatePost(Principal principal,@PathVariable Long id) {
        return postService.updatePost(principal,id);
    }

    @PostMapping("/getPosts/{userId}")
    public ResponseEntity<?> getPost(@PathVariable Long userId) {
        postService.getPostFromUser(userId);
         return ResponseEntity.ok(HttpStatus.OK);
        }

    }

