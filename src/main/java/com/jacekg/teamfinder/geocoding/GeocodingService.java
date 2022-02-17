package com.jacekg.teamfinder.geocoding;

import java.util.Optional;

public interface GeocodingService {
				
	public Location findLocationByAddress(String address);
}
