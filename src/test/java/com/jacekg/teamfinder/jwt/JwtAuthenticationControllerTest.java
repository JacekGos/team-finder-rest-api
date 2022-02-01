package com.jacekg.teamfinder.jwt;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	
	private JwtRequest jwtRequest;
	
	private UserDetails userDetails;
	
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
	}

	@Test
	void createAuthenticationToken_ShouldReturn_JwtResponseWithToken() throws Exception {
		
		String jsonBody = objectMapper.writeValueAsString(jwtRequest);
		
		doNothing().when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
		when(userDetailsService.loadUserByUsername(any(String.class))).thenReturn(userDetails);
		when(jwtTokenUtil.generateToken(userDetails)).thenReturn("abc123");
		
		String url = "/v1/signin";
		
		MvcResult mvcResult = mockMvc.perform(post(url)
				.contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
				.andExpect(status().isCreated()).andReturn();
		
		String returnedResponse = mvcResult.getResponse().getContentAsString();
		
		JwtResponse jwtResponse = objectMapper.readValue(returnedResponse, JwtResponse.class);
		
		System.out.println("response " + returnedResponse);
		
		verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class)).equals(1);
	}

}
