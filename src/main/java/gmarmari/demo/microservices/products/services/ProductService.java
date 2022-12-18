package gmarmari.demo.microservices.products.services;

import gmarmari.demo.microservices.products.entities.ProductDao;
import gmarmari.demo.microservices.products.entities.ProductDetailsDao;
import gmarmari.demo.microservices.products.entities.ProductDao;
import gmarmari.demo.microservices.products.entities.ProductDetailsDao;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<ProductDao> getProducts();

    Optional<ProductDao> getProduct(long productId);


    Optional<ProductDetailsDao> getProductDetails(long productId);

    void delete(long productId);

    void save(ProductDetailsDao productDetails);

}
