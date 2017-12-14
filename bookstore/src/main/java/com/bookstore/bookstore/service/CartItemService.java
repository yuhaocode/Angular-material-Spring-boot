package com.bookstore.bookstore.service;

import com.bookstore.bookstore.domain.Book;
import com.bookstore.bookstore.domain.CartItem;
import com.bookstore.bookstore.domain.ShoppingCart;
import com.bookstore.bookstore.domain.User;

import java.util.List;

public interface CartItemService {

    CartItem addBookToCartItem(Book book, User user, int qty);

    List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);

    //	List<CartItem> findByOrder(Order order);

    CartItem updateCartItem(CartItem cartItem);

    void removeCartItem(CartItem cartItem);

    CartItem findById(Long id);

    CartItem save(CartItem cartItem);

}
