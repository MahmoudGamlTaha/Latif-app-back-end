package com.commerce.backend.api;

import com.commerce.backend.model.response.color.ProductColorResponse;
import com.commerce.backend.service.ProductColorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
@WebMvcTest(ColorController.class)
@AutoConfigureWebClient
@ComponentScan(basePackages = {"com.commerce.backend.constants"})
class ColorControllerTest {

    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    private ProductColorService productColorService;
    @Autowired
    private MockMvc mockMvc;
    private Faker faker;

    @BeforeEach
    public void setUp() {
        faker = new Faker();
    }

    @Test
    void it_should_get_all_colors() throws Exception {

        // given
        List<ProductColorResponse> productColorResponses = Stream.generate(ProductColorResponse::new)
                .limit(faker.number().randomDigitNotZero())
                .collect(Collectors.toList());

        given(productColorService.findAll()).willReturn(productColorResponses);

        // when
        MvcResult result = mockMvc.perform(get("/api/public/colors")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        // then
        verify(productColorService, times(1)).findAll();
        then(result.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(productColorResponses));
    }
}