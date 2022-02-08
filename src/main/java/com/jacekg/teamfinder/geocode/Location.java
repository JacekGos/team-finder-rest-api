package com.jacekg.teamfinder.geocode;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Location {
	
	private double lattitude;
	
	private double longitude;
	
	@JsonProperty("lat")
	public double getLattitude() {
		return this.lattitude;
	}

	public void setLattitude(double lattitude) {
		this.lattitude = lattitude;
	}

	@JsonProperty("lng")
	public double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return "Location [latitude=" + lattitude + ", longitude=" + longitude + "]";
	}
}
