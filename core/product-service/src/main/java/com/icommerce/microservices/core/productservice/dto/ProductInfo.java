package com.icommerce.microservices.core.productservice.dto;

import com.icommerce.microservices.core.productservice.entity.ProductStateEnum;
import com.icommerce.microservices.core.productservice.entity.ProductStatusEnum;

import java.math.BigDecimal;
import java.util.Date;

public class ProductInfo {
    private Long id;
    
    private String name;

    private String description;

    private ProductStateEnum state;

    private ProductStatusEnum status;

    private BigDecimal price;

    private Double weight;

    private Date creationTs;

    private Date modifyTs;
    
    public ProductInfo(){
    }

    public ProductInfo(Long id, String name, String description, ProductStateEnum state, ProductStatusEnum status, BigDecimal price, Double weight, Date creationTs, Date modifyTs) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.state = state;
        this.status = status;
        this.price = price;
        this.weight = weight;
        this.creationTs = creationTs;
        this.modifyTs = modifyTs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductStateEnum getState() {
        return state;
    }

    public void setState(ProductStateEnum state) {
        this.state = state;
    }

    public ProductStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ProductStatusEnum status) {
        this.status = status;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Date getCreationTs() {
        return creationTs;
    }

    public void setCreationTs(Date creationTs) {
        this.creationTs = creationTs;
    }

    public Date getModifyTs() {
        return modifyTs;
    }

    public void setModifyTs(Date modifyTs) {
        this.modifyTs = modifyTs;
    }
}
