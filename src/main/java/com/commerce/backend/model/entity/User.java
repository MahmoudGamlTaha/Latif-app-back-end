package com.commerce.backend.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
//@ToString(exclude = "cart")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  //  TODO remove this and use cart repository findByUserId instead
  @JsonIgnore
  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private Cart cart;
  
  @OneToMany(mappedBy = "createdBy")
   private UserAds ads;
  
  @Column(name = "email", unique = true)
  private String email;

  @Column(name = "password", nullable = false)
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
  private Integer emailVerified;

  @Column(name = "mobile")
  private String phone;

  @Column(name = "avatar")
  private String avatar;

  @Column(name = "country")
  private String country;

  @Column(name = "address1")
  private String address;
  
  @Column(name = "created_at", updatable = false)
   @Type(type = "timestamp")
   private Date registrationDate;
 
  @OneToMany(mappedBy = "user")
  private Set<UserRole> roles;
  
  @Column(name = "active")
  private Boolean active;
   
}