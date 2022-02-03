package com.jacekg.teamfinder.venue;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VenueServiceImpl implements VenueService {
	
	private VenueRepository venueRepository;
	
	private ModelMapper modelMapper;
	
	@Override
	public VenueResponse save(VenueRequest venueRequest) {
		
		//check if venue on this address exists
		//throw exception if exists
		
		//Create  Venue object
		Venue venue = mapVenue(venueRequest);
		
		//Get sportDiscipline by name
		
		//set discipline to venue
		
		//save venue
		
//		return venueRepository.save(venue);
		return null;
	}

	private Venue mapVenue(VenueRequest venueRequest) {
		
		Venue venue = modelMapper.map(venueRequest, Venue.class);
		
		System.out.println("venue: " + venue);
		System.out.println("sport disc: " + venue.getSportDiscipline());
		
		return null;
	}

}
