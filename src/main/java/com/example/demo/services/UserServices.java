package com.example.demo.services;

import java.util.List;

import com.example.demo.model.User;

public interface UserServices {
	public User getUser(String id);

	public List<User> getAllUser();
}
