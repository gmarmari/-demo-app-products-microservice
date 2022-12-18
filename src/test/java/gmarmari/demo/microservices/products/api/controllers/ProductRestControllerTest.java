package gmarmari.demo.microservices.products.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import gmarmari.demo.microservices.products.adapters.ProductAdapter;
import gmarmari.demo.microservices.products.api.ProductDetailsDto;
import gmarmari.demo.microservices.products.api.ProductDto;
import gmarmari.demo.microservices.products.api.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Optional;

import static gmarmari.demo.microservices.products.CommonDataFactory.aLong;
import static gmarmari.demo.microservices.products.ProductDataFactory.aProductDetailsDto;
import static gmarmari.demo.microservices.products.ProductDataFactory.aProductDto;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductRestController.class)
class ProductRestControllerTest {

    @MockBean
    private ProductAdapter adapter;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Test
    void getProducts() throws Exception {
        // Given
        List<ProductDto> list = List.of(aProductDto(), aProductDto());
        when(adapter.getProducts()).thenReturn(list);

        // When
        ResultActions resultActions = mockMvc.perform(get("/"));

        // Then
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(list)));
    }

    @Test
    void getProductById() throws Exception {
        // Given
        long productId = aLong();
        ProductDto dto = aProductDto(productId);
        when(adapter.getProduct(productId)).thenReturn(Optional.of(dto));

        // When
        ResultActions resultActions = mockMvc.perform(get("/{id}", productId));

        // Then
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(dto)));
    }

    @Test
    void getProductById_notFound() throws Exception {
        // Given
        long productId = 123;
        when(adapter.getProduct(productId)).thenReturn(Optional.empty());

        // When
        ResultActions resultActions = mockMvc.perform(get("/{productId}", productId));

        // Then
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    void getProductDetailsById() throws Exception {
        // Given
        long productId = aLong();
        ProductDetailsDto dto = aProductDetailsDto(productId);
        when(adapter.getProductDetails(productId)).thenReturn(Optional.of(dto));

        // When
        ResultActions resultActions = mockMvc.perform(get("/{productId}/details", productId));

        // Then
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(dto)));
    }

    @Test
    void getProductDetailsById_notFound() throws Exception {
        // Given
        long productId = 123;
        when(adapter.getProductDetails(productId)).thenReturn(Optional.empty());

        // When
        ResultActions resultActions = mockMvc.perform(get("/{productId}/details", productId));

        // Then
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    void deleteById() throws Exception {
        // Given
        long productId = 123;
        when(adapter.delete(productId)).thenReturn(Response.OK);

        // When
        ResultActions resultActions = mockMvc.perform(delete("/{id}", productId));

        // Then
        resultActions.andExpect(status().isOk());
    }

    @Test
    void deleteById_error() throws Exception {
        // Given
        long productId = 123;
        when(adapter.delete(productId)).thenReturn(Response.ERROR);

        // When
        ResultActions resultActions = mockMvc.perform(delete("/{id}", productId));

        // Then
        resultActions.andExpect(status().isInternalServerError());
    }

    @Test
    void saveProduct() throws Exception {
        // Given
        ProductDetailsDto dto = aProductDetailsDto();
        when(adapter.save(dto)).thenReturn(Response.OK);

        // When
        ResultActions resultActions = mockMvc.perform(post("/").
                contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(dto)));

        // Then
        resultActions.andExpect(status().isOk());
    }

    @Test
    void saveProduct_error() throws Exception {
        // Given
        ProductDetailsDto dto = aProductDetailsDto();
        when(adapter.save(dto)).thenReturn(Response.ERROR);

        // When
        ResultActions resultActions = mockMvc.perform(post("/").
                contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(dto)));

        // Then
        resultActions.andExpect(status().isInternalServerError());
    }

}