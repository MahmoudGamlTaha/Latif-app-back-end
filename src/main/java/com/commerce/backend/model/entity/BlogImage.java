package com.commerce.backend.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "blog_images")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Long.class)
public class BlogImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@SequenceGenerator(name="seq-gen", sequenceName= "blogs_img_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "image")
    private String image;
    
    @ManyToOne(optional = true)
    @JoinColumn(name = "blog_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private Blog blog;
    
    @Column(name = "external_link")
    private Boolean externalLink;
    
    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;
}
