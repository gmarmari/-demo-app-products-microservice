package gmarmari.demo.microservices.products.services.usecase;

import gmarmari.demo.microservices.products.entities.ProductContactDao;
import gmarmari.demo.microservices.products.entities.ProductDao;
import gmarmari.demo.microservices.products.entities.ProductDetailsDao;
import gmarmari.demo.microservices.products.entities.ProductInfoDao;
import gmarmari.demo.microservices.products.repositories.ProductContactRepository;
import gmarmari.demo.microservices.products.repositories.ProductInfoRepository;
import gmarmari.demo.microservices.products.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static gmarmari.demo.microservices.products.CommonDataFactory.aLong;
import static gmarmari.demo.microservices.products.ProductDataFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductUseCaseTest {


    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductInfoRepository productInfoRepository;
    @Mock
    private ProductContactRepository productContactRepository;

    @InjectMocks
    private ProductUseCase useCase;

    @Test
    void getProducts() {
        // Given
        ProductDao productA = aProductDao(true);
        ProductDao productB = aProductDao(true);
        ProductDao productC = aProductDao(true);

        when(productRepository.findAll()).thenReturn(List.of(productA, productB, productC));

        // When
        List<ProductDao> list = useCase.getProducts();

        // Then
        assertThat(list).containsExactly(productA, productB, productC);
        verifyNoMoreInteractions(productRepository);
        verifyNoInteractions(productInfoRepository);
        verifyNoInteractions(productContactRepository);
    }

    @Test
    void getProduct() {
        // Given
        ProductDao product = aProductDao();

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        // When
        Optional<ProductDao> result = useCase.getProduct(product.getId());

        // Then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(product);
        verifyNoMoreInteractions(productRepository);
        verifyNoInteractions(productInfoRepository);
        verifyNoInteractions(productContactRepository);
    }

    @Test
    void getProductDetails() {
        // Given
        ProductDao product = aProductDao(true);
        ProductInfoDao info = aProductInfoDao(true);
        info.setProductId(product.getId());

        ProductContactDao contact = aProductContactDao(true);
        contact.setProductId(product.getId());

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(productInfoRepository.findByProductId(product.getId())).thenReturn(Optional.of(info));
        when(productContactRepository.findByProductId(product.getId())).thenReturn(Optional.of(contact));

        // When
        Optional<ProductDetailsDao> result = useCase.getProductDetails(product.getId());

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().product).isEqualTo(product);
        assertThat(result.get().info).isEqualTo(info);
        assertThat(result.get().contact).isEqualTo(contact);

        verifyNoMoreInteractions(productRepository);
        verifyNoMoreInteractions(productInfoRepository);
        verifyNoMoreInteractions(productContactRepository);
    }

    @Test
    void getProductDetails_noProduct() {
        // Given
        long productId = aLong();
        ProductInfoDao info = aProductInfoDao(true);
        info.setProductId(productId);

        ProductContactDao contact = aProductContactDao(true);
        contact.setProductId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.empty());
        when(productInfoRepository.findByProductId(productId)).thenReturn(Optional.of(info));
        when(productContactRepository.findByProductId(productId)).thenReturn(Optional.of(contact));

        // When
        Optional<ProductDetailsDao> result = useCase.getProductDetails(productId);

        // Then
        assertThat(result).isEmpty();

        verifyNoMoreInteractions(productRepository);
        verifyNoMoreInteractions(productInfoRepository);
        verifyNoMoreInteractions(productContactRepository);
    }

    @Test
    void getProductDetail_noInfo() {
        // Given
        ProductDao product = aProductDao(true);

        ProductContactDao contact = aProductContactDao(true);
        contact.setProductId(product.getId());

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(productInfoRepository.findByProductId(product.getId())).thenReturn(Optional.empty());
        when(productContactRepository.findByProductId(product.getId())).thenReturn(Optional.of(contact));

        // When
        Optional<ProductDetailsDao> result = useCase.getProductDetails(product.getId());

        // Then
        assertThat(result).isEmpty();

        verifyNoMoreInteractions(productRepository);
        verifyNoMoreInteractions(productInfoRepository);
        verifyNoMoreInteractions(productContactRepository);
    }

    @Test
    void getProductDetails_noContact() {
        // Given
        ProductDao product = aProductDao(true);
        ProductInfoDao info = aProductInfoDao(true);
        info.setProductId(product.getId());

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(productInfoRepository.findByProductId(product.getId())).thenReturn(Optional.of(info));
        when(productContactRepository.findByProductId(product.getId())).thenReturn(Optional.empty());

        // When
        Optional<ProductDetailsDao> result = useCase.getProductDetails(product.getId());

        // Then
        assertThat(result).isEmpty();

        verifyNoMoreInteractions(productRepository);
        verifyNoMoreInteractions(productInfoRepository);
        verifyNoMoreInteractions(productContactRepository);
    }

    @Test
    void delete() {
        // Given
        long productId = aLong();

        // When
        useCase.delete(productId);

        // Then
        verify(productRepository).deleteById(productId);
        verifyNoMoreInteractions(productRepository);

        verify(productInfoRepository).deleteByProductId(productId);
        verifyNoMoreInteractions(productInfoRepository);

        verify(productContactRepository).deleteByProductId(productId);
        verifyNoMoreInteractions(productContactRepository);
    }

    @Test
    void save() {
        // Given
        ProductDetailsDao productDetails = aProductDetailsDao();

        // When
        useCase.save(productDetails);

        // Then
        verify(productRepository).save(productDetails.product);
        verifyNoMoreInteractions(productRepository);

        verify(productInfoRepository).save(productDetails.info);
        verifyNoMoreInteractions(productInfoRepository);

        verify(productContactRepository).save(productDetails.contact);
        verifyNoMoreInteractions(productContactRepository);
    }


}