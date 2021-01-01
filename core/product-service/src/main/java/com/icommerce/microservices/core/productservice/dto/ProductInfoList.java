package com.icommerce.microservices.core.productservice.dto;

import java.util.ArrayList;
import java.util.List;

public class ProductInfoList {
    private List<ProductInfo> productInfoList = new ArrayList<>();

    public List<ProductInfo> getProductInfoList() {
        return productInfoList;
    }

    public void setProductInfoList(List<ProductInfo> productInfoList) {
        this.productInfoList = productInfoList;
    }
}
