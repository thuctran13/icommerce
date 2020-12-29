package com.icommerce.microservices.core.productservice.service;

import com.icommerce.microservices.core.productservice.dao.ProductDAO;
import com.icommerce.microservices.core.productservice.dto.ProductInfo;
import com.icommerce.microservices.core.productservice.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class ProductService {
    
    @Autowired
    private ProductDAO productDAO;

    /**
     * Sample usage: curl $HOST:$PORT/product/1
     *
     * @param productId
     * @return
     */
    @RequestMapping(value = "/product/{productId}", method = RequestMethod.GET)
    public ProductInfo get(@PathVariable Long productId) {
        Product product = productDAO.findById(productId).orElse(null);
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
        productDAO.save(product);
        return productInfo;
    }
    
}
