package com.tmkcomputers.springjwt.payload.request;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequest {
	@NotBlank
	private String username;

	@NotBlank
	private String currentPassword;

	@NotBlank
	private String newPassword;
}
