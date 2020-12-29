package com.icommerce.microservices.composite.productcompositeservice.service;

import com.icommerce.microservices.composite.productcompositeservice.bean.ProductCompositeBean;
import com.icommerce.microservices.composite.productcompositeservice.dto.Product;
import com.icommerce.microservices.composite.productcompositeservice.dto.ProductAggregated;
import com.icommerce.microservices.composite.productcompositeservice.util.RestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class ProductCompositeService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductCompositeService.class);

    @Autowired
    ProductCompositeBean productCompositeBean;

    @Autowired
    RestUtil restUtil;

    @RequestMapping("/")
    public String getProduct() {
        return "{\"timestamp\":\"" + new Date() + "\",\"content\":\"Product Composite Service\"}";
    }

    @RequestMapping("/product/{productId}")
    public ResponseEntity<ProductAggregated> getProduct(@PathVariable int productId) {

        // 1. First get mandatory product information
        ResponseEntity<Product> productResult = productCompositeBean.getProduct(productId);

        if (!productResult.getStatusCode().is2xxSuccessful()) {
            // We can't proceed, return whatever fault we got from the getProduct call
            return restUtil.createResponse(null, productResult.getStatusCode());
        }
        
        Product product = productResult.getBody();
        
        return restUtil.createOkResponse(new ProductAggregated(product.getProductId(), product.getName(), product.getWeight()));
    }
    
}
