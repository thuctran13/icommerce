package com.icommerce.microservices.core.productservice.dto;

import com.icommerce.microservices.core.productservice.entity.Product;

import java.util.Date;

public class DTOConverter {

    public static Product toProduct(ProductInfo productInfo, Product product) {
        product.setName(productInfo.getName());
        product.setDescription(productInfo.getDescription());
        product.setState(productInfo.getState());
        product.setStatus(productInfo.getStatus());
        product.setPrice(productInfo.getPrice());
        product.setWeight(productInfo.getWeight());
        product.setCreationTs(new Date());
        product.setCreationUid("admin");
        product.setModifyTs(new Date());
        product.setModifyUid("admin");
        product.setCtn(0L);

        return product;
    }

    public static ProductInfo toProductInfo(Product product) {
        return new ProductInfoBuilder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .state(product.getState())
                .status(product.getStatus())
                .price(product.getPrice())
                .weight(product.getWeight())
                .creationTs(product.getCreationTs())
                .modifyTs(product.getModifyTs())
                .build();
    }
}
