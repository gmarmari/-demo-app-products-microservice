package gmarmari.demo.microservices.products.entities;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class SizeDao {

    public static final SizeDao EMPTY = new SizeDao(0, SizeUnitDao.NONE);

    public final double amount;

    @NotNull
    public final SizeUnitDao unit;

    public SizeDao(double amount, SizeUnitDao unit) {
        this.amount = amount;
        this.unit = unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SizeDao sizeDao = (SizeDao) o;
        return Double.compare(sizeDao.amount, amount) == 0 && unit == sizeDao.unit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, unit);
    }

    @Override
    public String toString() {
        return "SizeDao{" +
                "amount=" + amount +
                ", unit=" + unit +
                '}';
    }
}
