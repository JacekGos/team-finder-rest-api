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
import com.jacekg.teamfinder.sport_discipline.SportDiscipline;
import com.jacekg.teamfinder.user.User;
import com.jacekg.teamfinder.user.UserRepository;
import com.jacekg.teamfinder.venue.Term;
import com.jacekg.teamfinder.venue.Venue;
import com.jacekg.teamfinder.venue.VenueRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GameServiceImpl implements GameService {
	
	private GameRepository gameRepository;
	
	private VenueRepository venueRepository;
	
	private UserRepository userRepository;
	
	private ModelMapper modelMapper;
	
	private static final Logger logger = LoggerFactory.getLogger(GameServiceImpl.class);
	
	@Override
	public GameResponse save(GameRequest gameRequest, Principal principal) {
		
		Game game = createGameToSave(gameRequest);
		
		return modelMapper.map(gameRepository.save(game), GameResponse.class);
	}

	private Game createGameToSave(GameRequest gameRequest, Principal principal) {
		
		Venue venue = venueRepository.getById(gameRequest.getVenueId());
		
		if (venue == null) {
			throw new SaveGameException("no venue with such id exists");
		}
		
		User creator = userRepository.findByUsername(principal.getName());
		
		if (creator == null) {
			throw new SaveGameException("no user with such id exists");
		}
		
		LocalDateTime gameDate 
			= LocalDateTime.of(gameRequest.getDate(), LocalTime.of(gameRequest.getHour(), 0));
		
		Term gameTerm = new Term(1L, gameDate);
		
		venue.addTerm(gameTerm);
		
		Game game = mapGame(gameRequest);
		
		
		return null;
	}
	
	private Game mapGame(GameRequest gameRequest, User creator, SportDiscipline sportDiscipline, Venue venue) {
		
		Game game = modelMapper.map(gameRequest, Game.class);
		
		return game;
	}
}





