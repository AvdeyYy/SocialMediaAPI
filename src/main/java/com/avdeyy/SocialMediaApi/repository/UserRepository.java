package com.avdeyy.SocialMediaApi.repository;

import com.avdeyy.SocialMediaApi.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    Optional<User> findByUsername(String username);
    @Query(value = "SELECT following_id FROM follower_following WHERE follower_id = :id",
    nativeQuery = true)
    String findByPostsFromFollowing(Long id);//По айди текущего пользоваетял находит всех его подписчиков в бд
}
