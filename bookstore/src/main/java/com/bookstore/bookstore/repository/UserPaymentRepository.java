package com.bookstore.bookstore.repository;

import com.bookstore.bookstore.domain.UserPayment;
import org.springframework.data.repository.CrudRepository;

public interface UserPaymentRepository extends CrudRepository<UserPayment, Long>{

}
