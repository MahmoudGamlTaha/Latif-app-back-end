package com.commerce.backend.service.cache;

import com.commerce.backend.constants.CategoryType;
import com.commerce.backend.constants.MessageType;
import com.commerce.backend.constants.SystemConstant;
import com.commerce.backend.converter.category.ItemObjectCategoryResponseConverter;
import com.commerce.backend.dao.ItemCategoryRepository;
import com.commerce.backend.dao.ItemObjectCategoryRepository;
import com.commerce.backend.dao.PetCategoryRepository;
import com.commerce.backend.dao.ServiceCategoryRepository;
import com.commerce.backend.model.entity.ItemCategory;
import com.commerce.backend.model.entity.ItemObjectCategory;
import com.commerce.backend.model.entity.PetCategory;
import com.commerce.backend.model.entity.ServiceCategory;
import com.commerce.backend.model.request.category.CategoryRequest;
import com.commerce.backend.model.response.BasicResponse;
import com.commerce.backend.model.response.category.ItemObjectCategoryResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@CacheConfig(cacheNames = "item_category")
public class ItemObjectCategoryCacheServiceImpl implements ItemObjectCategoryCacheService {

    private final ItemObjectCategoryRepository itemObjectRepository;
    private final PetCategoryRepository petCategoryRepository;
    private final ServiceCategoryRepository serviceCategoryRepository;
    private final ItemCategoryRepository itemCategoryRepository;
    private final ItemObjectCategoryResponseConverter itemObjectCategoryResponseConverter;

    @Autowired
    public ItemObjectCategoryCacheServiceImpl(ItemObjectCategoryRepository productCategoryRepository,
    		PetCategoryRepository petCategoryRepository, ItemCategoryRepository itemCategoryRepository,
    		ItemObjectCategoryResponseConverter itemObjectCategoryResponseConverter,
    		ServiceCategoryRepository serviceCategoryRepository) {
        this.itemObjectRepository = productCategoryRepository;
        this.petCategoryRepository = petCategoryRepository;
        this.itemCategoryRepository = itemCategoryRepository;
        this.itemObjectCategoryResponseConverter = itemObjectCategoryResponseConverter;
        this.serviceCategoryRepository = serviceCategoryRepository;
    }

    @Override
    @Cacheable(key = "#root.methodName", unless = "#result.size()==0")
    public List<ItemObjectCategory> findAllByOrderByName() {
        return itemObjectRepository.findAllByOrderByName();
    }

	@Override
	public List<PetCategory> findPetsCategories() {
		 return this.petCategoryRepository.findAllByOrderByName();
	}

	@Override
	public List<ItemCategory> findItemCategories() {
		return this.itemCategoryRepository.findAllByOrderByName();
	}

	@Override
	public ItemObjectCategory createItemObjectCategory(CategoryRequest request) {
	    ItemObjectCategory category = null;
		if(request.getType() == CategoryType.PETS.getType()) {
			category = new PetCategory();
			category.setName(request.getName());
			category.setIcon(request.getIcon());
			category.setIsExternalLink(request.isExternalLink());
			category.setNameAr(request.getNameAr());
			category.setIcon_select(request.getIcon_select_path());
			category.setActive(request.isActive());
			 Optional<PetCategory> cat = this.petCategoryRepository.findById(request.getCatParent());
			 PetCategory parent	= cat.isPresent()?cat.get(): null;
			 category.setParent(parent);
			this.petCategoryRepository.save((PetCategory)category);
		}else if(request.getType() == CategoryType.ACCESSORIES.getType()){
				category = new ItemCategory();
				category.setName(request.getName());
				category.setIcon(request.getIcon());
				category.setIcon_select(request.getIcon_select_path());
				category.setIsExternalLink(request.isExternalLink());
				category.setNameAr(request.getNameAr());
				category.setActive(request.isActive());
				category.setIcon_select(request.getIcon_select_path());
				Optional<ItemCategory> itemCategory = this.itemCategoryRepository.findById(request.getCatParent());
				ItemCategory parent = itemCategory.isPresent()?itemCategory.get(): null;
				category.setParent(parent);
				this.itemCategoryRepository.save((ItemCategory)category);
		} else {
			category = new ItemObjectCategory();
			category.setName(request.getName());
			category.setIcon(request.getIcon());
			category.setIcon_select(request.getIcon_select_path());
			category.setIsExternalLink(request.isExternalLink());
			category.setNameAr(request.getNameAr());
			category.setActive(request.isActive());
			category.setType(request.getType());
			category.setIcon_select(request.getIcon_select_path());
			Optional<ItemCategory> itemCategory = this.itemCategoryRepository.findById(request.getCatParent());
			ItemCategory parent = itemCategory.isPresent()?itemCategory.get(): null;
			category.setParent(parent);
			this.itemObjectRepository.save(category);
		}
		return category;
	}

