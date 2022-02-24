package com.jacekg.teamfinder.game;

import java.security.Principal;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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
import com.jacekg.teamfinder.venue.TermRepository;
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
	
	private TermRepository termRepository;
	
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
				map().setLongitude(source.getVenue().getLocation().getX());
				map().setLattitude(source.getVenue().getLocation().getY());
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
				.orElseThrow(() -> {throw new SaveGameException("no such user exists");});
		
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
		
		venue = updateVenueToSave(venue, gameDate, gameRequest.getDuration());
		
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
	
	private Venue updateVenueToSave(Venue venue, LocalDateTime gameDate, int duration) {
		
		List<Term> gameTerms = new ArrayList<>();
		gameTerms.add(new Term(null, gameDate));
		
		if (duration == 120) {
			gameTerms.add(new Term(null, gameDate.plusHours(1L)));
		}
		
		List<Term> busyTerms = termRepository.findByVenueId(venue.getId());
		
		for (Term gameTerm : gameTerms) {
			
			if (busyTerms.stream()
					.filter(term -> gameTerm.getBusyTerm().equals(term.getBusyTerm()))
					.findAny()
					.orElse(null) != null) {

				throw new SaveGameException("venue is not available on that date");
			}
		}
		
		venue.addTerms(gameTerms);
		
		venueRepository.save(venue);
		
		return venue;
	}
	
	@Transactional
	@Override
	public List<GameResponse> getAll() {
		
		List<Game> games = gameRepository.findAll();
		logger.info("location X: " + games.get(3).getVenue().getLocation().getX());
		logger.info("location Y: " + games.get(3).getVenue().getLocation().getY());
		
		return gameRepository.findAll().stream()
				.map(game -> modelMapper.map(game, GameResponse.class))
				.collect(Collectors.toList());
	}
}





