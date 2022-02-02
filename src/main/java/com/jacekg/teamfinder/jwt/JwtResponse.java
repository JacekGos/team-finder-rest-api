package com.jacekg.teamfinder.jwt;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	
	private final String jwttoken;
	
	@JsonCreator
	public JwtResponse(@JsonProperty("token") String jwttoken) {
		this.jwttoken = jwttoken;
	}

	public String getToken() {
		return this.jwttoken;
	}
}