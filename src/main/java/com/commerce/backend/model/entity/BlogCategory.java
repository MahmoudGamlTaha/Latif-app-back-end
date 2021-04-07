package com.commerce.backend.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "blog_categories")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Long.class)
public class BlogCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seq-gen")
    @SequenceGenerator(name="seq-gen", sequenceName="blog_categories_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;
    
    @Column(name = "name_ar")
    private String nameAr;

    @Column(name = "description")
    private String description;
    
    @Column(name = "icon")
    private String icon;
    
    @Column(name = "icon_select")
    private String iconSelect;
    
    @Column(name = "external_link")
    private Boolean externalLink;
    
    @Column(name = "active")
    private Boolean active;
    
    //@JsonIdentityReference(alwaysAsId = true)
    @JsonBackReference
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Blog> blogs;

    @Column(name = "created_at")
    private Date created_at;

    @Column(name = "updated_at")
    private Date updated_at;
}
