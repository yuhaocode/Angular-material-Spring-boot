package com.bookstore.bookstore.service.impl;

import java.security.Principal;
import java.util.Calendar;
import java.util.List;

import com.bookstore.bookstore.domain.*;
import com.bookstore.bookstore.repository.BillingAddressRepository;
import com.bookstore.bookstore.repository.OrderRepository;
import com.bookstore.bookstore.repository.PaymentRepository;
import com.bookstore.bookstore.repository.ShippingAddressRepository;
import com.bookstore.bookstore.service.BookService;
import com.bookstore.bookstore.service.CartItemService;
import com.bookstore.bookstore.service.OrderService;
import com.bookstore.bookstore.service.UserService;
import com.bookstore.bookstore.utility.MailConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BillingAddressRepository billingAddressRepository;

    @Autowired
    private ShippingAddressRepository shippingAddressRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private BookService bookService;

    @Autowired
    private MailConstructor mailConstructor;

    @Autowired
    private UserService userService;

    public synchronized Order createOrder(
            ShoppingCart shoppingCart,
            ShippingAddress shippingAddress,
            BillingAddress billingAddress,
            Payment payment,
            String shippingMethod,
            User user
    ){
        Order order = new Order();
        order.setBillingAddress(billingAddress);
        order.setOrderStatus("created");
        order.setPayment(payment);
        order.setShippingAddress(shippingAddress);
        order.setShippingMethod(shippingMethod);

        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);

        for (CartItem cartItem : cartItemList) {
            Book book = cartItem.getBook();
            cartItem.setOrder(order);
            book.setInStockNumber(book.getInStockNumber()-cartItem.getQty());
        }

        order.setCartItemList(cartItemList);
        order.setOrderDate(Calendar.getInstance().getTime());
        order.setOrderTotal(shoppingCart.getGrandTotal());
        shippingAddress.setOrder(order);
        billingAddress.setOrder(order);
        payment.setOrder(order);
        order.setUser(user);
        order = orderRepository.save(order);

        return order;
    }

    public Order findOne(Long id) {
        return orderRepository.findOne(id);
    }

    @Override
    public Order changeStatus(
            Order order, User user) {
        order.setUser(user);
        order.setOrderStatus("cancel");
        return orderRepository.save(order);
    }
}
