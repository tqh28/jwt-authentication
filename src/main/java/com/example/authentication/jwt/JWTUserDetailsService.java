package com.example.authentication.jwt;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.authentication.model.User;
import com.example.authentication.service.UserService;

@Service
public class JWTUserDetailsService implements UserDetailsService {

	private UserService userService;

	public JWTUserDetailsService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			User user = userService.findByUsername(username);
			return JWTUserDetailsFactory.create(user);
		} catch (Exception e) {
			throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username), e);
		}
	}
}
