package com.icommerce.microservices.core.reviewservice.dto;

import com.icommerce.microservices.core.reviewservice.entity.Review;

import java.util.Date;

public class DTOConverter {
    
    public static Review toReview(ReviewInfo reviewInfo, Review review) {
        review.setTitle(reviewInfo.getTitle());
        review.setDescription(reviewInfo.getDescription());
        review.setProductId(reviewInfo.getProductId());
        review.setCreationTs(new Date());
        review.setCreationUid("admin");
        review.setModifyTs(new Date());
        review.setModifyUid("admin");
        review.setCtn(0L);
        return review;
    }

    public static ReviewInfo toReviewInfo(Review review) {
        return new ReviewInfo(
                review.getId(),
                review.getTitle(),
                review.getDescription(),
                review.getProductId(),
                review.getCreationTs(),
                review.getModifyTs());
    }
}
