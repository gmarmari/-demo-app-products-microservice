package gmarmari.demo.microservices.products.repositories;

import gmarmari.demo.microservices.products.entities.ProductDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
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


    @Test
    void findByNameContainingIgnoreCase() {
        // Given
        String name = "orange";
        ProductDao productA = aProductDao();
        productA.setName("Orange");
        entityManager.persist(productA);

        ProductDao productB = aProductDao();
        productB.setName("Fresh orange juice");
        entityManager.persist(productB);

        ProductDao productC = aProductDao();
        productC.setName("Apple pie");
        entityManager.persist(productC);

        ProductDao productD = aProductDao();
        productD.setName("Bio orange");
        entityManager.persist(productD);

        // When
        List<ProductDao> list = repository.findByNameContainingIgnoreCase(name, Sort.by("name").ascending());

        // Then
        assertThat(list).containsExactly(productD, productB, productA);
    }



}