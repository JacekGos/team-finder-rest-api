package com.jacekg.teamfinder.game;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.assertj.core.api.Assertions.assertThat;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.jacekg.teamfinder.exceptions.SaveGameException;
import com.jacekg.teamfinder.exceptions.SaveVenueException;
import com.jacekg.teamfinder.role.Role;
import com.jacekg.teamfinder.sport_discipline.SportDiscipline;
import com.jacekg.teamfinder.sport_discipline.SportDisciplineRepository;
import com.jacekg.teamfinder.user.User;
import com.jacekg.teamfinder.user.UserRepository;
import com.jacekg.teamfinder.venue.Term;
import com.jacekg.teamfinder.venue.TermRepository;
import com.jacekg.teamfinder.venue.Venue;
import com.jacekg.teamfinder.venue.VenueRepository;

@ExtendWith(MockitoExtension.class)
class GameServiceImplTest {
	
	@InjectMocks
	GameServiceImpl serviceUnderTest;
	
	@Mock
	private GameRepository gameRepository;
	
	@Mock
	private VenueRepository venueRepository;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private TermRepository termRepository;
	
	@Mock
	private SportDisciplineRepository sportDisciplineRepository;
	
	@Mock
	private ModelMapper modelMapper;
	
	private GameRequest gameRequest;
	
	private GameResponse gameResponse;
	
	private Game game;
	
	private Optional<Venue> venue;
	
	private Principal principal;
	
	private SportDiscipline sportDiscipline;
	
	private User user;
	
	@BeforeEach
	void setUp() {
		
		List<Term> busyTerms = new ArrayList<>();
		
		venue = Optional.ofNullable(new Venue(1L, "sport venue", "address 1", null, null, new ArrayList<Term>()));
		
		principal = new Principal() {
			
			@Override
			public String getName() {
				return "admin";
			}
		};
		
		user = new User(
				10L,
				"username",
				"password",
				"email",
				true, true, true, true,
				Arrays.asList(new Role(1L, "ROLE_USER"), new Role(2L, "ROLE_ADMIN")),
				new HashSet<Game>(), null);
		
		sportDiscipline = new SportDiscipline(1L, "football", new ArrayList<Game>()); 
		
		gameRequest = new GameRequest(
				"gameName",
				"football",
				10, 60, 25, 1,
				LocalDate.now(),
				10,
				"game description"
				);
		
		gameResponse = new GameResponse(
				"gameName",
				"football",
				10, 60, 25,
				"sport hall",
				"address",
				LocalDateTime.of(LocalDate.of(2022, 1, 1), LocalTime.of(10, 0)),
				"game description"
				);
		
		game = new Game(
				1L,
				"gameName",
				LocalDateTime.of(LocalDate.now(), LocalTime.now()),
				25, 60, 10, 
				"description",
				user,
				null,
				sportDiscipline,
				null);
	}
	
	@Test
	void save_ShouldReturn_Game() {
		
		when(venueRepository.findById(any(Long.class))).thenReturn(venue);
		when(userRepository.findByUsername(anyString())).thenReturn(user);
		when(sportDisciplineRepository.findByName(anyString())).thenReturn(sportDiscipline);
		when(gameRepository.save(any(Game.class))).thenReturn(game);
		when(modelMapper.map(gameRequest, Game.class)).thenReturn(game);
		when(modelMapper.map(game, GameResponse.class)).thenReturn(gameResponse);
		
		GameResponse savedGame = serviceUnderTest.save(gameRequest, principal);
		
		verify(gameRepository).save(any(Game.class));
		
		assertThat(savedGame).hasFieldOrPropertyWithValue("name", "gameName");
		assertThat(savedGame).hasFieldOrPropertyWithValue("sportDisciplineName", "football");
		assertThat(savedGame).hasFieldOrPropertyWithValue("amountOfPlayers", 10);
		assertThat(savedGame).hasFieldOrPropertyWithValue("duration", 60);
		assertThat(savedGame).hasFieldOrPropertyWithValue("price", 25);
		assertThat(savedGame).hasFieldOrPropertyWithValue("venueName", "sport hall");
		assertThat(savedGame).hasFieldOrPropertyWithValue("venueAddress", "address");
		assertThat(savedGame).hasFieldOrPropertyWithValue("date", LocalDateTime.of(LocalDate.of(2022, 1, 1), LocalTime.of(10, 0)));
		assertThat(savedGame).hasFieldOrPropertyWithValue("description", "game description");
	}
	
	@Test
	void save_ShouldThrow_SaveGameException_WithMessage_NoVenueWithSuchIdExists() {
		
		SaveGameException exception = assertThrows(SaveGameException.class, () -> {
			serviceUnderTest.save(gameRequest, principal);
		});
		
		assertTrue(exception.getMessage().contains("no venue with such id exists"));
	}
	
	@Test
	void save_ShouldThrow_SaveGameException_WithMessage_NoSuchUserExists() {
		
		when(venueRepository.findById(any(Long.class))).thenReturn(venue);
		
		SaveGameException exception = assertThrows(SaveGameException.class, () -> {
			serviceUnderTest.save(gameRequest, principal);
		});
		
		assertTrue(exception.getMessage().contains("no such user exists"));
	}
	
	@Test
	void save_ShouldThrow_SaveGameException_WithMessage_NoSuchSportDisciplineExists() {
		
		when(venueRepository.findById(any(Long.class))).thenReturn(venue);
		when(userRepository.findByUsername(anyString())).thenReturn(user);
		
		SaveGameException exception = assertThrows(SaveGameException.class, () -> {
			serviceUnderTest.save(gameRequest, principal);
		});
		
		assertTrue(exception.getMessage().contains("no such sport discipline exists"));
	}
}












