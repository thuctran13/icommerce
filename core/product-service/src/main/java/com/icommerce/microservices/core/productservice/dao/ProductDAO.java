package com.icommerce.microservices.core.productservice.dao;

import com.icommerce.microservices.core.productservice.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDAO extends CrudRepository<Product, Long> {
    
}
