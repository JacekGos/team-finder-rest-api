package com.jacekg.teamfinder.venue;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class VenueServiceImpl implements VenueService {
	
	private VenueRepository venueRepository;
	
	@Override
	public VenueResponse save(VenueRequest venueRequest) {
		return venueRepository.save(venue);
	}

}
