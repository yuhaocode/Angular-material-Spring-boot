package com.bookstore.bookstore.domain.security;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Role implements Serializable{
    @Id
    private int roleId;

    private static final long serialVersionUID = 78767676L;

    private String name;
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<userRole> userRole = new HashSet<>();
    public Role(){}
    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<userRole> getUserRole() {
        return userRole;
    }

    public void setUserRole(Set<userRole> userRole) {
        this.userRole = userRole;
    }



}
