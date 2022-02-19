package com.jacekg.teamfinder.game;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.jacekg.teamfinder.exceptions.SaveGameException;
import com.jacekg.teamfinder.sport_discipline.SportDiscipline;
import com.jacekg.teamfinder.sport_discipline.SportDisciplineRepository;
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
	
	private SportDisciplineRepository sportDisciplineRepository;
	
	private ModelMapper modelMapper;
	
	private static final Logger logger = LoggerFactory.getLogger(GameServiceImpl.class);
	
	@Transactional
	@Override
	public GameResponse save(GameRequest gameRequest, Principal principal) {
		
		Game game = createGameToSave(gameRequest, principal);
		logger.info("after createGameToSave");
		return modelMapper.map(gameRepository.save(game), GameResponse.class);
	}
	
	private Game createGameToSave(GameRequest gameRequest, Principal principal) {
		
		logger.info("before found venue");
		Optional<Venue> foundVenue = venueRepository.findById(gameRequest.getVenueId());
		Venue venue = foundVenue.get();
		logger.info("found venue");
		
		if (venue == null) {
			throw new SaveGameException("no venue with such id exists");
		}
		
		logger.info("before found user");
		User creator = userRepository.findByUsername(principal.getName());
		logger.info("found user");
		
		if (creator == null) {
			throw new SaveGameException("no user with such id exists");
		}
		
//		SportDiscipline sportDiscipline = sportDisciplineRepository.getByName();
		
		LocalDateTime gameDate 
			= LocalDateTime.of(gameRequest.getDate(), LocalTime.of(gameRequest.getHour(), 0));
		logger.info("gameDate: " + gameDate);
		
		Term gameTerm = new Term(1L, gameDate);
		
		logger.info("before addTerm");
		venue.addTerm(gameTerm);
		logger.info("after addTerm");
		
		logger.info("before mapGame");
		Game game = mapGame(gameRequest, creator, venue);
		logger.info("after mapGame");
		
		return game;
	}
	
	private Game mapGame(GameRequest gameRequest, User creator, Venue venue) {
		
		logger.info("before map");
		Game game = modelMapper.map(gameRequest, Game.class);
		logger.info("after map");
		logger.info("before addCreator");
		game.addCreator(creator);
		logger.info("after addCreator");
		logger.info("before setVenue");
		game.setVenue(venue);
		logger.info("after setVenue");
		
		return game;
	}
}





