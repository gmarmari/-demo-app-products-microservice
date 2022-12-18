package gmarmari.demo.microservices.products.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Schema(name = "Prize_V01")
public class PrizeDto {

    @Size(min = 0)
    public final double amount;

    @NotNull
    public final PrizeUnitDto unit;

    public PrizeDto(@JsonProperty("amount") double amount,
                    @NotNull @JsonProperty("unit") PrizeUnitDto unit) {
        this.amount = amount;
        this.unit = unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrizeDto prizeDto = (PrizeDto) o;
        return Double.compare(prizeDto.amount, amount) == 0 && unit == prizeDto.unit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, unit);
    }

    @Override
    public String toString() {
        return "PrizeDto{" +
                "amount=" + amount +
                ", unit=" + unit +
                '}';
    }
}
