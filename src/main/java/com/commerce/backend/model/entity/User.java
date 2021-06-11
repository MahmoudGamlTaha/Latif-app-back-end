package com.commerce.backend.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
  @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
  private Long id;

  //  TODO remove this and use cart repository findByUserId instead
  //@JsonIgnore
  //@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  //private Cart cart;

  @JsonManagedReference
  @OneToMany(mappedBy = "createdBy", fetch = FetchType.EAGER)
  private Set<UserAds> ads;
  
  @Column(name = "email", unique = true)
  private String email;

  @Column(name = "password")
  private String password;

  @Column(name = "fname")
  private String firstName;

  @Column(name = "lname")
  private String lastName;

  @Column(name = "city")
  private String city;

  @Column(name = "state")
  private String state;

  @Column(name = "zip")
  private String zip;

  @Column(name = "email_verified")
  private Boolean emailVerified;

  @Column(name = "mobile", unique = true)
  private String mobile;

  @Column(name = "avatar")
  private String avatar;

  @Column(name = "country")
  private String country;

  @Column(name = "address1")
  private String address;

  @Column(name = "active")
  private boolean active;

  @Column(name = "username")
  private String username;
  
  @Column(name = "created_at", updatable = false)
   @Type(type = "timestamp")
   private Date registrationDate;

  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
  @JoinTable(
          name = "user_subscription",
          joinColumns = {@JoinColumn(name = "user_id")},
          inverseJoinColumns = {@JoinColumn(name = "subscription_id")}
  )
  @NotFound(action = NotFoundAction.IGNORE)
  private Set<SubscriptionTypes> subscriptions = new HashSet<>();

  @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
  @JoinTable(
          name = "user_roles",
          joinColumns = {@JoinColumn(name = "user_id")},
          inverseJoinColumns = {@JoinColumn(name = "role_id")}
  )
  @NotFound(action = NotFoundAction.IGNORE)
  private Set<Role> roles  = new HashSet<>();

  @JsonManagedReference
  @OneToMany
  @JoinColumn(name = "user_id")
  private Set<UserReportedAds> userReportedAds;

  @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
  @JoinTable(
          name = "user_interset_category",
          joinColumns = {@JoinColumn(name = "user_id")},
          inverseJoinColumns = {@JoinColumn(name = "category_id")}
  )
  @NotFound(action = NotFoundAction.IGNORE)
  private Set<ItemObjectCategory> interestCategories  = new HashSet<>();
}