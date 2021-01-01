package com.icommerce.microservices.core.reviewservice.dto;

import java.util.ArrayList;
import java.util.List;

public class ReviewInfoList {
    private List<ReviewInfo> reviewInfoList = new ArrayList<>();

    public List<ReviewInfo> getReviewInfoList() {
        return reviewInfoList;
    }

    public void setReviewInfoList(List<ReviewInfo> reviewInfoList) {
        this.reviewInfoList = reviewInfoList;
    }
}
