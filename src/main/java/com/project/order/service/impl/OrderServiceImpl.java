package com.project.order.service.impl;

import com.project.order.dao.OrderRepository;
import com.project.order.exception.InvalidOrder;
import com.project.order.exception.OrderNotFoundException;
import com.project.order.exception.ProductNotFound;
import com.project.order.model.OrderedProduct;
import com.project.order.model.Orders;
import com.project.order.handler.ExternalAPI;
import com.project.order.model.Product;
import com.project.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ExternalAPI externalAPI;

    @Override
    public ResponseEntity<List<Orders>> getAllOrder() {
        List<Orders> ordersList= new LinkedList<>();
        orderRepository.findAll().forEach(ordersList::add);
        if (ordersList.isEmpty()) {
            throw new OrderNotFoundException();
        }else
            return new ResponseEntity<>(ordersList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Orders> getOrderById(Long id) {
        Optional<Orders> orders = orderRepository.findById(id);
        ResponseEntity<Orders> order1 = orders.map(order -> new ResponseEntity<>(order, HttpStatus.OK)).orElseThrow(OrderNotFoundException::new);
        return order1;
    }

    @Override
    public ResponseEntity<Orders> createOrder(Orders orders) {
        if(orders.getOrderList() != null && !orders.getOrderList().isEmpty()) {
                //List<Long> productId = orders.getOrderList().stream().map(orderedProduct -> orderedProduct.getProductId()).collect(Collectors.toList());
                List<Product> orderedProducts= externalAPI.getProductDetails(orders.getOrderList());
                Map<Long, Product> orderedProductsMap = orderedProducts.stream().collect(Collectors.toConcurrentMap(Product::getId, orderedProduct -> orderedProduct));
                List<OrderedProduct> validProducts=new LinkedList<>();
                List<OrderedProduct> invalidProducts=new LinkedList<>();
                orderedProductsMap.forEach((k,v) ->{
                    OrderedProduct orderProd =new OrderedProduct();
                    orders.getOrderList().stream().forEach(orderedProduct -> {
                        if(orderedProduct.getProductId().equals(k) && orderedProduct.getQuantity() <= v.getQuantity()) {
                            orderProd.setProductId(k);
                            orderProd.setProductName(v.getProductName());
                            v.setQuantity(v.getQuantity()-orderedProduct.getQuantity());
                            orderedProductsMap.put(k,v);
                            orderProd.setQuantity(orderedProduct.getQuantity());
                            orderProd.setPrice(v.getPrice());
                            validProducts.add(orderProd);
                        } else if(orderedProduct.getProductId().equals(k) && orderedProduct.getQuantity() >= v.getQuantity()){
                            orderProd.setProductId(k);
                            orderProd.setProductName(orderedProduct.getProductName());
                            orderProd.setQuantity(v.getQuantity());
                            orderProd.setPrice(orderedProduct.getPrice());
                            invalidProducts.add(orderProd);
                        }else
                            throw new InvalidOrder();
                    });
                });
                if(!validProducts.isEmpty()) {
                    orders.setOrderList(validProducts);
                    externalAPI.updateProduct(orderedProductsMap);
                    Double price = validProducts.parallelStream().map(orderedProduct -> orderedProduct.getPrice()*orderedProduct.getQuantity()).collect(Collectors.summingDouble(Double::doubleValue));
                    orders.setTotal(price);
                    orders.setOrderDate(new Date());
                    Orders order = orderRepository.save(orders);
                    return new ResponseEntity<>(order, HttpStatus.OK);
                }else if(!invalidProducts.isEmpty())
                    throw new ProductNotFound(invalidProducts);
            }
            else
                throw new InvalidOrder();
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}
