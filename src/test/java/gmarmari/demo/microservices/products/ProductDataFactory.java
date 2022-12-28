package gmarmari.demo.microservices.products;

import gmarmari.demo.microservices.products.api.*;
import gmarmari.demo.microservices.products.entities.*;

import static gmarmari.demo.microservices.products.CommonDataFactory.*;

@SuppressWarnings("unused")
public class ProductDataFactory {

    private ProductDataFactory() {
        // Hide constructor
    }

    //region dto

    public static ProductDto aProductDto() {
        return aProductDto(aLong());
    }

    public static ProductDto aProductDto(long productId) {
        return new ProductDto(
                productId,
                aText(),
                aPrizeDto()
        );
    }

    public static ProductDetailsDto aProductDetailsDto() {
        return aProductDetailsDto(aLong());
    }

    public static ProductDetailsDto aProductDetailsDto(long productId) {
        return new ProductDetailsDto(
                aProductDto(productId),
                aProductInfoDto(productId),
                aProductContactDto(productId)
        );
    }

    public static ProductInfoDto aProductInfoDto() {
        return aProductInfoDto(aLong());
    }

    public static ProductInfoDto aProductInfoDto(long productId) {
        return new ProductInfoDto(
                aLong(),
                productId,
                aSizeDto(),
                aCountry(),
                aNullableText()
        );
    }

    public static ProductContactDto aProductContactDto() {
        return aProductContactDto(aLong());
    }

    public static ProductContactDto aProductContactDto(long productId) {
        return new ProductContactDto(
                aLong(),
                productId,
                aText(),
                aText(),
                aNullableText(),
                aNullableText(),
                aNullableText()
        );
    }

    public static PrizeDto aPrizeDto() {
        int index = aInt(PrizeUnitDto.values().length);
        return new PrizeDto(aDouble(), PrizeUnitDto.values()[index]);
    }

    public static SizeDto aSizeDto() {
        int index = aInt(SizeUnitDto.values().length);
        return new SizeDto(aDouble(), SizeUnitDto.values()[index]);
    }

    //endregion dto

    //region dao

    public static ProductDao aProductDao() {
        return aProductDao(false);
    }

    public static ProductDao aProductDao(boolean withId) {
        ProductDao dao = new ProductDao();
        if (withId) {
            dao.setId(aLong());
        }

        dao.setName(aText());
        dao.setPrize(aPrizeDao());
        return dao;
    }

    public static ProductInfoDao aProductInfoDao() {
        return aProductInfoDao(false);
    }

    public static ProductInfoDao aProductInfoDao(boolean withId) {
        ProductInfoDao dao = new ProductInfoDao();
        if (withId) {
            dao.setId(aLong());
        }
        dao.setProductId(aLong());
        dao.setSize(aSizeDao());
        dao.setCountryOfOrigin(aCountry());
        dao.setDescription(aNullableText());
        return dao;
    }

    public static ProductContactDao aProductContactDao() {
        return aProductContactDao(false);
    }

    public static ProductContactDao aProductContactDao(boolean withId) {
        ProductContactDao dao = new ProductContactDao();
        if (withId) {
            dao.setId(aLong());
        }
        dao.setProductId(aLong());
        dao.setName(aText());
        dao.setAddress(aText());
        dao.setTel(aNullableText());
        dao.setEmail(aNullableText());
        dao.setWebsite(aNullableText());
        return dao;
    }

    public static ProductDetailsDao aProductDetailsDao() {
        return aProductDetailsDao(aLong());
    }

    public static ProductDetailsDao aProductDetailsDao(long productId) {
        ProductDao product = aProductDao();
        product.setId(productId);

        ProductInfoDao info = aProductInfoDao();
        info.setProductId(productId);

        ProductContactDao contact = aProductContactDao();
        contact.setProductId(productId);

        return new ProductDetailsDao(product, info, contact);
    }

    public static PrizeDao aPrizeDao() {
        int index = aInt(PrizeUnitDao.values().length);
        return new PrizeDao(aDouble(), PrizeUnitDao.values()[index]);
    }

    public static SizeDao aSizeDao() {
        int index = aInt(SizeUnitDao.values().length);
        return new SizeDao(aDouble(), SizeUnitDao.values()[index]);
    }

    private static String aCountry() {
        return aBoolean() ? "Germany" : "England";
    }

    //endregion dao

}
