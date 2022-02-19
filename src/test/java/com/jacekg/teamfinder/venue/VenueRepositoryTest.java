package com.jacekg.teamfinder.venue;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DataJpaTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class VenueRepositoryTest {
	
	@Autowired
	private VenueRepository venueRepository;
	
	@Autowired
	private GeometryFactory geometryFactory;
	
	private Venue venue;
	
	private VenueType venueType;
	
	@BeforeEach
	void setUp() throws Exception {
		
		venueType = new VenueType(1L, "sports hall");
		
		venue = new Venue(
				1L,
				"venue",
				"address",
				geometryFactory.createPoint(new Coordinate(1.0, 1.0)),
				venueType,
				null);
	}
	
	@Disabled
	@Test
	void findNearWithinDistance_ShouldReturn_Venue() {
		
		Point centerPoint = geometryFactory.createPoint(new Coordinate(1.0, 1.0));
		
		venueRepository.save(venue);
		
		List<Venue> foundVenues = 
				venueRepository.findNearWithinDistance(centerPoint, 10000);
		
		assertThat(foundVenues).isNotEmpty();
	}
	
	@Test
	void testFindByLocationAndVenueType() {
		
		Point centerPoint = geometryFactory.createPoint(new Coordinate(1.0, 1.0));
		
		venueRepository.save(venue);
		
		Venue foundVenue = venueRepository.findByLocationAndVenueType(centerPoint, venueType);
		
		assertThat(foundVenue.getLocation()).isEqualTo(centerPoint);
	}

}
