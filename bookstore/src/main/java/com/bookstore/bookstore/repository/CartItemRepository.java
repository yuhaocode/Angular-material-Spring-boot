package com.bookstore.bookstore.repository;

import com.bookstore.bookstore.domain.CartItem;
import com.bookstore.bookstore.domain.ShoppingCart;
import org.springframework.data.repository.CrudRepository;


import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface CartItemRepository extends CrudRepository<CartItem, Long>{
    List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
}
