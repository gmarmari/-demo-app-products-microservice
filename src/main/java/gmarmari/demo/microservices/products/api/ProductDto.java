package gmarmari.demo.microservices.products.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Schema(name = "Product_V01")
public class ProductDto {
    public final long id;
    @NotBlank
    @Size(max = 100)
    public final String name;
    @NotNull
    public final PrizeDto prize;

    public ProductDto(@JsonProperty("id") long id,
                      @NotNull @JsonProperty("name") String name,
                      @NotNull @JsonProperty("prize")  PrizeDto prize) {
        this.id = id;
        this.name = name;
        this.prize = prize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDto that = (ProductDto) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(prize, that.prize);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, prize);
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", prize=" + prize +
                '}';
    }
}
