package com.jacekg.teamfinder.jwt;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jacekg.teamfinder.user.User;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	
	private final String jwttoken;
	
	private Long id;

	private String username;

	private String email;

//	private String role;
	
	@JsonCreator
	public JwtResponse(@JsonProperty("token") String jwttoken, User user) {
		
		this.jwttoken = jwttoken;
		this.id = user.getId();
		this.username = user.getUsername();
		this.email = user.getEmail();
//		this.role = user.getRoleName();
	}

//	public String getToken() {
//		return this.jwttoken;
//	}
}