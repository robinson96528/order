package com.project.order.model;

import java.util.List;
import java.util.Map;

public class ExternalRequest {
    List<Long> productId;

    Map<Long,Integer> productDetails;

    public List<Long> getProductId() {
        return productId;
    }

    public void setProductId(List<Long> productId) {
        this.productId = productId;
    }

    public Map<Long, Integer> getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(Map<Long, Integer> productDetails) {
        this.productDetails = productDetails;
    }
}
