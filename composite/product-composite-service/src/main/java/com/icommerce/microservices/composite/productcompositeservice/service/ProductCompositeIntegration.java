package com.icommerce.microservices.composite.productcompositeservice.service;

import com.icommerce.microservices.composite.productcompositeservice.dto.ProductInfo;
import com.icommerce.microservices.composite.productcompositeservice.dto.ProductInfoList;
import com.icommerce.microservices.composite.productcompositeservice.dto.ReviewInfo;
import com.icommerce.microservices.composite.productcompositeservice.dto.ReviewInfoList;
import com.icommerce.microservices.composite.productcompositeservice.util.RestUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
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

    @HystrixCommand(fallbackMethod = "defaultProduct")
    public ResponseEntity<ProductInfoList> findAllProducts() {

        URI uri = restUtil.getServiceUrl("product", "http://localhost:8081/product");
        String url = uri.toString() + "/product/";
        LOG.debug("FindAllProducts from URL: {}", url);

        ResponseEntity<ProductInfoList> responseEntity = restTemplate.getForEntity(url, ProductInfoList.class);
        LOG.debug("FindAllProducts http-status: {}", responseEntity.getStatusCode());
        LOG.debug("FindAllProducts body: {}", responseEntity.getBody());

        return responseEntity;
    }

    @HystrixCommand(fallbackMethod = "defaultProduct")
    public ResponseEntity<ProductInfo> findProduct(Long productId) {

        URI uri = restUtil.getServiceUrl("product", "http://localhost:8081/product");
        String url = uri.toString() + "/product/" + productId;
        LOG.debug("FindProduct from URL: {}", url);

        ResponseEntity<ProductInfo> responseEntity = restTemplate.getForEntity(url, ProductInfo.class);
        LOG.debug("FindProduct http-status: {}", responseEntity.getStatusCode());
        LOG.debug("FindProduct body: {}", responseEntity.getBody());

        return responseEntity;
    }

    @HystrixCommand(fallbackMethod = "defaultProduct")
    public ResponseEntity<ProductInfo> createProduct(ProductInfo productInfo) {

        URI uri = restUtil.getServiceUrl("product", "http://localhost:8081/product");
        String url = uri.toString() + "/product/";
        LOG.debug("createProduct from URL: {}", url);

        ResponseEntity<ProductInfo> responseEntity = restTemplate.postForEntity(url, productInfo, ProductInfo.class);
        LOG.debug("createProduct http-status: {}", responseEntity.getStatusCode());
        LOG.debug("createProduct body: {}", responseEntity.getBody());

        return responseEntity;
    }

    @HystrixCommand(fallbackMethod = "defaultProduct")
    public ResponseEntity<ProductInfo> updateProduct(Long productId, ProductInfo productInfo) {

        URI uri = restUtil.getServiceUrl("product", "http://localhost:8081/product");
        String url = uri.toString() + "/product/" + productId;
        LOG.debug("updateProduct from URL: {}", url);
        
        ResponseEntity<ProductInfo> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(productInfo), ProductInfo.class);
        LOG.debug("updateProduct http-status: {}", responseEntity.getStatusCode());
        LOG.debug("updateProduct body: {}", responseEntity.getBody());

        return responseEntity;
    }

    @HystrixCommand(fallbackMethod = "defaultProduct")
    public void deleteProduct(Long productId) {

        URI uri = restUtil.getServiceUrl("product", "http://localhost:8081/product");
        String url = uri.toString() + "/product/" + productId;
        LOG.debug("deleteProduct from URL: {}", url);

        restTemplate.delete(url);
    }

    @HystrixCommand(fallbackMethod = "defaultProduct")
    public ResponseEntity<ProductInfoList> searchProduct(String keyword) {

        URI uri = restUtil.getServiceUrl("product", "http://localhost:8081/product");
        String url = uri.toString() + "/product/find?keyword=" + keyword;
        LOG.debug("SearchProduct from URL: {}", url);

        ResponseEntity<ProductInfoList> responseEntity = restTemplate.getForEntity(url, ProductInfoList.class);
        LOG.debug("SearchProduct http-status: {}", responseEntity.getStatusCode());
        LOG.debug("SearchProduct body: {}", responseEntity.getBody());

        return responseEntity;
    }

    @HystrixCommand(fallbackMethod = "defaultReview")
    public ResponseEntity<ReviewInfoList> findAllReviewsByProduct(Long productId) {
        URI uri = restUtil.getServiceUrl("review", "http://localhost:8081/review");
        String url = uri.toString() + "/review/byProductId/" + productId;
        LOG.debug("FindAllReviewsByProduct from URL: {}", url);

        ResponseEntity<ReviewInfoList> responseEntity = restTemplate.getForEntity(url, ReviewInfoList.class);
        LOG.info("FindAllReviewsByProduct http-status: {}", responseEntity.getStatusCode());
        LOG.info("FindAllReviewsByProduct body: {}", responseEntity.getBody());

        return responseEntity;
    }

    @HystrixCommand(fallbackMethod = "defaultReview")
    public ResponseEntity<ReviewInfoList> findAllReviews() {
        URI uri = restUtil.getServiceUrl("review", "http://localhost:8081/review");
        String url = uri.toString() + "/review/";
        LOG.debug("FindAllReviewsByProduct from URL: {}", url);

        ResponseEntity<ReviewInfoList> responseEntity = restTemplate.getForEntity(url, ReviewInfoList.class);
        LOG.info("FindAllReviewsByProduct http-status: {}", responseEntity.getStatusCode());
        LOG.info("FindAllReviewsByProduct body: {}", responseEntity.getBody());

        return responseEntity;
    }

    @HystrixCommand(fallbackMethod = "defaultReview")
    public ResponseEntity<ReviewInfo> findReviewById(Long reviewId) {
        URI uri = restUtil.getServiceUrl("review", "http://localhost:8081/review");
        String url = uri.toString() + "/review/" + reviewId;
        LOG.debug("FindAllReviewsByProduct from URL: {}", url);

        ResponseEntity<ReviewInfo> responseEntity = restTemplate.getForEntity(url, ReviewInfo.class);
        LOG.info("FindAllReviewsByProduct http-status: {}", responseEntity.getStatusCode());
        LOG.info("FindAllReviewsByProduct body: {}", responseEntity.getBody());

        return responseEntity;
    }

    @HystrixCommand(fallbackMethod = "defaultReview")
    public ResponseEntity<ReviewInfo> createReview(ReviewInfo reviewInfo) {
        URI uri = restUtil.getServiceUrl("review", "http://localhost:8081/review");
        String url = uri.toString() + "/review/";
        LOG.debug("createReview from URL: {}", url);

        ResponseEntity<ReviewInfo> createReviewRes = restTemplate.postForEntity(url, reviewInfo, ReviewInfo.class);
        LOG.info("createReview http-status: {}", createReviewRes.getStatusCode());
        LOG.info("createReview body: {}", createReviewRes.getBody());

        return createReviewRes;
    }

    @HystrixCommand(fallbackMethod = "defaultReview")
    public ResponseEntity<ReviewInfo> updateReview(Long reviewId, ReviewInfo reviewInfo) {
        URI uri = restUtil.getServiceUrl("review", "http://localhost:8081/review");
        String url = uri.toString() + "/review/" + reviewId;
        LOG.debug("updateReview from URL: {}", url);

        ResponseEntity<ReviewInfo> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(reviewInfo), ReviewInfo.class);
        LOG.debug("updateReview http-status: {}", responseEntity.getStatusCode());
        LOG.debug("updateReview body: {}", responseEntity.getBody());

        return responseEntity;
    }

    @HystrixCommand(fallbackMethod = "defaultReview")
    public void deleteReview(Long reviewId) {
        URI uri = restUtil.getServiceUrl("review", "http://localhost:8081/review");
        String url = uri.toString() + "/review/" + reviewId;
        LOG.debug("CreateReviewByCustomer from URL: {}", url);
        
        restTemplate.delete(url);
    }

    public ResponseEntity<ProductInfoList> defaultProduct() {
        LOG.warn("Using fallback method for product-service");
        return restUtil.createResponse(null, HttpStatus.BAD_GATEWAY);
    }

    public ResponseEntity<ProductInfo> defaultReview() {
        LOG.warn("Using fallback method for review-service");
        return restUtil.createResponse(null, HttpStatus.BAD_GATEWAY);
    }
    
}
