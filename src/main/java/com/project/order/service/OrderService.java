package com.project.order.service;

import com.project.order.model.Orders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


public interface OrderService {
    public ResponseEntity<List<Orders>> getAllOrder();

    ResponseEntity<Orders> getOrderById(Long id);

    ResponseEntity<Orders> createOrder(Orders orders);
}
