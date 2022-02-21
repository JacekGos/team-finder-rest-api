package com.jacekg.teamfinder.game;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

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
	
	private Game game;
	
	private Optional<Venue> venue;
	
	private Principal principal;
	
	private SportDiscipline sportDiscipline;
	
	private User user;
	
	@BeforeEach
	void setUp() {
		
		gameRequest = new GameRequest(
				"gameName",
				"sportDisciplineName",
				10, 60, 25, 1,
				LocalDate.now(),
				10,
				"game description"
				);
		
		
		List<Term> busyTerms = new ArrayList<>();
//		busyTerms.add(new Term(1L, LocalDateTime.of(LocalDate.now(), LocalTime.now())));
		
		venue = Optional.ofNullable(new Venue(1L, "sport venue", "address 1", null, null, new ArrayList<Term>()));
		
		principal = new Principal() {
			
			@Override
			public String getName() {
				// TODO Auto-generated method stub
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

		game = new Game(
				1L,
				"gameName",
				LocalDateTime.of(LocalDate.now(), LocalTime.now()),
				10, 60, 10, 
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
		when(modelMapper.map(gameRequest, Game.class)).thenReturn(game);

		GameResponse game = serviceUnderTest.save(gameRequest, principal);
	}

}












