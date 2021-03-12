package com.example.authentication.repository;

import java.util.List;

import com.example.authentication.model.User;

public interface UserRepository {

	User findByUsername(String username);

	List<User> getAllUsers();

	void insert(User u);

	void update(User u);
}
