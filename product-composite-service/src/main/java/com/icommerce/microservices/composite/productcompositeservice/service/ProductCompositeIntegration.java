package com.icommerce.microservices.composite.productcompositeservice.service;

import com.icommerce.microservices.composite.productcompositeservice.dto.ProductInfo;
import com.icommerce.microservices.composite.productcompositeservice.dto.ProductInfoList;
import com.icommerce.microservices.composite.productcompositeservice.dto.ReviewInfo;
import com.icommerce.microservices.composite.productcompositeservice.dto.ReviewInfoList;
import com.icommerce.microservices.composite.productcompositeservice.util.RestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
public class ProductCompositeIntegration {

    private static final Logger LOG = LoggerFactory.getLogger(ProductCompositeIntegration.class);

    @Autowired
    RestUtil restUtil;

    private RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<ProductInfoList> findAllProducts() {

        URI uri = restUtil.getServiceUrl("product", "http://localhost:8081/product");
        String url = uri.toString() + "/product/";
        LOG.debug("FindAllProducts from URL: {}", url);

        ResponseEntity<ProductInfoList> findRes = restTemplate.getForEntity(url, ProductInfoList.class);
        LOG.debug("FindAllProducts http-status: {}", findRes.getStatusCode());
        LOG.debug("FindAllProducts body: {}", findRes.getBody());

        return findRes;
    }

    public ResponseEntity<ProductInfo> findProduct(Long productId) {

        URI uri = restUtil.getServiceUrl("product", "http://localhost:8081/product");
        String url = uri.toString() + "/product/" + productId;
        LOG.debug("FindProduct from URL: {}", url);

        ResponseEntity<ProductInfo> findRes = restTemplate.getForEntity(url, ProductInfo.class);
        LOG.debug("FindProduct http-status: {}", findRes.getStatusCode());
        LOG.debug("FindProduct body: {}", findRes.getBody());

        return findRes;
    }

    public ResponseEntity<ReviewInfoList> findAllReviewsByProduct(Long productId) {
        URI uri = restUtil.getServiceUrl("review", "http://localhost:8081/review");
        String url = uri.toString() + "/product/" + productId;
        LOG.debug("FindAllReviewsByProduct from URL: {}", url);

        ResponseEntity<ReviewInfoList> findRes = restTemplate.getForEntity(url, ReviewInfoList.class);
        LOG.info("FindAllReviewsByProduct http-status: {}", findRes.getStatusCode());
        LOG.info("FindAllReviewsByProduct body: {}", findRes.getBody());

        return findRes;
    }

    public ResponseEntity<ReviewInfo> createReviewByCustomer(ReviewInfo reviewInfo) {
        URI uri = restUtil.getServiceUrl("review", "http://localhost:8081/review");
        String url = uri.toString() + "/review/";
        LOG.debug("CreateReviewByCustomer from URL: {}", url);

        ResponseEntity<ReviewInfo> createReviewRes = restTemplate.postForEntity(url, reviewInfo, ReviewInfo.class);
        LOG.info("CreateReviewByCustomer http-status: {}", createReviewRes.getStatusCode());
        LOG.info("CreateReviewByCustomer body: {}", createReviewRes.getBody());

        return createReviewRes;
    }
}
