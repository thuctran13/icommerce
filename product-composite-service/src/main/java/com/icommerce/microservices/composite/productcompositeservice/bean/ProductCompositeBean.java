package com.icommerce.microservices.composite.productcompositeservice.bean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.icommerce.microservices.composite.productcompositeservice.dto.Product;
import com.icommerce.microservices.composite.productcompositeservice.dto.ProductAggregated;
import com.icommerce.microservices.composite.productcompositeservice.util.RestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;

@Component
public class ProductCompositeBean {

    private static final Logger LOG = LoggerFactory.getLogger(ProductCompositeBean.class);

    @Autowired
    RestUtil restUtil;

    private RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<Product> getProduct(int productId) {

        URI uri = restUtil.getServiceUrl("product", "http://localhost:8081/product");
        String url = uri.toString() + "/product/" + productId;
        LOG.debug("GetProduct from URL: {}", url);

        ResponseEntity<String> resultStr = restTemplate.getForEntity(url, String.class);
        LOG.debug("GetProduct http-status: {}", resultStr.getStatusCode());
        LOG.debug("GetProduct body: {}", resultStr.getBody());

        Product product = response2Product(resultStr);
        LOG.debug("GetProduct.id: {}", product.getProductId());

        return restUtil.createOkResponse(product);
    }

    public Product response2Product(ResponseEntity<String> response) {
        try {
            return getProductReader().readValue(response.getBody());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ObjectReader productReader = null;

    private ObjectReader getProductReader() {

        if (productReader != null) return productReader;

        ObjectMapper mapper = new ObjectMapper();
        return productReader = mapper.reader(Product.class);
    }
}
