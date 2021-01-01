package com.icommerce.microservices.composite.productcompositeservice.dto;

/**
 * Created by magnus on 04/03/15.
 */
public class ProductAggregated {
    private ProductInfo productInfo;
    private ReviewInfoList reviewResponse;

    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }

    public ReviewInfoList getReviewResponse() {
        return reviewResponse;
    }

    public void setReviewResponse(ReviewInfoList reviewResponse) {
        this.reviewResponse = reviewResponse;
    }
}
