package gmarmari.demo.microservices.products.repositories;

import gmarmari.demo.microservices.products.entities.ProductDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductDao, Long> {

}
