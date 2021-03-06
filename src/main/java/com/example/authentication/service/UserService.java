package com.example.authentication.service;

import java.util.List;

import com.example.authentication.model.User;

public interface UserService {
	User findByUsername(String username);

	List<User> getAllUsers();

	void save(User u);
}
