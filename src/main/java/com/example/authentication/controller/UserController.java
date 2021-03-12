package com.example.authentication.controller;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.authentication.dto.UserDTO;
import com.example.authentication.model.User;
import com.example.authentication.service.UserService;
import com.example.authentication.transform.UserTransform;

@RestController
@RequestMapping("/users")
public class UserController {

	private UserService userService;
	private DateFormat dateFormat;

	@Autowired
	public UserController(UserService userService, DateFormat dateFormat) {
		this.userService = userService;
		this.dateFormat = dateFormat;
	}

	@GetMapping
	@Secured("ROLE_ADMIN")
	public List<UserDTO> allUsers() {
		UserTransform transform = new UserTransform(dateFormat);
		List<User> users = userService.getAllUsers();
		List<UserDTO> userDTOs = new ArrayList<>();
		for (User u : users) {
			userDTOs.add(transform.apply(u));
		}
		return userDTOs;
	}

	@GetMapping("/current")
	public ResponseEntity<UserDTO> currentUserDetail() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		String username = securityContext.getAuthentication().getName();
		User user = userService.findByUsername(username);
		UserTransform transform = new UserTransform(dateFormat);
		return ResponseEntity.ok(transform.apply(user));
	}

}
