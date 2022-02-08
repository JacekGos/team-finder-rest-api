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
import org.springframework.stereotype.Service;

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
	
	@Override
	public VenueResponse save(VenueRequest venueRequest) {
		
		//check if venue on this address exists
		//throw exception if exists
		
		//Create  Venue object
//		Venue venue = mapVenue(venueRequest);

//		Point venueCoordinates = geometryFactory.createPoint(new Coordinate(50.8660773, 20.6285676));
		Point venueCoordinates = geometryFactory.createPoint(new Coordinate(51.41955305918177, 21.143201932437));
		
		SportDiscipline sportDiscipline = new SportDiscipline(1L, "football", null);
		
		Venue venue = new Venue();
		venue.setName("Kielce stadium");
		venue.setAddress("Kielce, Warszawska 1");
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
		
		Point venueCoordinates = geometryFactory.createPoint(new Coordinate(50.8660773, 20.6285676));
//		return venueRepository.findNearWithinDistance(venueCoordinates, 80000);
		
//		List<Venue> venues = venueRepository.findAll();
		List<Venue> venues = venueRepository.findNearWithinDistance(venueCoordinates, 100000);
		logger.info("venues: " + venues);
		
		return null;
		
	}

}
