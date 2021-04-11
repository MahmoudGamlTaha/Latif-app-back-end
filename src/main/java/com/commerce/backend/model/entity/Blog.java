package com.commerce.backend.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "blogs")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Long.class)
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seq-gen")
    @SequenceGenerator(name="seq-gen", sequenceName= "blogs_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false, length=250)
    private String title;

//    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(optional = true)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "category_id")
    private BlogCategory category;

    @Column(name = "image")
    private String image;

    @Column(name = "path")
    private String path;

    @Column(name = "description", length = 250)
    private String description;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User userId;
    
    @OneToMany(fetch = FetchType.LAZY)
    private Set<BlogImage> blogImage;

    @Column(name = "date")
    private Date date;

    @Column(name = "created_at")
    private Date created_at;

    @Column(name = "updated_at")
    private Date updated_at;
}
