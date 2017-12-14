package com.bookstore.bookstore.repository;

import com.bookstore.bookstore.domain.Order;
import com.bookstore.bookstore.domain.User;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findByUser (User user);

    Order findById(Long id);

}
