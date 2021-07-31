package com.project.order.controller;

import com.project.order.model.Orders;
import com.project.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("allOrder")
    public ResponseEntity<List<Orders>> getAllOrder() {

        return orderService.getAllOrder();
    }

    @PostMapping("getOrderById")
    public ResponseEntity<Orders> getOrderById(@RequestParam("id") Long id) {
        return orderService.getOrderById(id);
    }

    @PostMapping("createOrders")
    public ResponseEntity<Orders> createOrder(@RequestBody Orders orders){

        return orderService.createOrder(orders);
    }
}
