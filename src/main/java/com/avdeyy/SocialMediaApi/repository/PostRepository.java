package com.avdeyy.SocialMediaApi.repository;

import com.avdeyy.SocialMediaApi.entity.Post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(value = "SELECT header, text FROM posts where user_id = :userId",
    nativeQuery = true)
    List<String> findPostsByUser(@Param("userId") Long userId);


}
