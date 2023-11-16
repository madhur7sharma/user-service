package com.user_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "posts")
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url", nullable = false)
    private String postUrl;

    @ManyToOne
    @JoinColumn(name = "user_posts", nullable = false)
    private User user;

    @ManyToMany
    @JoinTable(name = "likes", joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> likes = new HashSet<>();

    @OneToMany(mappedBy = "post")
    @Column(name = "user_comments")
    private Set<Comment> comments;

    @Column(name = "datecreated")
    @CreationTimestamp
    private Date dateCreated;

    @Column(name = "lastupdated")
    @UpdateTimestamp
    private Date lastUpdated;
}
