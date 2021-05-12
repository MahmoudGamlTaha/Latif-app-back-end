package com.commerce.backend.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@Data
@Table(name = "category_type")
public class CategoryType {
                                                                                                                            
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="category_type_id_seq")
    @SequenceGenerator(name="category_type_id_seq", sequenceName= "category_type_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "code", unique = true)
    private String code;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "name_ar")
    private String nameAr;
    
    @Column(name = "active")
    private Boolean active;
    
    @Column(name = "order")
    private Integer order;
    
    @Column(name = "created_at")
    private Date created_at;

    @Column(name = "updated_at")
    private Date updated_at;
}
