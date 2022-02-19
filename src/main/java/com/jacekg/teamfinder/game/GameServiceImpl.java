package com.jacekg.teamfinder.game;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.jacekg.teamfinder.exceptions.SaveGameException;
import com.jacekg.teamfinder.venue.Term;
import com.jacekg.teamfinder.venue.Venue;
import com.jacekg.teamfinder.venue.VenueRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GameServiceImpl implements GameService {
	
	private GameRepository gameRepository;
	
	private VenueRepository venueRepository;
	
	private ModelMapper modelMapper;
	
	private static final Logger logger = LoggerFactory.getLogger(GameServiceImpl.class);
	
	@Override
	public GameResponse save(GameRequest gameRequest, Principal principal) {
		
		Game game = createGameToSave(gameRequest);
		
		return modelMapper.map(gameRepository.save(game), GameResponse.class);
	}

	private Game createGameToSave(GameRequest gameRequest) {
		
		Venue venue = venueRepository.getById(gameRequest.getVenueId());
		
		if (venue == null) {
			throw new SaveGameException("no venue with such id exists");
		}
		
		LocalDateTime gameDate 
			= LocalDateTime.of(gameRequest.getDate(), LocalTime.of(gameRequest.getHour(), 0));
		
		Term gameTerm = new Term(1L, gameDate);
		
		venue.setBusyTerms(Arrays.asList(gameTerm));
		
		return null;
	}
	
}
