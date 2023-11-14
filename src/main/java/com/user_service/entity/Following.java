package com.user_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.user_service.dto.FollowStates;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "following")
public class Following {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "follow_request", nullable = false)
    private FollowStates followRequest = FollowStates.NONE;

    @ManyToMany
    @JoinTable(name = "following_users", joinColumns = @JoinColumn(name = "following_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> follower = new HashSet<>();

    @Column(name = "datecreated")
    @CreationTimestamp
    private Date dateCreated;

    @Column(name = "lastupdated")
    @UpdateTimestamp
    private Date lastUpdated;

}
