package gmarmari.demo.microservices.products.adapters;

import gmarmari.demo.microservices.products.api.*;
import gmarmari.demo.microservices.products.entities.ProductContactDao;
import gmarmari.demo.microservices.products.entities.ProductDao;
import gmarmari.demo.microservices.products.entities.ProductDetailsDao;
import gmarmari.demo.microservices.products.entities.ProductInfoDao;
import gmarmari.demo.microservices.products.services.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
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
class ProductAdapterTest {

    @Mock
    private ProductService service;

    @Captor
    private ArgumentCaptor<ProductDetailsDao> productDetailsDaoCaptor;

    @InjectMocks
    private ProductAdapter adapter;

    @Test
    void getProducts() {
        // Given
        ProductDao daoA = aProductDao(true);
        ProductDao daoB = aProductDao(true);
        ProductDao daoC = aProductDao(true);

        when(service.getProducts()).thenReturn(List.of(daoA, daoB, daoC));

        // When
        List<ProductDto> list = adapter.getProducts();

        // Then
        assertThat(list).hasSize(3);
        verifyProduct(list.get(0), daoA);
        verifyProduct(list.get(1), daoB);
        verifyProduct(list.get(2), daoC);

        verifyNoMoreInteractions(service);
    }

    @Test
    void getProduct() {
        // Given
        long productId = aLong();
        ProductDao dao = aProductDao();
        dao.setId(productId);

        when(service.getProduct(productId)).thenReturn(Optional.of(dao));

        // When
        Optional<ProductDto> result = adapter.getProduct(productId);

        // Then
        assertThat(result).isPresent();
        verifyProduct(result.get(), dao);

        verifyNoMoreInteractions(service);
    }

    @Test
    void getProductDetails() {
        // Given
        long productId = aLong();
        ProductDetailsDao dao = aProductDetailsDao(productId);

        when(service.getProductDetails(productId)).thenReturn(Optional.of(dao));

        // When
        Optional<ProductDetailsDto> result = adapter.getProductDetails(productId);

        // Then
        assertThat(result).isPresent();
        verifyProduct(result.get().product, dao.product);
        verifyProductInfo(result.get().info, dao.info);
        verifyProductContact(result.get().contact, dao.contact);

        verifyNoMoreInteractions(service);
    }

    @Test
    void delete() {
        // Given
        long productId = aLong();

        // When
        Response response = adapter.delete(productId);

        // Then
        assertThat(response).isEqualTo(Response.OK);
        verify(service).delete(productId);
        verifyNoMoreInteractions(service);
    }

    @Test
    void delete_error() {
        // Given
        long productId = aLong();

        doThrow(new NullPointerException()).when(service).delete(productId);

        // When
        Response response = adapter.delete(productId);

        // Then
        assertThat(response).isEqualTo(Response.ERROR);
        verify(service).delete(productId);
        verifyNoMoreInteractions(service);
    }

    @Test
    void save() {
        // Given
        long productId = aLong();
        ProductDetailsDto dto = aProductDetailsDto(productId);

        // When
        Response result = adapter.save(dto);

        // Then
        assertThat(result).isEqualTo(Response.OK);

        verify(service).save(productDetailsDaoCaptor.capture());
        verifyNoMoreInteractions(service);
        ProductDetailsDao dao = productDetailsDaoCaptor.getValue();

        verifyProduct(dto.product, dao.product);
        verifyProductInfo(dto.info, dao.info);
        verifyProductContact(dto.contact, dao.contact);
    }

    @Test
    void save_error() {
        // Given
        long productId = aLong();
        ProductDetailsDto dto = aProductDetailsDto(productId);

        doThrow(new NullPointerException()).when(service).save(any());

        // When
        Response result = adapter.save(dto);

        // Then
        assertThat(result).isEqualTo(Response.ERROR);
        verifyNoMoreInteractions(service);
    }


    private void verifyProduct(ProductDto dto, ProductDao dao) {
        assertThat(dto.id).isEqualTo(dao.getId());
        assertThat(dto.name).isEqualTo(dao.getName());
        assertThat(dto.amount).isEqualTo(dao.getAmount());
        assertThat(dto.prize.amount).isEqualTo(dao.getPrize().amount);
        assertThat(dto.prize.unit.name()).isEqualTo(dao.getPrize().unit.name());
    }

    private void verifyProductInfo(ProductInfoDto dto, ProductInfoDao dao) {
        assertThat(dto.id).isEqualTo(dao.getId());
        assertThat(dto.productId).isEqualTo(dao.getProductId());
        assertThat(dto.countryOfOrigin).isEqualTo(dao.getCountryOfOrigin());
        assertThat(dto.description).isEqualTo(dao.getDescription());
        assertThat(dto.size.amount).isEqualTo(dao.getSize().amount);
        assertThat(dto.size.unit.name()).isEqualTo(dao.getSize().unit.name());
    }

    private void verifyProductContact(ProductContactDto dto, ProductContactDao dao) {
        assertThat(dto.id).isEqualTo(dao.getId());
        assertThat(dto.productId).isEqualTo(dao.getProductId());
        assertThat(dto.name).isEqualTo(dao.getName());
        assertThat(dto.address).isEqualTo(dao.getAddress());
        assertThat(dto.tel).isEqualTo(dao.getTel());
        assertThat(dto.email).isEqualTo(dao.getEmail());
        assertThat(dto.website).isEqualTo(dao.getWebsite());
    }

}