package com.example.authentication.transform;

import java.text.DateFormat;
import java.text.ParseException;

import com.example.authentication.dto.CreateUserDTO;
import com.example.authentication.dto.UserDTO;
import com.example.authentication.model.User;

public class UserTransform {

	private DateFormat dateFormat;
	private RoleTransform roleTransform;

	public UserTransform(DateFormat dateFormat) {
		this.dateFormat = dateFormat;
		roleTransform = new RoleTransform();
	}

	public User apply(CreateUserDTO dto) throws ParseException {
		User user = new User();
		user.setUsername(dto.getUsername());
		user.setPassword(dto.getPassword());
		user.setEmail(dto.getEmail());
		user.setFirstname(dto.getFirstname());
		user.setLastname(dto.getLastname());
		user.setActive(true);
		user.setRoles(roleTransform.apply(dto.getRoles()));
		user.setDob(dateFormat.parse(dto.getDob()));

		return user;
	}

	public UserDTO apply(User user) {
		UserDTO dto = new UserDTO();
		dto.setUsername(user.getUsername());
		dto.setEmail(user.getEmail());
		dto.setFirstname(user.getFirstname());
		dto.setLastname(user.getLastname());
		if (user.getDob() != null) {
			dto.setDob(dateFormat.format(user.getDob()));
		}
		dto.setRoles(roleTransform.apply(user.getRoles()));
		dto.setActive(user.isActive());
		return dto;
	}
}
