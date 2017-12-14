package com.bookstore.bookstore.service.impl;

import com.bookstore.bookstore.domain.*;
import com.bookstore.bookstore.repository.BookToCartItemRepository;
import com.bookstore.bookstore.repository.CartItemRepository;
import com.bookstore.bookstore.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
public class CartItemServiceImpl implements CartItemService{

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private BookToCartItemRepository bookToCartItemRepository;

    public CartItem addBookToCartItem(Book book, User user, int qty){
        List<CartItem> cartItemLIst = findByShoppingCart(user.getShoppingCart());

        for(CartItem cartItem : cartItemLIst){
            if(book.getId() == cartItem.getBook().getId()){
                cartItem.setQty(cartItem.getQty() + qty);
                cartItem.setSubtotal(new BigDecimal(book.getOurPrice()).multiply(new BigDecimal(qty)));
                cartItemRepository.save(cartItem);
                return cartItem;
            }
        }

        CartItem cartItem = new CartItem();
        cartItem.setShoppingCart(user.getShoppingCart());
        cartItem.setBook(book);
        cartItem.setQty(qty);
        cartItem.setSubtotal(new BigDecimal(book.getOurPrice()).multiply(new BigDecimal(qty)));

        cartItem = cartItemRepository.save(cartItem);

        BookToCartItem bookToCartItem = new BookToCartItem();
        bookToCartItem.setBook(book);
        bookToCartItem.setCartItem(cartItem);
        bookToCartItemRepository.save(bookToCartItem);

        return cartItem;
    }
    @Transactional
    public void removeCartItem(CartItem cartItem){
        bookToCartItemRepository.deleteByCartItem(cartItem);
        cartItemRepository.delete(cartItem);
    }

    public List<CartItem> findByShoppingCart(ShoppingCart shoppingCart){
        return cartItemRepository.findByShoppingCart(shoppingCart);
    }

    public CartItem updateCartItem(CartItem cartItem){
        BigDecimal bigDecimal = new BigDecimal(cartItem.getBook().getOurPrice()).multiply(new BigDecimal(cartItem.getQty()));
        bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);

        cartItem.setSubtotal(bigDecimal);
        cartItemRepository.save(cartItem);
        return cartItem;
    }

    public CartItem findById(Long id){
        return cartItemRepository.findOne(id);
    }

    @Override
    public CartItem save(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

//    public List<CartItem> findByOrder(Order order){
//        return cartItemRepository.findByOrder(order);
//    }
}
