package com.project.order.model;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import java.util.Date;
import java.util.List;

@Entity
public class Orders {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false,length = 32)
    private String customerName;

    @Column(nullable = false)
    private Date orderDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false,name="shipping_id")
    private ShippingAddress shippingAddress;

    @OneToMany(fetch=FetchType.EAGER,cascade = CascadeType.ALL,targetEntity = OrderedProduct.class)
    private List<OrderedProduct> orderList;

    @Column(nullable = false)
    private Double total;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        if(orderDate == null)
            orderDate=new java.sql.Date(new java.util.Date().getTime());
        this.orderDate = orderDate;
    }

    public ShippingAddress getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(ShippingAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public List<OrderedProduct> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderedProduct> orderList) {
        this.orderList = orderList;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
