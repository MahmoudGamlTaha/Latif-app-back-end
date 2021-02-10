package com.commerce.backend.model.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;


@Entity
@Table(name = "item_category")
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name ="type", discriminatorType = DiscriminatorType.INTEGER)
@ToString
public class ItemObjectCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="item_category_sequence")
    @SequenceGenerator(name="item_category_sequence", sequenceName= "item_category_sequence", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 250)
    private String name;
    
    @OneToOne
    @JoinColumn(name = "parent_id")
    private ItemObjectCategory parent_id;
    
    @Column(name = "icon")
    private String icon;
    
    @OneToMany(mappedBy="child", cascade = CascadeType.ALL)
    private Set<ItemObjectCategory> child = new HashSet<ItemObjectCategory>();
    
    @Column(name = "type", insertable = false, updatable = false)
    private Integer type;
    
    public void addChild(ItemObjectCategory itemObjectCategory) {
    	this.getChild().add(itemObjectCategory);
    }
    
   public ItemObjectCategory(String name, String icon, ItemObjectCategory parent) {
	  this.setName(name);
	  this.setIcon(icon);
	  this.setParent_id(parent);
  }

}
