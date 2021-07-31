package com.project.order.exception;

import com.project.order.model.OrderedProduct;
import org.aspectj.weaver.ast.Or;

import java.util.List;

public class ProductNotFound extends RuntimeException{

    List<OrderedProduct> invalidProduct;

    public ProductNotFound(List<OrderedProduct> invalidProducts) {
        this.invalidProduct=invalidProducts;
    }

    public List<OrderedProduct> getInvalidProduct() {
        return invalidProduct;
    }

    public void setInvalidProduct(List<OrderedProduct> invalidProduct) {
        this.invalidProduct = invalidProduct;
    }
}
