package gmarmari.demo.microservices.products.entities.converters;


import gmarmari.demo.microservices.products.entities.PrizeDao;
import gmarmari.demo.microservices.products.entities.PrizeUnitDao;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class PrizeConverterTest {

    private static Stream<Arguments> convertToDatabaseColumnProvider() {
        return Stream.of(
                Arguments.of(null, ""),
                Arguments.of(PrizeDao.EMPTY, "0.00-NONE"),
                Arguments.of(new PrizeDao(1, PrizeUnitDao.EURO), "1.00-EURO"),
                Arguments.of(new PrizeDao(1.2, PrizeUnitDao.EURO), "1.20-EURO"),
                Arguments.of(new PrizeDao(1.23, PrizeUnitDao.EURO), "1.23-EURO"),
                Arguments.of(new PrizeDao(1.234, PrizeUnitDao.EURO), "1.23-EURO"),
                Arguments.of(new PrizeDao(123456789, PrizeUnitDao.DOLLAR), "123456789.00-DOLLAR"),
                Arguments.of(new PrizeDao(123456789.1, PrizeUnitDao.DOLLAR), "123456789.10-DOLLAR"),
                Arguments.of(new PrizeDao(123456789.12, PrizeUnitDao.DOLLAR), "123456789.12-DOLLAR"),
                Arguments.of(new PrizeDao(123456789.123, PrizeUnitDao.DOLLAR), "123456789.12-DOLLAR")
        );
    }

    @ParameterizedTest
    @MethodSource("convertToDatabaseColumnProvider")
    void convertToDatabaseColumn(PrizeDao prize, String expected) {
        // Given
        PrizeConverter converter = create();

        // When
        String actual = converter.convertToDatabaseColumn(prize);

        // Then
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> convertToEntityAttributeProvider() {
        return Stream.of(
                Arguments.of(null, PrizeDao.EMPTY),
                Arguments.of("", PrizeDao.EMPTY),
                Arguments.of("abcdefg", PrizeDao.EMPTY),
                Arguments.of("0.00-INVALID_UNIT", PrizeDao.EMPTY),
                Arguments.of("0.00-NONE", PrizeDao.EMPTY),
                Arguments.of( "1.00-EURO", new PrizeDao(1, PrizeUnitDao.EURO)),
                Arguments.of( "1.20-EURO", new PrizeDao(1.2, PrizeUnitDao.EURO)),
                Arguments.of( "1.23-EURO", new PrizeDao(1.23, PrizeUnitDao.EURO)),
                Arguments.of( "123456789.00-DOLLAR", new PrizeDao(123456789, PrizeUnitDao.DOLLAR)),
                Arguments.of( "123456789.10-DOLLAR", new PrizeDao(123456789.1, PrizeUnitDao.DOLLAR)),
                Arguments.of( "123456789.12-DOLLAR", new PrizeDao(123456789.12, PrizeUnitDao.DOLLAR))
        );
    }

    @ParameterizedTest
    @MethodSource("convertToEntityAttributeProvider")
    void convertToEntityAttribute(String value, PrizeDao expected) {
        // Given
        PrizeConverter converter = create();

        // When
        PrizeDao actual = converter.convertToEntityAttribute(value);

        // Then
        assertThat(actual).isEqualTo(expected);
    }

    PrizeConverter create() {
        return new PrizeConverter();
    }

}