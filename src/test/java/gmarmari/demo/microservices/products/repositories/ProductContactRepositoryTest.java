package gmarmari.demo.microservices.products.repositories;

import gmarmari.demo.microservices.products.entities.ProductContactDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static gmarmari.demo.microservices.products.ProductDataFactory.aProductContactDao;
import static gmarmari.demo.microservices.products.ProductDataFactory.aProductDao;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ActiveProfiles("test")
@DataJpaTest
class ProductContactRepositoryTest {

    @Autowired
    private ProductContactRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void save_findById() {
        // Given
        Long productId = entityManager.persistAndGetId(aProductDao(), Long.class);

        ProductContactDao contact = aProductContactDao();
        contact.setProductId(productId);
        Long id = entityManager.persistAndGetId(contact, Long.class);

        // When
        Optional<ProductContactDao> resultOptional = repository.findById(id);

        // Then
        assertThat(resultOptional).isPresent();
        ProductContactDao result = resultOptional.get();

        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getProductId()).isEqualTo(productId);
        assertThat(result.getName()).isEqualTo(contact.getName());
        assertThat(result.getAddress()).isEqualTo(contact.getAddress());
        assertThat(result.getTel()).isEqualTo(contact.getTel());
        assertThat(result.getEmail()).isEqualTo(contact.getEmail());
        assertThat(result.getWebsite()).isEqualTo(contact.getWebsite());
    }

    @Test
    void save_findByProductId() {
        // Given
        Long productId = entityManager.persistAndGetId(aProductDao(), Long.class);

        ProductContactDao contact = aProductContactDao();
        contact.setProductId(productId);
        Long id = entityManager.persistAndGetId(contact, Long.class);

        // When
        Optional<ProductContactDao> resultOptional = repository.findByProductId(productId);

        // Then
        assertThat(resultOptional).isPresent();
        ProductContactDao result = resultOptional.get();

        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getProductId()).isEqualTo(productId);
        assertThat(result.getName()).isEqualTo(contact.getName());
        assertThat(result.getAddress()).isEqualTo(contact.getAddress());
        assertThat(result.getTel()).isEqualTo(contact.getTel());
        assertThat(result.getEmail()).isEqualTo(contact.getEmail());
        assertThat(result.getWebsite()).isEqualTo(contact.getWebsite());
    }

    @Test
    void deleteByProductId() {
        // Given
        Long productId = entityManager.persistAndGetId(aProductDao(), Long.class);

        ProductContactDao contact = aProductContactDao();
        contact.setProductId(productId);
        entityManager.persistAndGetId(contact, Long.class);

        // When
        repository.deleteByProductId(productId);

        // Then
        assertThat(repository.findByProductId(productId)).isEmpty();
    }

    @Test
    void productId_uniqueOnProductContactTable() {
        // Given
        Long productId = entityManager.persistAndGetId(aProductDao(), Long.class);

        ProductContactDao contact = aProductContactDao();
        contact.setProductId(productId);
        entityManager.persistAndGetId(contact, Long.class);

        ProductContactDao anotherContact = aProductContactDao();
        anotherContact.setProductId(productId);

        // When
        // Then
        assertThrows(DataIntegrityViolationException.class, () -> repository.save(anotherContact));
    }

}