package com.jacekg.teamfinder.game;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
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

import com.jacekg.teamfinder.sport_discipline.SportDisciplineRepository;
import com.jacekg.teamfinder.user.UserRepository;
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
	
	private Optional<Venue> venue;
	
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
		
		venue = Optional.ofNullable(new Venue(1L, "sport venue", "address 1", null, null, null));
	}
	
	@Test
	void save_ShouldReturn_Game() {
		
		when(venueRepository.findById(any(Long.class))).thenReturn(venue);
		when(userRepository.findByUsername(anyString())).thenReturn(null);
		when(sportDisciplineRepository.findByName(anyString())).thenReturn(null);

		GameResponse game = serviceUnderTest.save(gameRequest, null);
	}

}












