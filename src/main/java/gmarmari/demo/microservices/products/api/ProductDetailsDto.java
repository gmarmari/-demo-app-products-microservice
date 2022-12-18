package gmarmari.demo.microservices.products.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@Schema(name = "ProductDetails_V01")
public class ProductDetailsDto {

    @NotNull
    public final ProductDto product;

    @NotNull
    public final ProductInfoDto info;

    @NotNull
    public final ProductContactDto contact;

    public ProductDetailsDto(@NotNull @JsonProperty("product") ProductDto product,
                             @NotNull @JsonProperty("info")  ProductInfoDto info,
                             @NotNull @JsonProperty("contact")  ProductContactDto contact) {
        this.product = product;
        this.info = info;
        this.contact = contact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDetailsDto that = (ProductDetailsDto) o;
        return Objects.equals(product, that.product) && Objects.equals(info, that.info) && Objects.equals(contact, that.contact);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, info, contact);
    }

    @Override
    public String toString() {
        return "ProductDetailsDto{" +
                "product=" + product +
                ", info=" + info +
                ", contact=" + contact +
                '}';
    }
}
