package com.bookstore.bookstore.resource;

import com.bookstore.bookstore.domain.Order;
import com.bookstore.bookstore.domain.User;
import com.bookstore.bookstore.service.OrderService;
import com.bookstore.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderResource {
    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @RequestMapping("/getOrderList")
    public List<Order> getOrderList(Principal principal){
        User user = userService.findByUsername(principal.getName());
        List<Order> ordersList = user.getOrderList();

        return ordersList;
    }

    @RequestMapping(value = "/changestatus", method = RequestMethod.POST)
    public Order changestatus(@RequestBody  Order order , Principal principal){
        User user = userService.findByUsername(principal.getName());
        return orderService.changeStatus(order, user);
    }
}
