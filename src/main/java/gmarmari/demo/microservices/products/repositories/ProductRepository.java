package gmarmari.demo.microservices.products.repositories;

import gmarmari.demo.microservices.products.entities.ProductDao;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductDao, Long> {

    List<ProductDao> findByNameContainingIgnoreCase(String name, Sort sort);

}
