package com.jacekg.teamfinder.venue;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.jacekg.teamfinder.exceptions.SaveVenueException;
import com.jacekg.teamfinder.geocoding.GeocodingService;
import com.jacekg.teamfinder.geocoding.model.GeocodeLocation;
import com.jacekg.teamfinder.geocoding.model.GeocodeObject;

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
	
	@Transactional
	@Override
	public VenueResponse save(VenueRequest venueRequest) throws IOException {

		Venue venue = createVenueToSave(venueRequest);
		
		return modelMapper.map(venueRepository.save(venue), VenueResponse.class);
	}
	
	private Venue createVenueToSave(VenueRequest venueRequest) throws IOException {
		
		GeocodeObject geocodeObject = geocodingService.findLocationByAddress(venueRequest.getAddress());
		
		GeocodeLocation location = geocodeObject.getGeometry().getGeocodeLocation();
		
		Point venueCoordinates = 
				geometryFactory.createPoint(new Coordinate(location.getLongitude(), location.getLatitude()));
		
		VenueType venueType = venueTypeRepository.findByName(venueRequest.getVenueTypeName());
		
		if (venueType == null) {
			throw new SaveVenueException("no such venue type exists");
		}
		
		if (venueRepository.findByLocationAndVenueType(venueCoordinates, venueType) != null) {
			throw new SaveVenueException
				(venueRequest.getVenueTypeName() + " on this address is already registered");
		}
		
		venueRequest.setAddress(geocodeObject.getFormattedAddress());
		
		return mapVenue(venueRequest, venueCoordinates, venueType);
	}
	
	private Venue mapVenue(VenueRequest venueRequest, Point venueCoordinates, VenueType venueType) {
		
		Venue venue = modelMapper.map(venueRequest, Venue.class);
		venue.setLocation(venueCoordinates);
		venue.setVenueType(venueType);
		
		return venue;
	}

	@Override
	public List<VenueResponse> 
		findBySportDiscipline(String sportDiscipline, String address) throws IOException {
		
		GeocodeObject geocodeObject = geocodingService.findLocationByAddress(address);
		
		GeocodeLocation location = geocodeObject.getGeometry().getGeocodeLocation();
		
		Point venueCoordinates = 
				geometryFactory.createPoint(new Coordinate(location.getLongitude(), location.getLatitude()));
		
		return null;
	}

}
