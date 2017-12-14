package com.bookstore.bookstore.service;

import com.bookstore.bookstore.domain.UserShipping;

public interface UserShippingService {

    UserShipping findById(Long id);

    void removeById(Long id);
}
