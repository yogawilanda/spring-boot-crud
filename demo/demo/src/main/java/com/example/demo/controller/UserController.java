package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.services.UserServices;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

	private final UserServices userServices;

	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable String id) {
		User user = userServices.getUser(id);
		return ResponseEntity.ok(user);
	}
	
	// kalau tanpa ("[route]") jadinya /api/users/ itu dari @reqmap anotasi atas
	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = userServices.getAllUser();
		return ResponseEntity.ok(users);
	}

}
