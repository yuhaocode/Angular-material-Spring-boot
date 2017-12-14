package com.bookstore.bookstore;
import com.bookstore.bookstore.config.SecurityUtility;
import com.bookstore.bookstore.domain.User;
import com.bookstore.bookstore.domain.security.Role;
import com.bookstore.bookstore.domain.security.userRole;
import com.bookstore.bookstore.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class BookstoreApplication implements CommandLineRunner {

    @Autowired
    private UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(BookstoreApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        User user1 = new User();
        user1.setFirstName("p");
        user1.setLastName("j");
        user1.setUsername("p");
        user1.setPassword(SecurityUtility.passwordEncoder().encode("p"));
        user1.setEmail("JAdams@gmail.com");
        Set<userRole> userRoles = new HashSet<>();
        Role role1 = new Role();
        role1.setRoleId(1);
        role1.setName("ROLE_USER");
        userRoles.add(new userRole(user1, role1));

        userService.createUser(user1, userRoles);

        userRoles.clear();

        User user2 = new User();
        user2.setFirstName("Admin");
        user2.setLastName("Admin");
        user2.setUsername("admin");
        user2.setPassword(SecurityUtility.passwordEncoder().encode("p"));
        user2.setEmail("Admin@gmail.com");
        Role role2 = new Role();
        role2.setRoleId(0);
        role2.setName("ROLE_ADMIN");
        userRoles.add(new userRole(user2, role2));

        userService.createUser(user2, userRoles);
    }
}
