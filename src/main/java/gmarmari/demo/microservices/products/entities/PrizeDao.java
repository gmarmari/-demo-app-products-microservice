package gmarmari.demo.microservices.products.entities;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class PrizeDao {

    public static final PrizeDao EMPTY = new PrizeDao(0, PrizeUnitDao.NONE);

    public final double amount;

    @NotNull
    public final PrizeUnitDao unit;

    public PrizeDao(double amount, PrizeUnitDao unit) {
        this.amount = amount;
        this.unit = unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrizeDao prizeDao = (PrizeDao) o;
        return Double.compare(prizeDao.amount, amount) == 0 && unit == prizeDao.unit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, unit);
    }

    @Override
    public String toString() {
        return "PrizeDao{" +
                "amount=" + amount +
                ", unit=" + unit +
                '}';
    }
}
