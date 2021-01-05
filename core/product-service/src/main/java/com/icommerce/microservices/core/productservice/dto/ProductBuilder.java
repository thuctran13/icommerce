package com.icommerce.microservices.core.productservice.dto;

import java.math.BigDecimal;
import java.util.Date;

public interface ProductBuilder {
    ProductBuilder id(Long id);

    ProductBuilder name(String name);

    ProductBuilder description(String description);

    ProductBuilder state(String state);

    ProductBuilder status(String status);

    ProductBuilder price(BigDecimal price);

    ProductBuilder weight(Double weight);

    ProductBuilder creationTs(Date creationTs);

    ProductBuilder modifyTs(Date modifyTs);
    
    ProductInfo build();
}
