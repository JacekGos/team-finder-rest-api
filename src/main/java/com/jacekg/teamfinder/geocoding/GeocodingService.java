package com.jacekg.teamfinder.geocoding;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

import com.jacekg.teamfinder.geocoding.model.GeocodeLocation;
import com.jacekg.teamfinder.geocoding.model.GeocodeObject;
import com.jacekg.teamfinder.geocoding.model.GeocodeResult;

public interface GeocodingService {
				
	public GeocodeObject findLocationByAddress(String address) throws IOException;
}
