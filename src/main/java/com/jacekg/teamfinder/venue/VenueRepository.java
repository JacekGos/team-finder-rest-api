package com.jacekg.teamfinder.venue;

import java.util.List;

import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VenueRepository extends JpaRepository<Venue, Long> {
	
	@Query(value="SELECT * from venue where ST_DistanceSphere(location, :location) < :distanceM", nativeQuery = true)
	List<Venue> findNearWithinDistance(Point location, double distanceM);
}
