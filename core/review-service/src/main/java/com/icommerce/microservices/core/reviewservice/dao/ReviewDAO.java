package com.icommerce.microservices.core.reviewservice.dao;

import com.icommerce.microservices.core.reviewservice.entity.Review;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReviewDAO extends CrudRepository<Review, Long> {
    List<Review> findAllByProductId(Long productId);
}
