package com.bookstore.bookstore.domain.security;

import com.bookstore.bookstore.domain.User;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_role")
public class userRole implements Serializable{
    private static final long serialVersionUID = 232323232L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userRoleId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    private Role role;

    public userRole(){}

    public userRole(User user, Role role){
        this.role = role;
        this.user = user;
    }
    public long getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(long userRoleId) {
        this.userRoleId = userRoleId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }








}
