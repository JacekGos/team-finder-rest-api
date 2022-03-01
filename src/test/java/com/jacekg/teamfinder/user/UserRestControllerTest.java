package com.jacekg.teamfinder.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jacekg.teamfinder.exceptions.ErrorResponse;
import com.jacekg.teamfinder.jwt.JwtAuthenticationEntryPoint;
import com.jacekg.teamfinder.jwt.JwtRequestFilter;


@WebMvcTest(UserRestController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserRestControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private UserService userService;
	
	@MockBean
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@MockBean
	private UserDetailsService jwtUserDetailsService;

	@MockBean
	private JwtRequestFilter jwtRequestFilter;
	
	private UserRequest userRequest;
	
	private UserResponse userResponse;
	
	@BeforeEach
	void setUp() throws Exception {
		
		userRequest = new UserRequest(
				10L,
				"username",
				"password",
				"password",
				"email@email.com"); 
		
		userResponse = new UserResponse(
				10L,
				"username",
				"email",
				"ROLE_USER");
	}
	
	@Test
	@Disabled
	void createUser_ShouldReturn_StatusCreated_And_UserWithAdminRole() throws Exception {
	
		String jsonBody = objectMapper.writeValueAsString(userRequest);

		when(userService.save(any(UserRequest.class))).thenReturn(userResponse);
		
		String url = "/v1/signup";
		
		MvcResult mvcResult = mockMvc.perform(post(url)
				.contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
				.andExpect(status().isCreated()).andReturn();
		
		String returnedUser = mvcResult.getResponse().getContentAsString();
		
		UserResponse userResponse = objectMapper.readValue(returnedUser, UserResponse.class);
		
		assertThat(userResponse).isNotNull();
		assertThat(userResponse).hasFieldOrPropertyWithValue("id", 10L);
		assertThat(userResponse).hasFieldOrPropertyWithValue("username", "username");
		assertThat(userResponse).hasFieldOrPropertyWithValue("email", "email");
		assertThat(userResponse).hasFieldOrPropertyWithValue("role", "ROLE_USER");
	}
	
	@Test
	void createUser_ShouldReturn_StatusCreated_And_UserWithUserRole() throws Exception {
		
		String jsonBody = objectMapper.writeValueAsString(userRequest);

		when(userService.save(any(UserRequest.class))).thenReturn(userResponse);
		
		String url = "/v1/signup";
		
		MvcResult mvcResult = mockMvc.perform(post(url)
				.contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
				.andExpect(status().isCreated()).andReturn();
		
		String returnedUser = mvcResult.getResponse().getContentAsString();
		
		UserResponse userResponse = objectMapper.readValue(returnedUser, UserResponse.class);
		
		assertThat(userResponse).isNotNull();
		assertThat(userResponse).hasFieldOrPropertyWithValue("id", 10L);
		assertThat(userResponse).hasFieldOrPropertyWithValue("username", "username");
		assertThat(userResponse).hasFieldOrPropertyWithValue("email", "email");
		assertThat(userResponse).hasFieldOrPropertyWithValue("role", "ROLE_USER");
	}
	
	@Test
	void createUser_ShouldThrow_MethodArgumentNotValidException() throws Exception {
		
		userRequest.setEmail("email@");
		userRequest.setMatchingPassword(" ");
		
		String jsonBody = objectMapper.writeValueAsString(userRequest);

		when(userService.save(any(UserRequest.class))).thenReturn(userResponse);

		String url = "/v1/signup";

		MvcResult mvcResult = mockMvc.perform(post(url)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonBody))
				.andExpect(status().isBadRequest())
				.andReturn();
		
		String responseContent = mvcResult.getResponse().getContentAsString();
		
		ErrorResponse errorResponse = objectMapper.readValue(responseContent, ErrorResponse.class);
		
		assertThat(errorResponse).hasFieldOrPropertyWithValue("message", "validation error");
	}

}
