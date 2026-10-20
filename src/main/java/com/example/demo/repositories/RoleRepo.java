package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Role;

public interface RoleRepo extends JpaRepository<Role, String> {
}