package com.jacekg.teamfinder.jwt;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jacekg.teamfinder.exceptions.InvalidCredentialsException;
import com.jacekg.teamfinder.exceptions.UserNotValidException;
import com.jacekg.teamfinder.game.GameRestController;
import com.jacekg.teamfinder.user.User;
import com.jacekg.teamfinder.user.UserRepository;
import com.jacekg.teamfinder.user.UserService;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;
	
	@Autowired
	private UserService userService;
	
	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationController.class);
	
//	@CrossOrigin(origins = "http://localhost:4000")
	@CrossOrigin
	@PostMapping("/v1/signin")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);
		
		User user = userService.getUserData(authenticationRequest.getUsername());

		return ResponseEntity.ok(new JwtResponse(
				token, user.getId(), user.getUsername(), user.getEmail()));
	}

	private void authenticate(String username, String password) throws Exception {
		
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("User disabled", e);
		} catch (BadCredentialsException e) {
			throw new InvalidCredentialsException("Invalid username or password", e);
		}
	}
}
