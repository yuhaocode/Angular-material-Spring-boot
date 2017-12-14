package com.bookstore.bookstore.repository;

import com.bookstore.bookstore.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserRepository extends CrudRepository<User,Long> {
    User findByUsername(String username);
    User findByEmail(String email);
    List<User> findAll();
}
