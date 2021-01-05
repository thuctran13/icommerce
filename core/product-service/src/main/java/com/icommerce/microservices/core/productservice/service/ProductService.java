package com.icommerce.microservices.core.productservice.service;

import com.icommerce.microservices.core.productservice.dao.ProductDAO;
import com.icommerce.microservices.core.productservice.dto.DTOConverter;
import com.icommerce.microservices.core.productservice.dto.ProductInfo;
import com.icommerce.microservices.core.productservice.dto.ProductInfoList;
import com.icommerce.microservices.core.productservice.entity.Product;
import com.icommerce.microservices.core.productservice.util.RestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Date;

@RestController
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestMapping(value = "/product/")
public class ProductService {

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private RestUtil restUtil;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ProductInfoList findAll() {
        ProductInfoList productResponse = new ProductInfoList();
        productDAO.findAll()
                .forEach(product -> productResponse.getProductInfoList()
                        .add(DTOConverter.toProductInfo(product)));

        return productResponse;
    }

    @RequestMapping(value = "/{productId}", method = RequestMethod.GET)
    public ProductInfo find(@PathVariable Long productId) {
        Product product = findProductById(productId);

        return DTOConverter.toProductInfo(product);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ProductInfo create(@RequestBody() ProductInfo productInfo) {
        Product product = DTOConverter.toProduct(productInfo, new Product());

        product = productDAO.save(product);

        return DTOConverter.toProductInfo(product);
    }

    @RequestMapping(value = "/{productId}", method = RequestMethod.PUT)
    public ProductInfo update(@PathVariable Long productId, @RequestBody() ProductInfo productInfo) {
        Product product = findProductById(productId);

        //update field from request
        if (productInfo.getName() != null) {
            product.setName(productInfo.getName());
        }
        if (productInfo.getDescription() != null) {
            product.setDescription(productInfo.getDescription());
        }
        if (productInfo.getState() != null) {
            product.setState(productInfo.getState());
        }
        if (productInfo.getStatus() != null) {
            product.setStatus(productInfo.getStatus());
        }
        if (productInfo.getPrice() != null) {
            product.setPrice(productInfo.getPrice());
        }
        if (productInfo.getWeight() != null) {
            product.setWeight(productInfo.getWeight());
        }
        product.setModifyTs(new Date());
        product.setModifyUid("admin");
        product.setCtn(product.getCtn() + 1);
        product = productDAO.save(product);

        return DTOConverter.toProductInfo(product);
    }

    @RequestMapping(value = "/{productId}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long productId) {
        Product product = findProductById(productId);

        productDAO.delete(product);
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public ProductInfoList searchProduct(@RequestParam(name = "keyword") String keyword) {
        ProductInfoList productInfoList = new ProductInfoList();
        productDAO.searchProduct(keyword).forEach(product -> 
                productInfoList.getProductInfoList().add(DTOConverter.toProductInfo(product))
        );

        return productInfoList;
    }

    private Product findProductById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Product id must not be null");
        }

        Product product = productDAO.findById(id).orElse(null);
        if (product == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }

        return product;
    }
}
