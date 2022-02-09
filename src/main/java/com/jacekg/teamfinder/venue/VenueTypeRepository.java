package com.jacekg.teamfinder.venue;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueTypeRepository extends JpaRepository<VenueType, Long> {
	
	VenueType findByName(String name);
}
