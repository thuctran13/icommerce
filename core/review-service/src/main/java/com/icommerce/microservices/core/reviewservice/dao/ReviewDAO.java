package com.icommerce.microservices.core.reviewservice.dao;

import com.icommerce.microservices.core.reviewservice.entity.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewDAO extends CrudRepository<Review, Long> {

}
