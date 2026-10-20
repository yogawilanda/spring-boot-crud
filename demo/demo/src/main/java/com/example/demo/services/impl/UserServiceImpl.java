package com.example.demo.services.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.config.ExceptionCustom;
import com.example.demo.model.User;
import com.example.demo.repositories.UserRepo;
import com.example.demo.services.UserServices;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserServices {

    private final UserRepo userRepo;

    @Override
    public User getUser(String id) {
        return userRepo.findById(id)
                .orElseThrow(() ->
                        new ExceptionCustom("User not found", HttpStatus.NOT_FOUND));
    }

    @Override
    public List<User> getAllUser() {
        return userRepo.findAll();
    }
}