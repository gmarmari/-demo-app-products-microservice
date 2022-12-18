package gmarmari.demo.microservices.products.repositories;


import gmarmari.demo.microservices.products.entities.ProductInfoDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static gmarmari.demo.microservices.products.ProductDataFactory.aProductDao;
import static gmarmari.demo.microservices.products.ProductDataFactory.aProductInfoDao;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@DataJpaTest
class ProductInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void save_findById() {
        // Given
        Long productId = entityManager.persistAndGetId(aProductDao(), Long.class);

        ProductInfoDao info = aProductInfoDao();
        info.setProductId(productId);
        Long id = entityManager.persistAndGetId(info, Long.class);

        // When
        Optional<ProductInfoDao> resultOptional = repository.findById(id);

        // Then
        assertThat(resultOptional).isPresent();
        ProductInfoDao result = resultOptional.get();

        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getProductId()).isEqualTo(productId);
        assertThat(result.getCountryOfOrigin()).isEqualTo(info.getCountryOfOrigin());
        assertThat(result.getSize()).isEqualTo(info.getSize());
        assertThat(result.getDescription()).isEqualTo(info.getDescription());
    }

    @Test
    void save_findByProductId() {
        // Given
        Long productId = entityManager.persistAndGetId(aProductDao(), Long.class);

        ProductInfoDao info = aProductInfoDao();
        info.setProductId(productId);
        long id = entityManager.persistAndGetId(info, Long.class);

        // When
        Optional<ProductInfoDao> resultOptional = repository.findByProductId(productId);

        // Then
        assertThat(resultOptional).isPresent();
        ProductInfoDao result = resultOptional.get();

        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getProductId()).isEqualTo(productId);
        assertThat(result.getCountryOfOrigin()).isEqualTo(info.getCountryOfOrigin());
        assertThat(result.getSize()).isEqualTo(info.getSize());
        assertThat(result.getDescription()).isEqualTo(info.getDescription());
    }

    @Test
    void deleteByProductId() {
        // Given
        Long productId = entityManager.persistAndGetId(aProductDao(), Long.class);

        ProductInfoDao info = aProductInfoDao();
        info.setProductId(productId);
        entityManager.persistAndGetId(info, Long.class);

        // When
        repository.deleteByProductId(productId);

        // Then
        assertThat(repository.findByProductId(productId)).isEmpty();
    }

    @Test
    void productId_uniqueOnProductInfoTable() {
        // Given
        Long productId = entityManager.persistAndGetId(aProductDao(), Long.class);

        ProductInfoDao info = aProductInfoDao();
        info.setProductId(productId);
        entityManager.persistAndGetId(info, Long.class);

        ProductInfoDao anotherInfo = aProductInfoDao();
        anotherInfo.setProductId(productId);

        // When
        // Then
        assertThrows(DataIntegrityViolationException.class, () -> repository.save(anotherInfo));
    }

}