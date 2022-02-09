package com.jacekg.teamfinder.geocoding;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.ToString;

@ToString
public class Geometry {
	
	private Location location;

	@JsonProperty("location")
	public Location getLocation() {
		return this.location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
}