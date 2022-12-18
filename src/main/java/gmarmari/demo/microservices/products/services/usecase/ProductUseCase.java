package gmarmari.demo.microservices.products.services.usecase;

import gmarmari.demo.microservices.products.entities.ProductContactDao;
import gmarmari.demo.microservices.products.entities.ProductDao;
import gmarmari.demo.microservices.products.entities.ProductDetailsDao;
import gmarmari.demo.microservices.products.entities.ProductInfoDao;
import gmarmari.demo.microservices.products.repositories.ProductContactRepository;
import gmarmari.demo.microservices.products.repositories.ProductInfoRepository;
import gmarmari.demo.microservices.products.repositories.ProductRepository;
import gmarmari.demo.microservices.products.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductUseCase implements ProductService {

    private final ProductRepository productRepository;
    private final ProductInfoRepository productInfoRepository;
    private final ProductContactRepository productContactRepository;

    @Autowired
    public ProductUseCase(ProductRepository productRepository,
                          ProductInfoRepository productInfoRepository,
                          ProductContactRepository productContactRepository) {
        this.productRepository = productRepository;
        this.productInfoRepository = productInfoRepository;
        this.productContactRepository = productContactRepository;
    }

    @Override
    public List<ProductDao> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<ProductDao> getProduct(long productId) {
        return productRepository.findById(productId);
    }

    @Override
    public Optional<ProductDetailsDao> getProductDetails(long productId) {
        Optional<ProductDao> product = productRepository.findById(productId);
        Optional<ProductInfoDao> info = productInfoRepository.findByProductId(productId);
        Optional<ProductContactDao> contact = productContactRepository.findByProductId(productId);

        if (product.isPresent() && info.isPresent() && contact.isPresent()) {
            return Optional.of(
                    new ProductDetailsDao(
                            product.get(),
                            info.get(),
                            contact.get()
                    )
            );
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void delete(long productId) {
        productInfoRepository.deleteByProductId(productId);
        productContactRepository.deleteByProductId(productId);
        productRepository.deleteById(productId);
    }

    @Override
    public void save(ProductDetailsDao productDetails) {
        productRepository.save(productDetails.product);
        productInfoRepository.save(productDetails.info);
        productContactRepository.save(productDetails.contact);
    }
}