	@Override
	public ItemObjectCategory deleteItemObjectCategory(Long id) {
		 Optional<ItemObjectCategory> itemObjectCategory = this.itemObjectRepository.findById(id); //may be not need
		this.itemObjectRepository.deleteById(id);
		return itemObjectCategory.isPresent()?itemObjectCategory.get():null;
	}

	@Override
	public BasicResponse findAllByTypeId(Integer id, Integer page) {
		BasicResponse categoryByType = new BasicResponse();
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Pageable pageable = PageRequest.of(page,SystemConstant.MOBILE_PAGE_SIZE);
			if(id == CategoryType.PETS.getType()) {
				
				Page<PetCategory> petCategory = this.petCategoryRepository.findAll(pageable);
				List<ItemObjectCategoryResponse> catList =  petCategory.get()
						   .map(itemObjectCategoryResponseConverter)
						   .collect(Collectors.toList());
				
						response.put(MessageType.Data.getMessage(), catList);
						categoryByType.setSuccess(true);
						response.put(MessageType.CurrentPage.getMessage(), petCategory.getNumber());
						response.put(MessageType.TotalItems.getMessage(),  petCategory.getTotalElements());
						response.put(MessageType.TotalPages.getMessage(),  petCategory.getTotalPages());
						
			}else if(id == CategoryType.SERVICE.getType()){
				Page<ServiceCategory> serviceCategory = this.serviceCategoryRepository.findAll(pageable);
				List<ItemObjectCategoryResponse> catList =  serviceCategory.get()
						   .map(itemObjectCategoryResponseConverter)
						   .collect(Collectors.toList());
				
						response.put(MessageType.Data.getMessage(), catList);
						response.put(MessageType.CurrentPage.getMessage(), serviceCategory.getNumber());
						response.put(MessageType.TotalItems.getMessage(),  serviceCategory.getTotalElements());
						response.put(MessageType.TotalPages.getMessage(),  serviceCategory.getTotalPages());
						categoryByType.setSuccess(true);
			}
			else {
				
		     Page<ItemObjectCategory> itemObjectCategory  = this.itemObjectRepository.findByType(id, pageable);
		     List<ItemObjectCategoryResponse> catList =  itemObjectCategory.get()
			   .map(itemObjectCategoryResponseConverter)
			   .collect(Collectors.toList());
			response.put(MessageType.Data.getMessage(), catList);
			response.put(MessageType.CurrentPage.getMessage(), itemObjectCategory.getNumber());
			response.put(MessageType.TotalItems.getMessage(),  itemObjectCategory.getTotalElements());
			response.put(MessageType.TotalPages.getMessage(),  itemObjectCategory.getTotalPages());
			categoryByType.setSuccess(true);
			}
		}catch(Exception ex) {
			response.put(MessageType.Data.getMessage(), ex.getMessage());
			categoryByType.setSuccess(false);
		}
		
		categoryByType.setResponse(response);
		categoryByType.setMsg(MessageType.Success.getMessage());
		
		return categoryByType;
	}
}
