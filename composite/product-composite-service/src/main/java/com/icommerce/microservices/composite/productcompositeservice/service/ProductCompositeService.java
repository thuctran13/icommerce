package com.icommerce.microservices.composite.productcompositeservice.service;

import com.icommerce.microservices.composite.productcompositeservice.dto.ProductAggregated;
import com.icommerce.microservices.composite.productcompositeservice.dto.ProductAggregatedListResponse;
import com.icommerce.microservices.composite.productcompositeservice.dto.ProductInfo;
import com.icommerce.microservices.composite.productcompositeservice.dto.ProductInfoList;
import com.icommerce.microservices.composite.productcompositeservice.dto.ReviewInfo;
import com.icommerce.microservices.composite.productcompositeservice.dto.ReviewInfoList;
import com.icommerce.microservices.composite.productcompositeservice.util.RestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Date;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RestController
public class ProductCompositeService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductCompositeService.class);

    @Autowired
    ProductCompositeIntegration productIntegration;

    @Autowired
    RestUtil restUtil;

    @RequestMapping("/")
    public String getProduct() {
        return "{\"timestamp\":\"" + new Date() + "\",\"content\":\"Product Composite Service\"}";
    }

    @RequestMapping("/product/{productId}")
    public ResponseEntity<ProductAggregated> find(@PathVariable Long productId) {

        // 1. First get product information
        ResponseEntity<ProductInfo> productResult = productIntegration.findProduct(productId);

        if (!productResult.getStatusCode().is2xxSuccessful()) {
            // We can't proceed, return whatever fault we got from the findProduct call
            return restUtil.createResponse(null, productResult.getStatusCode());
        }

        ProductAggregated productAggregated = new ProductAggregated();
        productAggregated.setProductInfo(productResult.getBody());

        // 2. Get all product reviews
        ResponseEntity<ReviewInfoList> reviewsResponse = productIntegration.findAllReviewsByProduct(productId);
        if (!reviewsResponse.getStatusCode().is2xxSuccessful()) {
            LOG.warn("Issue while getting review list {}", reviewsResponse.getStatusCode());
        }

        productAggregated.setReviewResponse(reviewsResponse.getBody());

        return restUtil.createOkResponse(productAggregated);
    }

    @RequestMapping("/product")
    public ResponseEntity<ProductAggregatedListResponse> findAll() {
        // 1. First get all product information
        ResponseEntity<ProductInfoList> productResult = productIntegration.findAllProducts();

        if (!productResult.getStatusCode().is2xxSuccessful()) {
            // We can't proceed, return whatever fault we got from the findProduct call
            return restUtil.createResponse(null, productResult.getStatusCode());
        }

        ProductAggregatedListResponse response = new ProductAggregatedListResponse();
        if (productResult.getBody() != null) {
            productResult.getBody().getProductInfoList().forEach(productInfo -> {
                ProductAggregated productAggregated = new ProductAggregated();
                productAggregated.setProductInfo(productInfo);
                response.getProductAggregatedList().add(productAggregated);
            });
        }

        return restUtil.createOkResponse(response);
    }

    @RequestMapping(value = "/product", method = RequestMethod.POST)
    public ResponseEntity<ProductInfo> createProduct(@RequestBody ProductInfo productInfo) {
        ResponseEntity<ProductInfo> createRes = productIntegration.createProduct(productInfo);
        if (!createRes.getStatusCode().is2xxSuccessful()) {
            return restUtil.createResponse(null, createRes.getStatusCode());
        }
        return restUtil.createOkResponse(createRes.getBody());
    }

    @RequestMapping(value = "/product/{productId}", method = RequestMethod.PUT)
    public ResponseEntity<ProductInfo> updateProduct(@PathVariable Long productId, @RequestBody ProductInfo productInfo) {
        ResponseEntity<ProductInfo> responseEntity = productIntegration.updateProduct(productId, productInfo);
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            return restUtil.createResponse(null, responseEntity.getStatusCode());
        }
        return restUtil.createOkResponse(responseEntity.getBody());
    }

    @RequestMapping(value = "/product/{productId}", method = RequestMethod.DELETE)
    public ResponseEntity<ProductInfo> deleteProduct(@PathVariable Long productId) {
        try {
            productIntegration.deleteProduct(productId);
            return restUtil.createResponse(null, HttpStatus.OK);
        } catch (Exception e) {
            LOG.warn("Exception occur while deleting product", e);
            return restUtil.createResponse(null, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping("/product/find")
    public ResponseEntity<ProductAggregatedListResponse> searchProduct(@RequestParam(name = "keyword") String keyword) {
        // 1. First get all product information
        ResponseEntity<ProductInfoList> productResult = productIntegration.searchProduct(keyword);

        if (!productResult.getStatusCode().is2xxSuccessful()) {
            // We can't proceed, return whatever fault we got from the findProduct call
            return restUtil.createResponse(null, productResult.getStatusCode());
        }

        ProductAggregatedListResponse response = new ProductAggregatedListResponse();
        if (productResult.getBody() != null) {
            productResult.getBody().getProductInfoList().forEach(productInfo -> {
                ProductAggregated productAggregated = new ProductAggregated();
                productAggregated.setProductInfo(productInfo);
                response.getProductAggregatedList().add(productAggregated);
            });
        }

        return restUtil.createOkResponse(response);
    }

    @RequestMapping(value = "/review/findByProduct/{productId}", method = RequestMethod.GET)
    public ResponseEntity<ReviewInfoList> findAllByProduct(@PathVariable Long productId) {
        ResponseEntity<ReviewInfoList> reviewInfoResponse = productIntegration.findAllReviewsByProduct(productId);
        if (!reviewInfoResponse.getStatusCode().is2xxSuccessful()) {
            // We can't proceed, return whatever fault we got from the findProduct call
            return restUtil.createResponse(null, reviewInfoResponse.getStatusCode());
        }

        return restUtil.createOkResponse(reviewInfoResponse.getBody());
    }

    @RequestMapping(value = "review/", method = RequestMethod.GET)
    public ResponseEntity<ReviewInfoList> findAllReviews() {
        ResponseEntity<ReviewInfoList> reviewInfoResponse = productIntegration.findAllReviews();
        if (!reviewInfoResponse.getStatusCode().is2xxSuccessful()) {
            // We can't proceed, return whatever fault we got from the findProduct call
            return restUtil.createResponse(null, reviewInfoResponse.getStatusCode());
        }

        return restUtil.createOkResponse(reviewInfoResponse.getBody());
    }

    @RequestMapping(value = "review/{reviewId}", method = RequestMethod.GET)
    public ResponseEntity<ReviewInfo> findReviewById(@PathVariable Long reviewId) {
        ResponseEntity<ReviewInfo> reviewInfoResponse = productIntegration.findReviewById(reviewId);
        if (!reviewInfoResponse.getStatusCode().is2xxSuccessful()) {
            // We can't proceed, return whatever fault we got from the findProduct call
            return restUtil.createResponse(null, reviewInfoResponse.getStatusCode());
        }

        return restUtil.createOkResponse(reviewInfoResponse.getBody());
    }

    @RequestMapping(value = "review", method = RequestMethod.POST)
    public ResponseEntity<ReviewInfo> createReview(@RequestBody ReviewInfo reviewInfo) {
        ResponseEntity<ReviewInfo> reviewInfoResponse = productIntegration.createReview(reviewInfo);
        if (!reviewInfoResponse.getStatusCode().is2xxSuccessful()) {
            // We can't proceed, return whatever fault we got from the findProduct call
            return restUtil.createResponse(null, reviewInfoResponse.getStatusCode());
        }

        return restUtil.createOkResponse(reviewInfoResponse.getBody());
    }

    @RequestMapping(value = "/review/{reviewId}", method = RequestMethod.PUT)
    public ResponseEntity<ReviewInfo> updateReview(@PathVariable Long reviewId, @RequestBody ReviewInfo reviewInfo) {

        ResponseEntity<ReviewInfo> responseEntity = productIntegration.updateReview(reviewId, reviewInfo);
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            return restUtil.createResponse(null, responseEntity.getStatusCode());
        }
        return restUtil.createOkResponse(responseEntity.getBody());
    }

    @RequestMapping(value = "/review/{reviewId}", method = RequestMethod.DELETE)
    public ResponseEntity<ReviewInfo> deleteReview(@PathVariable Long reviewId) {
        try {
            productIntegration.deleteReview(reviewId);
            return restUtil.createResponse(null, HttpStatus.OK);
        } catch (Exception e) {
            LOG.warn("Exception occur while deleting product", e);
            return restUtil.createResponse(null, HttpStatus.BAD_REQUEST);
        }
    }
}
