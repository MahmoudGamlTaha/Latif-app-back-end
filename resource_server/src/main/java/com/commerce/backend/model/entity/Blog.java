package com.commerce.backend.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "blogs")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Long.class)
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@SequenceGenerator(name="blogs_sequence", sequenceName= "blogs_sequence", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false, length=250)
    private String title;

    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    @JoinColumn(name = "category_id")
    private BlogCategory category;

    @Column(name = "image")
    private String image;

    @Column(name = "path")
    private String path;

    @Column(name = "description", length = 250)
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "date")
    private Date date;

    @Column(name = "created_at")
    private Date created_at;

    @Column(name = "updated_at")
    private Date updated_at;

    public Blog() {

    }
}