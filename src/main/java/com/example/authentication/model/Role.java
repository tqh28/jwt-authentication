package com.example.authentication.model;

public enum Role {

	ROLE_ADMIN(1), ROLE_USER(2);

	private final int roleNumber;

	private Role(int number) {
		this.roleNumber = number;
	}

	public int getRoleNumber() {
		return roleNumber;
	}

}
