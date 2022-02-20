package com.jacekg.teamfinder.venue;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TermRepository extends JpaRepository<Term, Long> {
	
	@Query("FROM Term WHERE venue_id=:venueId")
	List<Term> findByVenueId(@Param("venueId") Long venueId);
}
