package com.example.authentication.dto;

import javax.validation.constraints.NotBlank;

public class ChangePasswordDTO {

	@NotBlank(message = "username.not.valid")
	private String username;
	@NotBlank(message = "currentPassword.not.valid")
	private String currentPassword;
	@NotBlank(message = "newPassword.not.valid")
	private String newPassword;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}
