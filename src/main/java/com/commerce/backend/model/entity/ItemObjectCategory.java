package com.commerce.backend.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;


@Entity
@Table(name = "item_category")
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name ="type", discriminatorType = DiscriminatorType.INTEGER)
public class ItemObjectCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="item_category_sequence")
    @SequenceGenerator(name="item_category_sequence", sequenceName= "item_category_sequence", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 250, nullable = false, unique = true)
    private String name;
    
    @Column(name = "name_ar", length = 250)
    private String nameAr;

    @Column(name = "code", length = 40)
    private String code;

    @ManyToOne(optional = true)
    @JoinColumn(name = "parent_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private ItemObjectCategory parent;
    
    @Column(name = "icon")
    private String icon;
    
    @Column(name = "icon_select")
    private String icon_select;
    
    @OneToMany(mappedBy="parent", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<ItemObjectCategory> child = new HashSet<ItemObjectCategory>();
    
    @Column(name = "type", insertable = false, updatable = false)
    private Integer type;
    
    @Column(name = "active")
    private Boolean active;
    
    @Column(name = "is_external_link")
    private Boolean isExternalLink;

    @ManyToMany(mappedBy = "interestCategories", cascade = CascadeType.PERSIST)
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnore(value = true)
    private Set<User> users;
    
    @Column(name = "show_interest")
    private Boolean showInterest;
    public void addChild(ItemObjectCategory itemObjectCategory) {
    	this.getChild().add(itemObjectCategory);
    }
    
   public ItemObjectCategory(String name, String icon, ItemObjectCategory parent) {
	  this.setName(name);
	  this.setIcon(icon);
	//  this.setParent(parent);
  }

}
