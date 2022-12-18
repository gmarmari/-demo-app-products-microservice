package gmarmari.demo.microservices.products.entities.converters;

import gmarmari.demo.microservices.products.entities.SizeDao;
import gmarmari.demo.microservices.products.entities.SizeUnitDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.AttributeConverter;
import java.text.DecimalFormat;

public class SizeConverter implements AttributeConverter<SizeDao, String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SizeConverter.class);

    private static final String SEPARATOR = "-";
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.000");


    @Override
    public String convertToDatabaseColumn(SizeDao sizeDao) {
        StringBuilder builder = new StringBuilder();
        if (sizeDao != null) {
            builder.append(DECIMAL_FORMAT.format(sizeDao.amount));
            builder.append(SEPARATOR);
            builder.append(sizeDao.unit.name());
        }
        return builder.toString();
    }



    @Override
    public SizeDao convertToEntityAttribute(String s) {
        if (s == null) {
            return SizeDao.EMPTY;
        }

        try {
            String[] parts = s.split(SEPARATOR);
            double amount = Double.parseDouble(parts[0]);
            SizeUnitDao unit = SizeUnitDao.valueOf(parts[1]);
            return new SizeDao(amount, unit);
        } catch (Exception e) {
            LOGGER.error("Error parsing SizeUnit from string: " + s);
            return SizeDao.EMPTY;
        }
    }
}
