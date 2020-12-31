package com.icommerce.microservices.core.productservice.service;

import com.icommerce.microservices.core.productservice.dao.ProductDAO;
import com.icommerce.microservices.core.productservice.dto.ProductInfo;
import com.icommerce.microservices.core.productservice.dto.ProductResponse;
import com.icommerce.microservices.core.productservice.entity.Product;
import com.icommerce.microservices.core.productservice.util.RestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Date;

@RestController
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductService {
    
    @Autowired
    private ProductDAO productDAO;
    
    @Autowired
    private RestUtil restUtil;

    @RequestMapping(value = "/product/", method = RequestMethod.GET)
    public ProductResponse findAll() {
        ProductResponse productResponse = new ProductResponse();
        productDAO.findAll().forEach(product -> productResponse.getProductInfoList().add(new ProductInfo(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getState(),
                product.getStatus(),
                product.getPrice(),
                product.getWeight(),
                product.getCreationTs(),
                product.getModifyTs())));

        return productResponse;
    }
    
    @RequestMapping(value = "/product/{productId}", method = RequestMethod.GET)
    public ProductInfo find(@PathVariable Long productId) {
        Product product = findProductById(productId);

        return new ProductInfo(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getState(),
                product.getStatus(),
                product.getPrice(),
                product.getWeight(),
                product.getCreationTs(),
                product.getModifyTs()
        );
    }

    @RequestMapping(value = "/product", method = RequestMethod.POST)
    public ProductInfo create(@RequestBody() ProductInfo productInfo) {
        Product product = new Product();
        product.setName(productInfo.getName());
        product.setDescription(productInfo.getDescription());
        product.setState(productInfo.getState());
        product.setStatus(productInfo.getStatus());
        product.setPrice(productInfo.getPrice());
        product.setWeight(productInfo.getWeight());
        product.setCreationTs(new Date());
        product.setCreationUid("admin");
        product.setModifyTs(new Date());
        product.setModifyUid("admin");
        product.setCtn(0L);
        product = productDAO.save(product);

        return new ProductInfo(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getState(),
                product.getStatus(),
                product.getPrice(),
                product.getWeight(),
                product.getCreationTs(),
                product.getModifyTs()
        );
    }

    @RequestMapping(value = "/product/{productId}", method = RequestMethod.PUT)
    public ProductInfo update(@PathVariable Long productId, @RequestBody() ProductInfo productInfo) {
        Product product = findProductById(productId);
        
        //update field from request
        product.setName(productInfo.getName());
        product.setDescription(productInfo.getDescription());
        if (productInfo.getState() != null) {
            product.setState(productInfo.getState());
        }
        if (productInfo.getStatus() != null) {
            product.setStatus(productInfo.getStatus());
        }
        product.setPrice(productInfo.getPrice());
        product.setWeight(productInfo.getWeight());
        product.setModifyTs(new Date());
        product.setModifyUid("admin");
        product.setCtn(product.getCtn() + 1);
        product = productDAO.save(product);
        
        return new ProductInfo(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getState(),
                product.getStatus(),
                product.getPrice(),
                product.getWeight(),
                product.getCreationTs(),
                product.getModifyTs()
        );
    }

    @RequestMapping(value = "/product/{productId}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long productId) {
        Product product = findProductById(productId);
        
        productDAO.delete(product);
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
