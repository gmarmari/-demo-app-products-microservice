package gmarmari.demo.microservices.products.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Schema(name = "ProductInfo_V01")
public class ProductInfoDto {

    public final long id;

    public final long productId;

    @NotNull
    public final SizeDto size;

    @NotBlank
    @Size(max = 100)
    public final String countryOfOrigin;

    @Nullable
    @Size(max = 1000)
    public final String description;

    public ProductInfoDto(@JsonProperty("id") long id,
                          @JsonProperty("productId") long productId,
                          @NotNull @JsonProperty("size") SizeDto size,
                          @NotNull @JsonProperty("countryOfOrigin") String countryOfOrigin,
                          @Nullable @JsonProperty("description") String description) {
        this.id = id;
        this.productId = productId;
        this.size = size;
        this.countryOfOrigin = countryOfOrigin;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductInfoDto that = (ProductInfoDto) o;
        return id == that.id && productId == that.productId && Objects.equals(size, that.size) && Objects.equals(countryOfOrigin, that.countryOfOrigin) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productId, size, countryOfOrigin, description);
    }

    @Override
    public String toString() {
        return "ProductInfoDto{" +
                "id=" + id +
                ", productId=" + productId +
                ", size=" + size +
                ", countryOfOrigin='" + countryOfOrigin + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
