package com.jacekg.teamfinder.jwt;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jacekg.teamfinder.exceptions.ErrorResponse;
import com.jacekg.teamfinder.role.Role;
import com.jacekg.teamfinder.user.User;
import com.jacekg.teamfinder.user.UserService;

@WebMvcTest(JwtAuthenticationController.class)
@AutoConfigureMockMvc(addFilters = false)
class JwtAuthenticationControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private JwtTokenUtil jwtTokenUtil;
	
	@MockBean
	private AuthenticationManager authenticationManager;
	
	@MockBean
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@MockBean
	private JwtUserDetailsService userDetailsService;

	@MockBean
	private JwtRequestFilter jwtRequestFilter;
	
	@MockBean
	private UserService userService;
	
	private JwtRequest jwtRequest;
	
	private UserDetails userDetails;
	
	private User user;
	
	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationControllerTest.class);
	
	@BeforeEach
	void setUp() throws Exception {
		
		List<GrantedAuthority> roles = new ArrayList<>();
		roles.add(new SimpleGrantedAuthority("ROLE_USER"));
		
		jwtRequest = new JwtRequest("admin", "password");
		
		userDetails = new org.springframework.security.core.userdetails.User(
				"username", 
				"password", 
				true, 
				true, 
				true, 
				true, 
				roles);
		
		user = new User(
				10L,
				"username",
				"password",
				"email",
				true, true, true, true,
				Arrays.asList(new Role(1L, "ROLE_USER"), new Role(2L, "ROLE_ADMIN")),
				null,
				null);
	}

	@Test
	void createAuthenticationToken_ShouldReturn_JwtResponseWithToken() throws Exception {
		
		String jsonBody = objectMapper.writeValueAsString(jwtRequest);
		
		when(userDetailsService.loadUserByUsername(any(String.class))).thenReturn(userDetails);
		when(jwtTokenUtil.generateToken(userDetails)).thenReturn("abc123");
		when(userService.getUserData(anyString())).thenReturn(user);
		
		String url = "/v1/signin";
		
		MvcResult mvcResult = mockMvc.perform(post(url)
				.contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
				.andExpect(status().isOk()).andReturn();
		
		
		String returnedResponse = mvcResult.getResponse().getContentAsString();
		logger.info("jwt response:" + returnedResponse);
		
		JwtResponse jwtResponse = objectMapper.readValue(returnedResponse, JwtResponse.class);
		logger.info("test ");
		
		assertThat(jwtResponse).hasFieldOrPropertyWithValue("jwttoken", "abc123");
		assertThat(jwtResponse).hasFieldOrPropertyWithValue("id", 10L);
		assertThat(jwtResponse).hasFieldOrPropertyWithValue("username", "username");
		assertThat(jwtResponse).hasFieldOrPropertyWithValue("email", "email");
	}
	
	@Test
	void createAuthenticationToken_ShouldReturn_InvalidCredentialsException() throws Exception {
		
		String jsonBody = objectMapper.writeValueAsString(jwtRequest);
		
		when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class)))
			.thenThrow(BadCredentialsException.class);
		
		String url = "/v1/signin";
		
		MvcResult mvcResult = mockMvc.perform(post(url)
				.contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
				.andExpect(status().isBadRequest()).andReturn();
		
		String responseContent = mvcResult.getResponse().getContentAsString();
		
		ErrorResponse errorResponse = objectMapper.readValue(responseContent, ErrorResponse.class);
		
		assertThat(errorResponse).hasFieldOrPropertyWithValue("message", "Invalid username or password");
	}

}
