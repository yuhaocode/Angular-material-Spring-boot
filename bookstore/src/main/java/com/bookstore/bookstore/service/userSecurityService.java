package com.bookstore.bookstore.service;

import com.bookstore.bookstore.domain.User;
import com.bookstore.bookstore.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class userSecurityService implements UserDetailsService{
    private static final Logger LOG= LoggerFactory.getLogger(userSecurityService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)throws UsernameNotFoundException{
        User user = userRepository.findByUsername(username);
        if(null == user){
            LOG.warn("Username {} not found", username);
            throw new UsernameNotFoundException("Username " + username+ " not found");
        }
        return user;
    }


}
