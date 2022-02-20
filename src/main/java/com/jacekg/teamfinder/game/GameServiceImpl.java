package com.jacekg.teamfinder.game;

import java.security.Principal;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Supplier;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
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
import com.jacekg.teamfinder.venue.VenueResponse;

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
	
	@PostConstruct
	public void postConstruct() {
		
		modelMapper.addMappings(new PropertyMap<Game, GameResponse>() {
			protected void configure() {
				map().setSportDisciplineName(source.getSportDiscipline().getName());
				map().setVenueName(source.getVenue().getName());
				map().setVenueAddress(source.getVenue().getAddress());
			}
		});
		
		modelMapper.addMappings(new PropertyMap<GameRequest, Game>() {
			protected void configure() {
				skip(destination.getId());
			}
		});
	}
	
	@Transactional
	@Override
	public GameResponse save(GameRequest gameRequest, Principal principal) {
		
		Game game = createGameToSave(gameRequest, principal);
		
		return modelMapper.map(gameRepository.save(game), GameResponse.class);
	}
	
	private Game createGameToSave(GameRequest gameRequest, Principal principal) {
		
		Optional<Venue> foundVenue = venueRepository.findById(gameRequest.getVenueId());
		Venue venue = Optional.ofNullable(foundVenue)
			.get()
			.orElseThrow(() -> {throw new SaveGameException("no venue with such id exists");});
		
		Optional<User> foundUser = Optional.ofNullable(userRepository.findByUsername(principal.getName()));
		User creator = Optional.ofNullable(foundUser)
				.get()
				.orElseThrow(() -> {throw new SaveGameException("no user with such id exists");});
		
		Optional<SportDiscipline> foundSportDiscipline 
			= Optional.ofNullable(sportDisciplineRepository.findByName(gameRequest.getSportDisciplineName()));
		SportDiscipline sportDiscipline = Optional.ofNullable(foundSportDiscipline)
				.get()
				.orElseThrow(() -> {throw new SaveGameException("no such sport discipline exists");});
		
		LocalDateTime gameDate;
		
		try {
			gameDate 
				= LocalDateTime.of(gameRequest.getDate(), LocalTime.of(gameRequest.getHour(), 0));
		} catch (DateTimeException exc) {
			throw new SaveGameException("incorrect date or time");
		}
		
		venue = updateVenueToSave(venue, gameDate);
		
		Game game = mapGame(gameRequest);
		game.addCreator(creator);
		game.setVenue(venue);
		game.setDate(gameDate);
		game.addSportDiscipline(sportDiscipline);
		
		return game;
	}
	
	private Game mapGame(GameRequest gameRequest) {
		
		Game game = modelMapper.map(gameRequest, Game.class);
		
		return game;
	}
	
	private Venue updateVenueToSave(Venue venue, LocalDateTime gameDate) {
		
		Term gameTerm = new Term(1L, gameDate);
		
		venue.addTerm(gameTerm);
		
		venueRepository.save(venue);
		
		return venue;
	}
}





