package com.bookstore.bookstore.repository;

import com.bookstore.bookstore.domain.BookToCartItem;
import com.bookstore.bookstore.domain.CartItem;
import org.springframework.data.repository.CrudRepository;

public interface BookToCartItemRepository extends CrudRepository<BookToCartItem, Long>{
    void deleteByCartItem(CartItem cartItem);
}
