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
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jacekg.teamfinder.exceptions.ErrorResponse;
import com.jacekg.teamfinder.jwt.JwtAuthenticationEntryPoint;
import com.jacekg.teamfinder.jwt.JwtRequestFilter;
import com.jacekg.teamfinder.user.User;

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
	
	private User user;
	
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
				1L,
				"gameName",
				"football",
				10, 60, 25,
				"sport hall",
				"address",
				LocalDateTime.of(LocalDate.of(2022, 1, 1), LocalTime.of(10, 0)),
				"game description",
				0, 0);
		
		user = new User();
		user.setId(1L);
	}

	@Test
	void createGame_ShouldReturn_StatusCreated_AndGame() throws Exception {
		
		TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken(user,null);
		
		String jsonBody = objectMapper.writeValueAsString(gameRequest);
		
		when(gameService.save(any(GameRequest.class), any(Principal.class))).thenReturn(gameResponse);
		
		String url = "/v1/games";
		
		MvcResult mvcResult = mockMvc.perform(post(url)
				.contentType(MediaType.APPLICATION_JSON)
				.principal(testingAuthenticationToken)
                .content(jsonBody))
				.andExpect(status().isCreated()).andReturn();
		
		String returnedGame = mvcResult.getResponse().getContentAsString();
		
		GameResponse gameResponse = objectMapper.readValue(returnedGame, GameResponse.class);
		
		assertThat(gameResponse).hasFieldOrPropertyWithValue("id", 1L);
		assertThat(gameResponse).hasFieldOrPropertyWithValue("name", "gameName");
		assertThat(gameResponse).hasFieldOrPropertyWithValue("sportDisciplineName", "football");
		assertThat(gameResponse).hasFieldOrPropertyWithValue("amountOfPlayers", 10);
		assertThat(gameResponse).hasFieldOrPropertyWithValue("duration", 60);
		assertThat(gameResponse).hasFieldOrPropertyWithValue("price", 25);
		assertThat(gameResponse).hasFieldOrPropertyWithValue("venueName", "sport hall");
		assertThat(gameResponse).hasFieldOrPropertyWithValue("venueAddress", "address");
		assertThat(gameResponse).hasFieldOrPropertyWithValue("date", LocalDateTime.of(LocalDate.of(2022, 1, 1), LocalTime.of(10, 0)));
		assertThat(gameResponse).hasFieldOrPropertyWithValue("description", "game description");
	}
	
	@Test
	void createGame_ShouldThrow_MethodArgumentNotValidException() throws Exception {
		
		gameRequest.setAmountOfPlayers(0);
		
		TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken(user,null);
		
		String jsonBody = objectMapper.writeValueAsString(gameRequest);
		
		when(gameService.save(any(GameRequest.class), any(Principal.class))).thenReturn(gameResponse);
		
		String url = "/v1/games";
		
		MvcResult mvcResult = mockMvc.perform(post(url)
				.contentType(MediaType.APPLICATION_JSON)
				.principal(testingAuthenticationToken)
                .content(jsonBody))
				.andExpect(status().isBadRequest()).andReturn();
		
		String responseContent = mvcResult.getResponse().getContentAsString();
		
		ErrorResponse errorResponse = objectMapper.readValue(responseContent, ErrorResponse.class);
		
		assertThat(errorResponse).hasFieldOrPropertyWithValue("message", "validation error");
	}

}







