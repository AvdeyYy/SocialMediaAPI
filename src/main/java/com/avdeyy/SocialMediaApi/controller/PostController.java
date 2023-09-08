package com.avdeyy.SocialMediaApi.controller;


import com.avdeyy.SocialMediaApi.entity.Post;
import com.avdeyy.SocialMediaApi.entity.User;
import com.avdeyy.SocialMediaApi.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

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
}
