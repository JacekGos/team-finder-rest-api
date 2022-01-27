package com.jacekg.teamfinder.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.jacekg.teamfinder.validation.FieldMatch;
import com.jacekg.teamfinder.validation.ValidEmail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldMatch.List({
	@FieldMatch(first = "password", second = "matchingPassword", message = "passwords must match")
})
public class UserRequest {
	
	private Long id;

	@NotNull(message = "required")
	@Size(min = 1, max = 50, message = "za długa nazwa użytkownika")
	private String username;

	@NotNull(message = "required")
	@Size(max = 30, message = "password too long (max. 30 characters)")
	@Size(min = 5, message = "password too short (min 5 characters)")
	private String password;
	
	@NotNull(message = "required")
	@Size(max = 30, message = "password too long (max. 30 characters)")
	@Size(min = 5, message = "password too short (min 5 characters)")
	private String matchingPassword;

	@ValidEmail
	@NotNull(message = "required")
	@Size(min = 1, max = 50, message = "too long email address")
	private String email;

	private String role;
}
