package com.icommerce.microservices.core.productservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "PRODUCT")
public class Product {
    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private Long id;
    
    @Column(name = "NAME", length = 128, nullable = false)
    private String name;

    @Column(name = "DESCRIPTION", length = 2000)
    private String description;

    @Column(name = "STATE", length = 20)
    private String state;

    @Column(name = "STATUS", length = 20)
    private String status;
    
    @Column(name = "PRICE")
    private BigDecimal price;
    
    @Column(name = "WEIGHT")
    private Double weight;
    
    @Column(name = "CREATION_TS", nullable = false)
    private Date creationTs;

    @Column(name = "CREATION_UID", nullable = false)
    private String creationUid;

    @Column(name = "MOTIFY_TS", nullable = false)
    private Date modifyTs;

    @Column(name = "MOTIFY_UID", nullable = false)
    private String modifyUid;
    
    @Column(name = "CTN", nullable = false)
    private Long ctn;

    public Long getId() {
        return id;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public Long getCtn() {
        return ctn;
    }

    public void setCtn(Long ctn) {
        this.ctn = ctn;
    }

    public String getCreationUid() {
        return creationUid;
    }

    public void setCreationUid(String creationUid) {
        this.creationUid = creationUid;
    }

    public String getModifyUid() {
        return modifyUid;
    }

    public void setModifyUid(String modifyUid) {
        this.modifyUid = modifyUid;
    }
}
