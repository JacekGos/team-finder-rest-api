package com.jacekg.teamfinder.user;

import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.status;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController("/v1")
@AllArgsConstructor
public class UserRestController {
	
	private UserService userService;
	
	@PostMapping("/v1/signup")
	public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest) {
		return status(HttpStatus.CREATED).body(userService.save(userRequest));
	}
	
}
