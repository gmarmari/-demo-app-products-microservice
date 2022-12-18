package gmarmari.demo.microservices.products.repositories;

import gmarmari.demo.microservices.products.entities.ProductInfoDao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductInfoRepository extends JpaRepository<ProductInfoDao, Long> {

    Optional<ProductInfoDao> findByProductId(long productId);

    void deleteByProductId(long productId);

}
