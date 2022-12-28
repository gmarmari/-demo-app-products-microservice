package gmarmari.demo.microservices.products.repositories;

import gmarmari.demo.microservices.products.entities.ProductDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static gmarmari.demo.microservices.products.ProductDataFactory.aProductDao;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void save_findById() {
        // Given
        ProductDao product = aProductDao();
        Long id = entityManager.persistAndGetId(product, Long.class);

        // When
        Optional<ProductDao> resultOptional = repository.findById(id);

        // Then
        assertThat(resultOptional).isPresent();
        ProductDao result = resultOptional.get();

        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getName()).isEqualTo(product.getName());
        assertThat(result.getPrize()).isEqualTo(product.getPrize());
    }


}