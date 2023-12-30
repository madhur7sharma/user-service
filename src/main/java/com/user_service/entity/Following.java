package com.user_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.user_service.dto.FollowStates;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "following")
@EqualsAndHashCode
@ToString
public class Following {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "follow_request", nullable = false)
    private FollowStates followRequest = FollowStates.NONE;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name="from_user_fk")
    private User from;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name="to_user_fk")
    private User to;

    @Column(name = "datecreated")
    @CreationTimestamp
    private Date dateCreated;

    @Column(name = "lastupdated")
    @UpdateTimestamp
    private Date lastUpdated;

}
