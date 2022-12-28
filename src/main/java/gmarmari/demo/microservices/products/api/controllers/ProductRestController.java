package gmarmari.demo.microservices.products.api.controllers;

import gmarmari.demo.microservices.products.adapters.ProductAdapter;
import gmarmari.demo.microservices.products.api.ProductDetailsDto;
import gmarmari.demo.microservices.products.api.ProductDto;
import gmarmari.demo.microservices.products.api.ProductNotFoundException;
import gmarmari.demo.microservices.products.api.ProductsApi;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class ProductRestController implements ProductsApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductRestController.class);

    private final ProductAdapter adapter;

    @Autowired
    public ProductRestController(ProductAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    @CircuitBreaker(name = "breaker", fallbackMethod = "getProductsFallback")
    public List<ProductDto> getProducts() {
        return adapter.getProducts();
    }

    public List<ProductDto> getProductsFallback(Throwable t) {
        LOGGER.warn("Fallback method for getProducts", t);
        return List.of();
    }

    @Override
    @CircuitBreaker(name = "breaker", fallbackMethod = "getProductsFromIdsFallback")
    public List<ProductDto> getProductsFromIds(String productIds) {
        return adapter.getProductsFromIds(productIds);
    }

    public List<ProductDto> getProductsFromIdsFallback(String productIds, Throwable t) {
        LOGGER.warn("Fallback method for getProductsFromIds with input: " + productIds, t);
        return List.of();
    }

    @Override
    @CircuitBreaker(name = "breaker", fallbackMethod = "findProductsByNameFallback")
    public List<ProductDto> findProductsByName(String name) {
        return adapter.findProductsByName(name);
    }

    public List<ProductDto> findProductsByNameFallback(String name, Throwable t) {
        LOGGER.warn("Fallback method for findProductsByName with input: " + name, t);
        return List.of();
    }

    @Override
    public ProductDto getProductById(long productId) {
        return adapter.getProduct(productId)
                .orElseThrow(ProductNotFoundException::new);
    }

    @Override
    public ProductDetailsDto getProductDetailsById(long productId) {
        return adapter.getProductDetails(productId)
                .orElseThrow(ProductNotFoundException::new);
    }

    @Override
    public void deleteById(long productId) {
        adapter.delete(productId)
                .throwIfError(() -> new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "An error occurred by deleting the product"));
    }

    @Override
    public void saveProduct(ProductDetailsDto productDetails) {
        adapter.save(productDetails)
                .throwIfError(() -> new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "An error occurred by saving the product"));
    }
}
