package com.icommerce.microservices.core.reviewservice.service;

import com.icommerce.microservices.core.reviewservice.dao.ReviewDAO;
import com.icommerce.microservices.core.reviewservice.dto.DTOConverter;
import com.icommerce.microservices.core.reviewservice.dto.ReviewInfo;
import com.icommerce.microservices.core.reviewservice.dto.ReviewInfoList;
import com.icommerce.microservices.core.reviewservice.entity.Review;
import com.icommerce.microservices.core.reviewservice.util.RestUtil;
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
@RequestMapping(value = "review")
public class ReviewService {

    @Autowired
    private ReviewDAO reviewDAO;

    @Autowired
    private RestUtil restUtil;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ReviewInfoList findAll() {
        ReviewInfoList productResponse = new ReviewInfoList();
        reviewDAO.findAll().forEach(review -> productResponse.getReviewInfoList()
                .add(DTOConverter.toReviewInfo(review)));

        return productResponse;
    }

    @RequestMapping(value = "/{productId}", method = RequestMethod.GET)
    public ReviewInfo find(@PathVariable Long productId) {
        Review review = findReviewById(productId);

        return DTOConverter.toReviewInfo(review);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ReviewInfo create(@RequestBody() ReviewInfo reviewInfo) {
        Review review = DTOConverter.toReview(reviewInfo, new Review());
        review = reviewDAO.save(review);

        return DTOConverter.toReviewInfo(review);
    }

    @RequestMapping(value = "/{reviewId}", method = RequestMethod.PUT)
    public ReviewInfo update(@PathVariable Long reviewId, @RequestBody() ReviewInfo reviewInfo) {
        Review review = findReviewById(reviewId);

        //update field from request
        if (reviewInfo.getTitle() != null) {
            review.setTitle(reviewInfo.getTitle());
        }
        if (review.getDescription() != null) {
            review.setDescription(reviewInfo.getDescription());
        }
        review.setModifyTs(new Date());
        review.setModifyUid("admin");
        review.setCtn(review.getCtn() + 1);
        review = reviewDAO.save(review);

        return DTOConverter.toReviewInfo(review);
    }

    @RequestMapping(value = "/{reviewId}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long reviewId) {
        Review review = findReviewById(reviewId);

        reviewDAO.delete(review);
    }

    @RequestMapping(value = "/byProductId/{productId}", method = RequestMethod.GET)
    public ReviewInfoList findAllByProductId(@PathVariable Long productId) {
        ReviewInfoList reviewResponse = new ReviewInfoList();
        reviewDAO.findAllByProductId(productId)
                .forEach(review -> reviewResponse.getReviewInfoList()
                        .add(DTOConverter.toReviewInfo(review)));

        return reviewResponse;
    }

    private Review findReviewById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Product id must not be null");
        }

        Review review = reviewDAO.findById(id).orElse(null);
        if (review == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found");
        }

        return review;
    }
}
