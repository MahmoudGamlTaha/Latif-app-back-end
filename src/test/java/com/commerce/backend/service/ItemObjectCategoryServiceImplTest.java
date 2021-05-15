package com.commerce.backend.service;

import com.commerce.backend.constants.SystemConstant;
import com.commerce.backend.converter.category.ItemObjectCategoryResponseConverter;
import com.commerce.backend.error.exception.ResourceNotFoundException;
import com.commerce.backend.model.dto.ItemObjectCategoryVO;
import com.commerce.backend.model.entity.ItemObjectCategory;
import com.commerce.backend.model.response.category.ItemObjectCategoryResponse;
import com.commerce.backend.service.cache.ItemObjectCategoryCacheService;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class ItemObjectCategoryServiceImplTest {

    @InjectMocks
    private ItemObjectCategoryServiceImpl productCategoryService;

    @Mock
    private ItemObjectCategoryCacheService productCategoryCacheService;

    @Mock
    private ItemObjectCategoryResponseConverter productCategoryResponseConverter;

    private Faker faker;

    @BeforeEach
    public void setUp() {
        faker = new Faker();
    }

    @Test
    void it_should_find_all_categories() {

        // given
        String categoryName = faker.lorem().word();
        ItemObjectCategory productCategory = new ItemObjectCategory();
        productCategory.setName(categoryName);
        ItemObjectCategoryResponse productCategoryResponse = new ItemObjectCategoryResponse();
        productCategoryResponse.setCategory(ItemObjectCategoryVO.builder().name(categoryName).build());

        List<ItemObjectCategory> productCategoryList = Stream.generate(() -> productCategory)
                .limit(faker.number().randomDigitNotZero())
                .collect(Collectors.toList());
        Pageable pageable1 = PageRequest.of(0, SystemConstant.MOBILE_PAGE_SIZE);
     //   given(productCategoryCacheService.findAllByOrderByName(pageable1)).willReturn(productCategoryList);
        given(productCategoryResponseConverter.apply(any(ItemObjectCategory.class))).willReturn(productCategoryResponse);

        // when
       // List<ItemObjectCategoryResponse> productCategoryResponseList = productCategoryService.findAllByOrderByName(pageable1);

        // then
       // then(productCategoryResponseList.size()).isEqualTo(productCategoryList.size());
       // productCategoryResponseList.forEach(productCategoryResponse1 -> then(productCategoryResponse1.getCategory().getName()).isEqualTo(productCategory.getName()));

    }

    @Test
    void it_should_throw_exception_when_no_category() {

        // given
    	Pageable pageable1 = PageRequest.of(0, SystemConstant.MOBILE_PAGE_SIZE);
      //  given(productCategoryCacheService.findAllByOrderByName(pageable1)).willReturn(Collections.emptyList());

        // when, then
        assertThatThrownBy(() -> productCategoryService.findAllByOrderByName(pageable1))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Could not find product categories");

    }

}