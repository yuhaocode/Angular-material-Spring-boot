package com.bookstore.bookstore.service;

import com.bookstore.bookstore.domain.User;
import com.bookstore.bookstore.domain.UserBilling;
import com.bookstore.bookstore.domain.UserPayment;
import com.bookstore.bookstore.domain.UserShipping;
import com.bookstore.bookstore.domain.security.userRole;

import java.util.Set;

public interface UserService {
    User createUser(User user, Set<userRole> userRoles);

    User findByUsername(String username);

    User findByEmail(String email);

    User save(User user);

    User findById(Long id);

    void updateUserPaymentInfo(UserBilling userBilling, UserPayment userPayment, User user);

    void updateUserBilling(UserBilling userBilling, UserPayment userPayment, User user);

    void setUserDefaultPayment(Long userPaymentId, User user);

     void updateUserShipping(UserShipping userShipping, User user);

     void setUserDefaultShipping(Long userShippingId, User user);
}
