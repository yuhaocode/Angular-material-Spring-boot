package com.bookstore.bookstore.service;


import com.bookstore.bookstore.domain.*;
import org.springframework.http.ResponseEntity;

public interface OrderService {

    Order createOrder(
            ShoppingCart shoppingCart,
            ShippingAddress shippingAddress,
            BillingAddress billingAddress,
            Payment payment,
            String shippingMethod,
            User user
    );
    Order changeStatus(Order order, User user);

}

