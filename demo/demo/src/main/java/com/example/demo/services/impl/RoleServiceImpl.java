package com.example.demo.services.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.config.ExceptionCustom;
import com.example.demo.model.Role;
import com.example.demo.repositories.RoleRepo;
import com.example.demo.services.RoleServices;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleServices {

    private final RoleRepo roleRepo;

    @Override
    public Role getRole(String alias) {
        return roleRepo.findById(alias)
                .orElseThrow(() ->
                        new ExceptionCustom("Role not found", HttpStatus.NOT_FOUND));
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepo.findAll();
    }
}