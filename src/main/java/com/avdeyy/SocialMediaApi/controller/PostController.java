package com.avdeyy.SocialMediaApi.controller;


import com.avdeyy.SocialMediaApi.dto.PostDto;
import com.avdeyy.SocialMediaApi.entity.Post;
import com.avdeyy.SocialMediaApi.service.PostService;
import com.avdeyy.SocialMediaApi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;

@RestController
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
    }

    @PostMapping("/post/remove")
    public ResponseEntity<?> removePost(@RequestBody Long id, Principal principal) {
        return postService.deletePost(id, principal);
    }

    @PostMapping("/post/add")
    public ResponseEntity<?> addPost(Principal principal, @RequestBody Post post) {
        return postService.addPostToUser(principal, post);
    }

    @PostMapping("/post/{id}/update")
    public ResponseEntity<?> updatePost(Principal principal, @PathVariable Long id, @RequestBody PostDto postDto) {
        return postService.updatePost(principal, id, postDto);
    }

    @GetMapping("/getPosts/{userId}")
    public ResponseEntity<?> viewPost(@PathVariable Long userId) {
        postService.getPostByUser(userId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/receivePosts")
    public ResponseEntity<?> receivePosts(Principal principal) {
        postService.getPostsByFollowing(principal);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}


