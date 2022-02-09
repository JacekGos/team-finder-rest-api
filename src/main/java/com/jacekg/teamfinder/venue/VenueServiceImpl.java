package com.jacekg.teamfinder.venue;

import java.util.List;

import javax.annotation.PostConstruct;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.jacekg.teamfinder.geocoding.GeocodingService;
import com.jacekg.teamfinder.geocoding.Location;
import com.jacekg.teamfinder.sport_discipline.SportDiscipline;
import com.jacekg.teamfinder.user.User;
import com.jacekg.teamfinder.user.UserResponse;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VenueServiceImpl implements VenueService {
	
	private GeocodingService geocodingService;
	
	private VenueRepository venueRepository;
	
	private VenueTypeRepository venueTypeRepository;
	
	private ModelMapper modelMapper;
	
	private GeometryFactory geometryFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(VenueServiceImpl.class);
	
	@PostConstruct
	public void postConstruct() {
		
		modelMapper.addMappings(new PropertyMap<Venue, VenueResponse>() {
			protected void configure() {
				map().setVenueTypeName(source.getVenueType().getName());
			}
		});
	}
	
	@Override
	public VenueResponse save(VenueRequest venueRequest) {
		
		Location location = geocodingService.findLocationByAddress(venueRequest.getAddress());
		logger.info("Location: " + location);
		
		Point venueCoordinates = 
				geometryFactory.createPoint(new Coordinate(location.getLng(), location.getLat()));
		
		VenueType venueType = venueTypeRepository.findByName(venueRequest.getVenueTypeName());
		logger.info("venue type: " + venueType);
		
		Venue venue = mapVenue(venueRequest, venueCoordinates, venueType);
		logger.info("mapped venue: " + venue);
		
		VenueResponse venueResponse = modelMapper.map(venueRepository.save(venue), VenueResponse.class);
		logger.info("venueResposne: " + venueResponse);
		
		return modelMapper.map(venueRepository.save(venue), VenueResponse.class);
	}
	
	private Venue mapVenue(VenueRequest venueRequest, Point venueCoordinates, VenueType venueType) {
		
		Venue venue = modelMapper.map(venueRequest, Venue.class);
		venue.setLocation(venueCoordinates);
		venue.setVenueType(venueType);
		
		return venue;
	}

}
