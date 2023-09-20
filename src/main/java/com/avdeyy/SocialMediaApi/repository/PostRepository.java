package com.avdeyy.SocialMediaApi.repository;

import com.avdeyy.SocialMediaApi.entity.Post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
          @Query(value = "SELECT * FROM posts u where u.user_id = :id",
          nativeQuery = true)
          List<Post> findPostsByUser(@Param("id") Long id);
}
