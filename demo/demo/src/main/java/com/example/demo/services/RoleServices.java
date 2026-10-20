package com.example.demo.services;

import java.util.List;

import com.example.demo.model.Role;

public interface RoleServices {

    Role getRole(String alias);

    List<Role> getAllRoles();
}