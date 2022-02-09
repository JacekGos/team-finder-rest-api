package com.jacekg.teamfinder.geocoding;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Geometry {
	
	@JsonProperty("location")
	public Location getLocation() {
		return this.location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	Location location;

	@Override
	public String toString() {
		return "Geometry [location=" + location + "]";
	}
}