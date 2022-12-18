package gmarmari.demo.microservices.products.entities;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class ProductDetailsDao {

    @NotNull
    public final ProductDao product;

    @NotNull
    public final ProductInfoDao info;

    @NotNull
    public final ProductContactDao contact;

    public ProductDetailsDao(ProductDao product, ProductInfoDao info, ProductContactDao contact) {
        this.product = product;
        this.info = info;
        this.contact = contact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDetailsDao that = (ProductDetailsDao) o;
        return Objects.equals(product, that.product) && Objects.equals(info, that.info) && Objects.equals(contact, that.contact);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, info, contact);
    }

    @Override
    public String toString() {
        return "ProductDetailsDao{" +
                "product=" + product +
                ", info=" + info +
                ", contact=" + contact +
                '}';
    }
}
