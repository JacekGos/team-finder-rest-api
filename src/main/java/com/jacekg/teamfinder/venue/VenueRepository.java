package com.jacekg.teamfinder.venue;

import java.util.List;

import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VenueRepository extends JpaRepository<Venue, Long> {
	
	@Query(value="SELECT * from venue where ST_DistanceSphere(location, :location) < :distance", nativeQuery = true)
	List<Venue> findNearWithinDistance(Point location, double distance);
	
//	@Query("FROM MonthlyBudget mb WHERE user_id=:userId AND date=:date")
//	MonthlyBudget findByUserIdAndDate(@Param("userId") Long userId, @Param("date") LocalDate date);
	
//	@Query("FROM Venue v WHERE location=:location AND venue_type_id=:")
	Venue findByLocationAndVenueType(Point location, VenueType venueType);
}
