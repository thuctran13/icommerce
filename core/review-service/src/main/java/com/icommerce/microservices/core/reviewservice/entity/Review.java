package com.icommerce.microservices.core.reviewservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "REVIEW")
public class Review {
    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "TITLE", length = 128, nullable = false)
    private String title;

    @Column(name = "DESCRIPTION", length = 2000, nullable = false)
    private String description;

    @Column(name = "PRODUCT_ID", nullable = false)
    private Long productId;

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

    public String getCreationUid() {
        return creationUid;
    }

    public void setCreationUid(String creationUid) {
        this.creationUid = creationUid;
    }

    public Date getModifyTs() {
        return modifyTs;
    }

    public void setModifyTs(Date modifyTs) {
        this.modifyTs = modifyTs;
    }

    public String getModifyUid() {
        return modifyUid;
    }

    public void setModifyUid(String modifyUid) {
        this.modifyUid = modifyUid;
    }

    public Long getCtn() {
        return ctn;
    }

    public void setCtn(Long ctn) {
        this.ctn = ctn;
    }
}
