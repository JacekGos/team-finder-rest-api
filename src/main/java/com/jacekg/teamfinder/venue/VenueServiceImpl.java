package com.jacekg.teamfinder.venue;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
	
	private final String SPORTS_HALL = "sports hall";
	
	private final String OUTDOOR_PITCH = "outdoor pitch";
	
	private final String BASKETBALL_PITCH = "basketball pitch";
	
	private final String TENNIS_COURT = "tennis court";
	
	private final String JOGGING_STARTING_POINT = "jogging starting point";
	
	@PostConstruct
	public void postConstruct() {
		
		modelMapper.addMappings(new PropertyMap<Venue, VenueResponse>() {
			protected void configure() {
				map().setVenueTypeName(source.getVenueType().getName());
				map().setBusyTerms(source.mapBusyTermsToDates());
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
	
	@Transactional
	@Override
	public List<VenueResponse> getAllBySportDysciplineAndAddress
		(String sportDisciplineName, String address) throws IOException {
		
		GeocodeObject geocodeObject = geocodingService.findLocationByAddress(address);
		
		GeocodeLocation location = geocodeObject.getGeometry().getGeocodeLocation();
		
		Point venueCoordinates = 
				geometryFactory.createPoint(new Coordinate(location.getLongitude(), location.getLatitude()));
		
		List<String> venueTypeNames = getVenueTypeNamesBySportDiscipline(sportDisciplineName);
		
		List<Venue> venues 
			= venueRepository.findByVenueTypeWithinDistance(venueCoordinates, 20000, venueTypeNames);
		
		return venues.stream()
				.map(venue -> modelMapper.map(venue, VenueResponse.class))
				.collect(Collectors.toList());
	}

	private List<String> getVenueTypeNamesBySportDiscipline(String sportDisciplineName) {
		
		List<String> venueTypeNames = new ArrayList<String>();
		
		switch (sportDisciplineName) {
		
		case "football":
			venueTypeNames.add(SPORTS_HALL);
			venueTypeNames.add(OUTDOOR_PITCH);
			break;
		
		case "volleyball":
			venueTypeNames.add(SPORTS_HALL);
			break;
			
		case "basketball":
			venueTypeNames.add(SPORTS_HALL);
			venueTypeNames.add(BASKETBALL_PITCH);
			break;

		case "tennis":
			venueTypeNames.add(TENNIS_COURT);
			break;

		case "jogging":
			venueTypeNames.add(JOGGING_STARTING_POINT);
			break;
			
		default:
			break;
		}
		
		return venueTypeNames;
	}

}
