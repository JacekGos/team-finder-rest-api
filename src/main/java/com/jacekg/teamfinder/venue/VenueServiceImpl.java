package com.jacekg.teamfinder.venue;

import java.util.List;

import javax.transaction.Transactional;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.jacekg.teamfinder.game.GameServiceImpl;
import com.jacekg.teamfinder.sport_discipline.SportDiscipline;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VenueServiceImpl implements VenueService {
	
	private VenueRepository venueRepository;
	
	private ModelMapper modelMapper;
	
	private GeometryFactory geometryFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(VenueServiceImpl.class);
	
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private WebClient.Builder webClientBuilder;
	
	@Value("${geocoding.api.key}")
	private final String API_KEY;
	
	@Value("${geocoding.api.url}")
	private final String GEOCODING_API_URL;
	
	@Override
	public VenueResponse save(VenueRequest venueRequest) {
		
		//check if venue on this address exists
		//throw exception if exists
		
		//Create  Venue object
//		Venue venue = mapVenue(venueRequest);

//		Point venueCoordinates = geometryFactory.createPoint(new Coordinate(20.88387349946455, 52.19534977700785));
		Point venueCoordinates = geometryFactory.createPoint(new Coordinate(20.980332604713254, 52.232282077952625));
//		Point venueCoordinates = geometryFactory.createPoint(new Coordinate(52.22629611891915, 20.93406243991821));
		
		SportDiscipline sportDiscipline = new SportDiscipline(1L, "football", null);
		
		Venue venue = new Venue();
		venue.setName("ursus stadium");
		venue.setAddress("Warszawa, Warszawska 1");
//		venue.setSportDiscipline(sportDiscipline);
		venue.setLocation(venueCoordinates);
		
		//Get sportDiscipline by name
		
		//set discipline to venue
		
		//save venue
		
		Venue savedVenue = venueRepository.save(venue);
		logger.info("venue: " + savedVenue);
		
		return new VenueResponse();
//		return venueRepository.save(venue);
//		return null;
	}
	
	//TODO remove- test purposes
	@Override
//	@Transactional
	public List<Venue> findVenues() {
		
		Point venueCoordinates = geometryFactory.createPoint(new Coordinate(20.980332604713254, 52.232282077952625));
		
//		List<Venue> venues = venueRepository.findAll();
		List<Venue> venues = venueRepository.findNearWithinDistance(venueCoordinates, 7750);
		logger.info("venues: " + venues);
		logger.info("venues number: " + venues.size());
		
		return null;
		
	}

}
