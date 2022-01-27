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
	@FieldMatch(first = "password", second = "matchingPassword", message = "podane hasła muszą się zgadzać")
})
public class UserRequest {
	
	private Long id;

	@NotNull(message = "wymagane")
	@Size(min = 1, max = 50, message = "za długa nazwa użytkownika")
	private String username;

	@NotNull(message = "wymagane")
	@Size(max = 30, message = "za długie hasło (max 30 znaków)")
	@Size(min = 5, message = "za krótkie hasło (min 5 znaków)")
	private String password;
	
	@NotNull(message = "wymagane")
	@Size(max = 30, message = "za długie hasło (max 30 znaków)")
	@Size(min = 5, message = "za krótkie hasło (min 5 znaków)")
	private String matchingPassword;

	@NotNull(message = "wymagane")
	@Size(min = 1, max = 50, message = "za długie imię")
	private String firstName;

	@NotNull(message = "wymagane")
	@Size(min = 1, max = 50, message = "za długie nazwisko")
	private String lastName;

	@ValidEmail
	@NotNull(message = "wymagane")
	@Size(min = 1, max = 50, message = "za długi email")
	private String email;

	private String role;

	private boolean isEnabled;

	private boolean isNonExpired;

	private boolean isCredentialsNonExpired;

	private boolean isNonLocked;
}
