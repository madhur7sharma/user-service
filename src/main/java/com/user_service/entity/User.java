package com.user_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.*;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String userName;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "is_private", nullable = false)
    private boolean isPrivate = false;

    @Column(name = "firstname", nullable = false)
    private String firstName;

    @Column(name = "lastname", nullable = false)
    private String lastName;

    @Column(name = "age")
    private int age;

    @Column(name = "dob")
    private Date dateOfBirth;

    @Column(name = "roles", nullable = false)
    private String roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Column(name = "posts")
    private List<Post> posts;

    @ManyToMany(mappedBy = "likes")
    private Set<Post> likedPosts = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @Column(name = "user_comments")
    private Set<Comment> comments;

    @OneToMany(mappedBy="to")
    private Set<Following> followers = new HashSet<>();

    @OneToMany(mappedBy="from")
    private Set<Following> following = new HashSet<>();

    @Column(name = "datecreated")
    @CreationTimestamp
    private Date dateCreated;

    @Column(name = "lastupdated")
    @UpdateTimestamp
    private Date lastUpdated;
}
