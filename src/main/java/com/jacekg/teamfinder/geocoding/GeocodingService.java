package com.jacekg.teamfinder.geocoding;

import java.util.Optional;

public interface GeocodingService {
				
	public Optional<Location> findLocationByAddress(String address);
}
