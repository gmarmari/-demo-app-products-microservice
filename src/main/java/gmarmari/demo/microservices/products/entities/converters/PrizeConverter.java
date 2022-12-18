package gmarmari.demo.microservices.products.entities.converters;

import gmarmari.demo.microservices.products.entities.PrizeDao;
import gmarmari.demo.microservices.products.entities.PrizeUnitDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.AttributeConverter;
import java.text.DecimalFormat;

public class PrizeConverter implements AttributeConverter<PrizeDao, String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrizeConverter.class);

    private static final String SEPARATOR = "-";
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");

    @Override
    public String convertToDatabaseColumn(PrizeDao prizeDao) {
        StringBuilder builder = new StringBuilder();
        if (prizeDao != null) {
            builder.append(DECIMAL_FORMAT.format(prizeDao.amount));
            builder.append(SEPARATOR);
            builder.append(prizeDao.unit.name());
        }
        return builder.toString();
    }

    @Override
    public PrizeDao convertToEntityAttribute(String s) {
        if (s == null) {
            return PrizeDao.EMPTY;
        }

        try {
            String[] parts = s.split(SEPARATOR);
            double amount = Double.parseDouble(parts[0]);
            PrizeUnitDao unit = PrizeUnitDao.valueOf(parts[1]);
            return new PrizeDao(amount, unit);
        } catch (Exception e) {
            LOGGER.error("Error parsing PrizeUnit from string: " + s);
            return PrizeDao.EMPTY;
        }
    }
}
