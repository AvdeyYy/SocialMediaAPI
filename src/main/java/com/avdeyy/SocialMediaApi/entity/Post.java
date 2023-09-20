package com.avdeyy.SocialMediaApi.entity;

import lombok.Data;
import org.springframework.http.ResponseEntity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "posts")
public class Post implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "header")
    private String header;
    @Column(name = "text")
    private String text;
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

}
