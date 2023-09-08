package com.avdeyy.SocialMediaApi.repository;

import com.avdeyy.SocialMediaApi.entity.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {

}
