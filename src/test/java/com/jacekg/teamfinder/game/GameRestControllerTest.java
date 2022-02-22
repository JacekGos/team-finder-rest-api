package com.jacekg.teamfinder.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jacekg.teamfinder.jwt.JwtAuthenticationEntryPoint;
import com.jacekg.teamfinder.jwt.JwtRequestFilter;
import com.jacekg.teamfinder.venue.VenueRequest;
import com.jacekg.teamfinder.venue.VenueResponse;

@WebMvcTest(GameRestController.class)
@AutoConfigureMockMvc(addFilters = false)
class GameRestControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private GameService gameService;
	
	@MockBean
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@MockBean
	private UserDetailsService jwtUserDetailsService;

	@MockBean
	private JwtRequestFilter jwtRequestFilter;
	
	private GameRequest gameRequest;
	
	private GameResponse gameResponse;
	
	private Principal principal;
	
	@BeforeEach
	void setUp() throws Exception {
		
		gameRequest = new GameRequest(
				"gameName",
				"football",
				10, 60, 25, 1,
				LocalDate.of(2022, 1, 1),
				10,
				"game description");
		
		gameResponse = new GameResponse(
				"gameName",
				"football",
				10, 60, 25,
				"sport hall",
				"address",
				LocalDateTime.of(LocalDate.of(2022, 1, 1), LocalTime.of(10, 0)),
				"game description");
		
		principal = new Principal() {
			
			@Override
			public String getName() {
				return "admin";
			}
		};
	}

	@Test
	void createGame_ShouldReturn_StatusCreated_AndGame() throws Exception {
		
		String jsonBody = objectMapper.writeValueAsString(gameRequest);
		
		when(gameService.save(any(GameRequest.class), any(Principal.class))).thenReturn(gameResponse);
		
		String url = "/v1/games";
		
		MvcResult mvcResult = mockMvc.perform(post(url)
				.contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
				.andExpect(status().isCreated()).andReturn();
		
		String returnedGame = mvcResult.getResponse().getContentAsString();
		
		VenueResponse venueResponse = objectMapper.readValue(returnedGame, VenueResponse.class);
		
		assertThat(venueResponse).hasFieldOrPropertyWithValue("name", "sport venue");
		assertThat(venueResponse).hasFieldOrPropertyWithValue("address", "address 1");
		assertThat(venueResponse).hasFieldOrPropertyWithValue("venueTypeName", "sports hall");
	}

}







