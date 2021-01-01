package com.icommerce.microservices.core.productservice.dao;

import com.icommerce.microservices.core.productservice.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductDAO extends CrudRepository<Product, Long> {
    
    @Query("SELECT p from Product p where lower(p.name) like lower(concat('%', ?1,'%')) or lower(p.description) like lower(concat('%', ?1,'%')) order by p.creationTs desc")
    List<Product> searchProduct(String keyword);
}
