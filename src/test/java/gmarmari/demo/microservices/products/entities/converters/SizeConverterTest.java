package gmarmari.demo.microservices.products.entities.converters;

import gmarmari.demo.microservices.products.entities.SizeDao;
import gmarmari.demo.microservices.products.entities.SizeUnitDao;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class SizeConverterTest {

    private static Stream<Arguments> convertToDatabaseColumnProvider() {
        return Stream.of(
                Arguments.of(null, ""),
                Arguments.of(SizeDao.EMPTY, "0.000-NONE"),
                Arguments.of(new SizeDao(1, SizeUnitDao.GRAMS), "1.000-GRAMS"),
                Arguments.of(new SizeDao(1.2, SizeUnitDao.GRAMS), "1.200-GRAMS"),
                Arguments.of(new SizeDao(1.23, SizeUnitDao.GRAMS), "1.230-GRAMS"),
                Arguments.of(new SizeDao(1.234, SizeUnitDao.GRAMS), "1.234-GRAMS"),
                Arguments.of(new SizeDao(1.2345, SizeUnitDao.GRAMS), "1.234-GRAMS"),
                Arguments.of(new SizeDao(123456789, SizeUnitDao.MILLI_LITER), "123456789.000-MILLI_LITER"),
                Arguments.of(new SizeDao(123456789.1, SizeUnitDao.MILLI_LITER), "123456789.100-MILLI_LITER"),
                Arguments.of(new SizeDao(123456789.12, SizeUnitDao.MILLI_LITER), "123456789.120-MILLI_LITER"),
                Arguments.of(new SizeDao(123456789.123, SizeUnitDao.MILLI_LITER), "123456789.123-MILLI_LITER"),
                Arguments.of(new SizeDao(123456789.1234, SizeUnitDao.MILLI_LITER), "123456789.123-MILLI_LITER")
        );
    }

    @ParameterizedTest
    @MethodSource("convertToDatabaseColumnProvider")
    void convertToDatabaseColumn(SizeDao size, String expected) {
        // Given
        SizeConverter converter = create();

        // When
        String actual = converter.convertToDatabaseColumn(size);

        // Then
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> convertToEntityAttributeProvider() {
        return Stream.of(
                Arguments.of(null, SizeDao.EMPTY),
                Arguments.of("", SizeDao.EMPTY),
                Arguments.of("abcdefg", SizeDao.EMPTY),
                Arguments.of("0.000-INVALID_UNIT", SizeDao.EMPTY),
                Arguments.of("0.000-NONE", SizeDao.EMPTY),
                Arguments.of( "1.000-GRAMS", new SizeDao(1, SizeUnitDao.GRAMS)),
                Arguments.of( "1.200-GRAMS", new SizeDao(1.2, SizeUnitDao.GRAMS)),
                Arguments.of( "1.230-GRAMS", new SizeDao(1.23, SizeUnitDao.GRAMS)),
                Arguments.of( "1.234-GRAMS", new SizeDao(1.234, SizeUnitDao.GRAMS)),
                Arguments.of( "123456789.000-MILLI_LITER", new SizeDao(123456789, SizeUnitDao.MILLI_LITER)),
                Arguments.of( "123456789.100-MILLI_LITER", new SizeDao(123456789.1, SizeUnitDao.MILLI_LITER)),
                Arguments.of( "123456789.120-MILLI_LITER", new SizeDao(123456789.12, SizeUnitDao.MILLI_LITER)),
                Arguments.of( "123456789.123-MILLI_LITER", new SizeDao(123456789.123, SizeUnitDao.MILLI_LITER))
        );
    }

    @ParameterizedTest
    @MethodSource("convertToEntityAttributeProvider")
    void convertToEntityAttribute(String value, SizeDao expected) {
        // Given
        SizeConverter converter = create();

        // When
        SizeDao actual = converter.convertToEntityAttribute(value);

        // Then
        assertThat(actual).isEqualTo(expected);
    }

    SizeConverter create() {
        return new SizeConverter();
    }

}