package com.icommerce.microservices.core.productservice.dto;

import java.math.BigDecimal;
import java.util.Date;

public class ProductInfoBuilder implements ProductBuilder {

    private Long id;

    private String name;

    private String description;

    private String state;

    private String status;

    private BigDecimal price;

    private Double weight;

    private Date creationTs;

    private Date modifyTs;

    @Override
    public ProductBuilder id(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public ProductBuilder name(String name) {
        this.name = name;
        return this;
    }

    @Override
    public ProductBuilder description(String description) {
        this.description = description;
        return this;
    }

    @Override
    public ProductBuilder state(String state) {
        this.state = state;
        return this;
    }

    @Override
    public ProductBuilder status(String status) {
        this.status = status;
        return this;
    }

    @Override
    public ProductBuilder price(BigDecimal price) {
        this.price = price;
        return this;
    }

    @Override
    public ProductBuilder weight(Double weight) {
        this.weight = weight;
        return this;
    }

    @Override
    public ProductBuilder creationTs(Date creationTs) {
        this.creationTs = creationTs;
        return this;
    }

    @Override
    public ProductBuilder modifyTs(Date modifyTs) {
        this.modifyTs = modifyTs;
        return this;
    }

    @Override
    public ProductInfo build() {
        return new ProductInfo(id, name, description, state, status, price, weight, creationTs, modifyTs);
    }
}
