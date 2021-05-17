package com.commerce.backend.model.request.user;

import com.commerce.backend.validator.CustomEmail;
import com.commerce.backend.validator.PasswordMatches;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.NumberFormat;

@Data
@PasswordMatches
public class RegisterUserRequest {

	@NotBlank
	@Size(min = 3, max = 52)
	@CustomEmail
	private String email;

	@NotBlank
	@Size(min = 3, max = 12)
	@NumberFormat
	private String phone;

	@NotBlank
	@Size(min = 3, max = 100)
	private String name;

	@NotBlank
	@Size(min = 5, max = 50)
	private String password;

	@NotBlank
	@Size(min = 5, max = 50)
	private String passwordRepeat;
}
