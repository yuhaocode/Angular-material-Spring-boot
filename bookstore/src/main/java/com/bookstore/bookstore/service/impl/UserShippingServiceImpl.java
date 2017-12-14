package com.bookstore.bookstore.service.impl;

import com.bookstore.bookstore.domain.UserShipping;
import com.bookstore.bookstore.repository.UserShippingRepository;
import com.bookstore.bookstore.service.UserShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserShippingServiceImpl implements UserShippingService{

    @Autowired
    private UserShippingRepository userShippingRepository;

    public UserShipping findById(Long id){
        return userShippingRepository.findOne(id);
    }

    public void removeById(Long id){
        userShippingRepository.delete(id);
    }

}
