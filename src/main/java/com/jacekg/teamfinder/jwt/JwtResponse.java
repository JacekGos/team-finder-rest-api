package com.jacekg.teamfinder.jwt;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jacekg.teamfinder.user.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	
	private final String jwttoken;
	
	private Long id;

	private String username;

	private String email;
	
	@JsonCreator
	public JwtResponse(
			@JsonProperty("token") String jwttoken, 
			@JsonProperty("id") Long id,
			@JsonProperty("username") String username,
			@JsonProperty("email") String email) {
		
		this.jwttoken = jwttoken;
		this.id = id;
		this.username = username;
		this.email = email;
	}
}