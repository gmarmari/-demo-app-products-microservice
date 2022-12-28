package gmarmari.demo.microservices.products.adapters;

import gmarmari.demo.microservices.products.api.*;
import gmarmari.demo.microservices.products.entities.*;
import gmarmari.demo.microservices.products.services.ProductService;
import gmarmari.demo.microservices.products.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ProductAdapter {

    private final ProductService service;

    @Autowired
    public ProductAdapter(ProductService service) {
        this.service = service;
    }


    public List<ProductDto> getProducts() {
        return service.getProducts().stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    public List<ProductDto> getProductsFromIds(String productIds) {
        List<Long> ids = Arrays.stream(productIds.split(",")).map(Long::parseLong).collect(Collectors.toList());
        return service.getProductsFromIds(ids).stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    public List<ProductDto> findProductsByName(String name) {
        return service.findProductsByName(name).stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    public Optional<ProductDto> getProduct(long productId) {
        return service.getProduct(productId).map(this::convert);
    }

    public Optional<ProductDetailsDto> getProductDetails(long productId) {
        return service.getProductDetails(productId).map(this::convert);
    }

    public Response delete(long productId) {
        try {
            service.delete(productId);
            return Response.OK;
        } catch (Exception e) {
            return Response.ERROR;
        }
    }

    public Response save(ProductDetailsDto productDetails) {
        try {
            service.save(convert(productDetails));
            return Response.OK;
        } catch (Exception e) {
            return Response.ERROR;
        }
    }



    //region convert methods

    private ProductDto convert(ProductDao dao) {
        return new ProductDto(
                dao.getId(),
                dao.getName(),
                convert(dao.getPrize())
        );
    }

    private ProductDetailsDto convert(ProductDetailsDao dao) {
        return new ProductDetailsDto(
                convert(dao.product),
                convert(dao.info),
                convert(dao.contact)
        );
    }


    private ProductInfoDto convert(ProductInfoDao dao) {
        return new ProductInfoDto(
                dao.getId(),
                dao.getProductId(),
                convert(dao.getSize()),
                dao.getCountryOfOrigin(),
                dao.getDescription()
        );
    }

    private ProductContactDto convert(ProductContactDao dao) {
        return new ProductContactDto(
                dao.getId(),
                dao.getProductId(),
                dao.getName(),
                dao.getAddress(),
                dao.getTel(),
                dao.getEmail(),
                dao.getWebsite()
        );
    }

    private PrizeDto convert(PrizeDao dao) {
        return new PrizeDto(
                dao.amount,
                PrizeUnitDto.valueOf(dao.unit.name())
        );
    }

    private SizeDto convert(SizeDao dao) {
        return new SizeDto(
                dao.amount,
                SizeUnitDto.valueOf(dao.unit.name())
        );
    }

    private ProductDao convert(ProductDto dto) {
        ProductDao dao = new ProductDao();
        dao.setId(dto.id);
        dao.setName(dto.name);
        dao.setPrize(convert(dto.prize));
        return dao;
    }

    private ProductDetailsDao convert(ProductDetailsDto dto) {
        return new ProductDetailsDao(
                convert(dto.product),
                convert(dto.info),
                convert(dto.contact)
        );
    }


    private ProductInfoDao convert(ProductInfoDto dto) {
        ProductInfoDao dao = new ProductInfoDao();
        dao.setId(dto.id);
        dao.setProductId(dto.productId);
        dao.setSize(convert(dto.size));
        dao.setCountryOfOrigin(dto.countryOfOrigin);
        dao.setDescription(dto.description);
        return dao;
    }

    private ProductContactDao convert(ProductContactDto dto) {
        ProductContactDao dao = new ProductContactDao();
        dao.setId(dto.id);
        dao.setProductId(dto.productId);
        dao.setName(dto.name);
        dao.setAddress(dto.address);
        dao.setTel(dto.tel);
        dao.setEmail(dto.email);
        dao.setWebsite(dto.website);
        return dao;
    }

    private PrizeDao convert(PrizeDto dto) {
        return new PrizeDao(
                dto.amount,
                PrizeUnitDao.valueOf(dto.unit.name())
        );
    }

    private SizeDao convert(SizeDto dto) {
        return new SizeDao(
                dto.amount,
                SizeUnitDao.valueOf(dto.unit.name())
        );
    }

    //endregion convert methods

}
