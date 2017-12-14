package com.bookstore.bookstore.repository;

import com.bookstore.bookstore.domain.security.Role;
import com.bookstore.bookstore.domain.security.userRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

public interface RoleRepository extends CrudRepository<Role, Long> {

}
