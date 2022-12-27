package gmarmari.demo.microservices.products.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Schema(name = "ProductContanct_V01")
public class ProductContactDto {

    public final long id;

    public final long productId;

    @NotBlank
    @Size(max = 100)
    public final String name;

    @NotBlank
    @Size(max = 200)
    public final String address;

    @Nullable
    @Size(max = 32)
    public final String tel;

    @Nullable
    @Size(max = 100)
    public final String email;

    @Nullable
    @Size(max = 100)
    public final String website;

    public ProductContactDto(@JsonProperty("id") long id,
                             @JsonProperty("productId") long productId,
                             @NotNull @JsonProperty("name")  String name,
                             @NotNull @JsonProperty("address") String address,
                             @Nullable @JsonProperty("tel") String tel,
                             @Nullable @JsonProperty("email") String email,
                             @Nullable @JsonProperty("website") String website) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.address = address;
        this.tel = tel;
        this.email = email;
        this.website = website;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductContactDto that = (ProductContactDto) o;
        return id == that.id && productId == that.productId && Objects.equals(name, that.name) && Objects.equals(address, that.address) && Objects.equals(tel, that.tel) && Objects.equals(email, that.email) && Objects.equals(website, that.website);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productId, name, address, tel, email, website);
    }

    @Override
    public String toString() {
        return "ProductContactDto{" +
                "id=" + id +
                ", productId=" + productId +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", tel='" + tel + '\'' +
                ", email='" + email + '\'' +
                ", website='" + website + '\'' +
                '}';
    }
}
