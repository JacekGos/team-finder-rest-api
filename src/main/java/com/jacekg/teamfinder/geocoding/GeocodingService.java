package com.jacekg.teamfinder.geocoding;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

import com.jacekg.teamfinder.geocoding.model.GeocodeLocation;

public interface GeocodingService {
				
	public GeocodeLocation findLocationByAddress(String address) throws IOException;
}
