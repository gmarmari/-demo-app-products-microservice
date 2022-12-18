package gmarmari.demo.microservices.products.repositories;

import gmarmari.demo.microservices.products.entities.ProductContactDao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductContactRepository extends JpaRepository<ProductContactDao, Long> {

    Optional<ProductContactDao> findByProductId(long productId);

    void deleteByProductId(long productId);

}
