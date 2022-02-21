package com.jacekg.teamfinder.game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.jacekg.teamfinder.geocoding.model.GeocodeGeometry;
import com.jacekg.teamfinder.geocoding.model.GeocodeLocation;
import com.jacekg.teamfinder.geocoding.model.GeocodeObject;
import com.jacekg.teamfinder.sport_discipline.SportDisciplineRepository;
import com.jacekg.teamfinder.user.UserRepository;
import com.jacekg.teamfinder.venue.TermRepository;
import com.jacekg.teamfinder.venue.Venue;
import com.jacekg.teamfinder.venue.VenueRepository;
import com.jacekg.teamfinder.venue.VenueRequest;
import com.jacekg.teamfinder.venue.VenueResponse;
import com.jacekg.teamfinder.venue.VenueType;

@ExtendWith(MockitoExtension.class)
class GameServiceImplTest {
	
	@InjectMocks
	GameServiceImpl gameServiceImpl;
	
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
	
	@BeforeEach
	void setUp() {
		
	}
	
	@Test
	void save_ShouldReturn_Game() {
		fail("Not yet implemented");
	}

}
