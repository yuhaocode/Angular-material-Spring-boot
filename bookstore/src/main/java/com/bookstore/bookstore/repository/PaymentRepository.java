package com.bookstore.bookstore.repository;

import com.bookstore.bookstore.domain.Payment;
import org.springframework.data.repository.CrudRepository;

public interface PaymentRepository extends CrudRepository<Payment, Long>{
}
