package gmarmari.demo.microservices.products.entities;

import gmarmari.demo.microservices.products.entities.converters.SizeConverter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "product_info")
public class ProductInfoDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long productId;

    @NotNull
    @Convert(converter = SizeConverter.class)
    @Column(name="size")
    private SizeDao size;

    @NotNull
    private String countryOfOrigin;

    @Nullable
    private String description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public SizeDao getSize() {
        return size;
    }

    public void setSize(SizeDao size) {
        this.size = size;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductInfoDao that = (ProductInfoDao) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ProductInfoDao{" +
                "id=" + id +
                ", productId=" + productId +
                ", size=" + size +
                ", countryOfOrigin='" + countryOfOrigin + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
