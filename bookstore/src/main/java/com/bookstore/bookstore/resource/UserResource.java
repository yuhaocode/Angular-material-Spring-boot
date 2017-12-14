package com.bookstore.bookstore.resource;

import com.bookstore.bookstore.config.SecurityConfig;
import com.bookstore.bookstore.config.SecurityUtility;
import com.bookstore.bookstore.domain.User;
import com.bookstore.bookstore.domain.security.Role;
import com.bookstore.bookstore.domain.security.userRole;
import com.bookstore.bookstore.service.UserService;
import com.bookstore.bookstore.utility.MailConstructor;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class UserResource {

    @Autowired
    private MailConstructor mailConstructor;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/newUser", method = RequestMethod.POST)
    public ResponseEntity newUserPost(
            HttpServletRequest request,
            @RequestBody HashMap<String, String> mapper) throws Exception{
        String username = mapper.get("username");
        String userEmail = mapper.get("email");
        String firstName = mapper.get("firstName");
        String lastName = mapper.get("lastName");
        String password = mapper.get("password");

        if(userService.findByUsername(username) != null){
            return new ResponseEntity("usernameExists", HttpStatus.BAD_REQUEST);
        }

        if(userService.findByEmail(userEmail) != null){
            return new ResponseEntity("emailExists", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(userEmail);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
        user.setPassword(encryptedPassword);

        Role role = new Role();
        role.setRoleId(1);
        role.setName("ROlE_USER");
        Set<userRole> userRoles = new HashSet<>();
        userRoles.add(new userRole(user, role));


        userService.createUser(user, userRoles);

        SimpleMailMessage email = mailConstructor.constructNewUserEmail(user, password);
        mailSender.send(email);

        return new ResponseEntity("User Added Successfully", HttpStatus.OK);
    }


    @RequestMapping(value = "/forgetPassword", method = RequestMethod.POST)
    public ResponseEntity forgetPasswordPost(
            HttpServletRequest request,
            @RequestBody HashMap<String, String> email) throws Exception{
        User user = userService.findByEmail(email.get("email"));

        if(user == null){
            return new ResponseEntity("Email not found", HttpStatus.BAD_REQUEST);
        }

        String password = SecurityUtility.randomPassword();
        String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);

        user.setPassword((encryptedPassword));

        userService.save(user);
        SimpleMailMessage newEmail = mailConstructor.constructNewUserEmail(user, password);
        mailSender.send(newEmail);

        return new ResponseEntity("Email Sent!", HttpStatus.OK);
    }

    @RequestMapping(value="/updateUserInfo", method=RequestMethod.POST)
    public ResponseEntity profileInfo(
            @RequestBody HashMap<String, Object> mapper
    ) throws Exception {
    //why
        int id = (Integer) mapper.get("id");
        String email = (String) mapper.get("email");
        String username = (String) mapper.get("username");
        String firstName = (String) mapper.get("firstName");
        String lastName = (String) mapper.get("lastName");
        String newPassword = (String) mapper.get("newPassword");
        String currentPassword = (String) mapper.get("currentPassword");

        User currentUser = userService.findById(Long.valueOf(id));

        if (currentUser == null) {
            throw new Exception("User not found");
        }

        if (userService.findByEmail(email) != null) {
            if (userService.findByEmail(email).getId() != currentUser.getId()) {
                return new ResponseEntity("Email not found!", HttpStatus.BAD_REQUEST);
            }
        }

        if (userService.findByUsername(username) != null) {
            if (userService.findByUsername(username).getId() != currentUser.getId()) {
                return new ResponseEntity("Username not found!", HttpStatus.BAD_REQUEST);
            }
        }

        SecurityConfig securityConfig = new SecurityConfig();


        BCryptPasswordEncoder passwordEncoder = SecurityUtility.passwordEncoder();
        String dbPassword = currentUser.getPassword();

        if (null != currentPassword)
            if (passwordEncoder.matches(currentPassword, dbPassword)) {
                if (newPassword != null && !newPassword.isEmpty() && !newPassword.equals("")) {
                    currentUser.setPassword(passwordEncoder.encode(newPassword));
                }
                currentUser.setEmail(email);
            } else {
                return new ResponseEntity("Incorrect current password!", HttpStatus.BAD_REQUEST);
            }


        currentUser.setFirstName(firstName);
        currentUser.setLastName(lastName);
        currentUser.setUsername(username);


        userService.save(currentUser);

        return new ResponseEntity("Update Success", HttpStatus.OK);
    }

    @RequestMapping("/getCurrentUser")
    public User getCurrrentUser(Principal principal){
        User user = new User();
        if(null != principal){
             user = userService.findByUsername(principal.getName());
        }


        return user;
    }





}
