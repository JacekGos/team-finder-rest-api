package com.jacekg.teamfinder.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
	
	private Long id;

	private String username;

	private String email;

	private String role;
}
