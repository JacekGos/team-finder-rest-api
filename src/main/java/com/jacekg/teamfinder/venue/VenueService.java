package com.jacekg.teamfinder.venue;

import java.io.IOException;
import java.util.List;

public interface VenueService {
	
	public VenueResponse save(VenueRequest venueRequest) throws IOException;

	public List<VenueResponse> getAllBySportDysciplineAndAddress(String sportDisciplineName, String address) throws IOException;
}
