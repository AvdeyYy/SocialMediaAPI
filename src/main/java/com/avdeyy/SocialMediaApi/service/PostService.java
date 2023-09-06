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
    private final JwtTokenUtil jwtTokenUtil;

//    public ResponseEntity<?> addPostToUser(@RequestBody Post post) {
//        post.setHeader(post.getHeader());
//        post.setText(post.getText());
//        postRepository.save(post);
//        return  ResponseEntity.ok(HttpStatus.OK);
//    }
    public ResponseEntity<?> addPostToUser(@RequestBody Post post,@AuthenticationPrincipal User user) {
        post.setHeader(post.getHeader());
        post.setText(post.getText());
        post.setUser(user);
        postRepository.save(post);
        return  ResponseEntity.ok("success");
    }
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        postRepository.deleteById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    public ResponseEntity<?> updatePost(@PathVariable Long id) { // С айдишником все нормально, разобраться почему не заменяет данные в базе
        Post post = postRepository.findById(id).orElseThrow();
        post.setHeader(post.getHeader());
        post.setText(post.getText());
        postRepository.save(post);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
