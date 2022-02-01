package com.jacekg.teamfinder.jwt;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(JwtAuthenticationController.class)
@AutoConfigureMockMvc(addFilters = false)
class JwtAuthenticationControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private JwtTokenUtil jwtTokenUtil;
	
	@MockBean
	private AuthenticationManager authenticationManager;
	
	@MockBean
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@MockBean
	private JwtUserDetailsService jwtUserDetailsService;

	@MockBean
	private JwtRequestFilter jwtRequestFilter;
	
	private JwtRequest jwtRequest;
	
	@BeforeEach
	void setUp() throws Exception {
		
		jwtRequest = new JwtRequest("admin", "password");
	}

	@Test
	void createAuthenticationToken_ShouldReturn_JwtResponseWithToken() {
		
		when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class)))
			.thenReturn(null);
	}

}
