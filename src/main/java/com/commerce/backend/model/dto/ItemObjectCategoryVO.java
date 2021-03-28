package com.commerce.backend.model.dto;

import com.commerce.backend.model.entity.ItemObjectCategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ItemObjectCategoryVO {
	private long id;
    private String name;
    private String nameAr;
    private String icon;
    private Integer type;
    private Boolean isExternalLink;
    private ItemObjectCategoryVO parent;
    public ItemObjectCategoryVO(ItemObjectCategory entity) {
    	this.id = entity.getId();
    	this.icon = entity.getIcon();
    	this.name = entity.getName();
    	this.type = entity.getType();
    	this.nameAr = entity.getNameAr();
   // 	this.isExternalLink = entity.getIsExternalLink();
   
    }
    
    
}
