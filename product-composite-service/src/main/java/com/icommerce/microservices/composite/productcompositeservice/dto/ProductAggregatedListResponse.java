package com.icommerce.microservices.composite.productcompositeservice.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by magnus on 04/03/15.
 */
public class ProductAggregatedListResponse {
    private List<ProductAggregated> productAggregatedList = new ArrayList<>();

    public List<ProductAggregated> getProductAggregatedList() {
        return productAggregatedList;
    }

    public void setProductAggregatedList(List<ProductAggregated> productAggregatedList) {
        this.productAggregatedList = productAggregatedList;
    }
}
