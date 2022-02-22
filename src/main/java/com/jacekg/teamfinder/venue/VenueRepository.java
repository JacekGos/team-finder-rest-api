package com.jacekg.teamfinder.venue;

import java.util.List;

import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VenueRepository extends JpaRepository<Venue, Long> {
	
//	@Query(value="SELECT * from venue where ST_DistanceSphere(location, :location) < :distance",
//			nativeQuery = true)
//	List<Venue> findByVenueTypeWithinDistance(Point location, double distance);
	
	@Query(value="SELECT * from venue as v inner join venue_type as vt"
			+ " on v.venue_type_id = vt.id"
			+ " where vt.name in :venueTypeNames and ST_DistanceSphere(location, :location) < :distance",
			nativeQuery = true)
	List<Venue> findByVenueTypeWithinDistance(Point location, double distance, List<String> venueTypeNames);
	
	Venue findByLocationAndVenueType(Point location, VenueType venueType);
}
