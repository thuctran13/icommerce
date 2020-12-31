package com.icommerce.microservices.composite.productcompositeservice.dto;

import java.io.Serializable;
import java.util.Date;

public class ReviewInfo implements Serializable {

    private static final long serialVersionUID = 833859631359411045L;
    
    private Long id;

    private String title;

    private String description;

    private Long productId;

    private Date creationTs;

    private Date modifyTs;

    public ReviewInfo() {
    }

    public ReviewInfo(Long id, String title, String description, Long productId, Date creationTs, Date modifyTs) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.productId = productId;
        this.creationTs = creationTs;
        this.modifyTs = modifyTs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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
