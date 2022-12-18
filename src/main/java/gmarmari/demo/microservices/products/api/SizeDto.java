package gmarmari.demo.microservices.products.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Schema(name = "Size_V01")
public class SizeDto {

    @Size(min = 0)
    public final double amount;

    @NotNull
    public final SizeUnitDto unit;

    public SizeDto(@JsonProperty("amount") double amount,
                   @NotNull @JsonProperty("unit") SizeUnitDto unit) {
        this.amount = amount;
        this.unit = unit;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SizeDto sizeDto = (SizeDto) o;
        return Double.compare(sizeDto.amount, amount) == 0 && unit == sizeDto.unit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, unit);
    }

    @Override
    public String toString() {
        return "SizeDto{" +
                "amount=" + amount +
                ", unit=" + unit +
                '}';
    }
}
