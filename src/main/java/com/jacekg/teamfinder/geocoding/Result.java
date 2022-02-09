package com.jacekg.teamfinder.geocoding;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Result {

	@JsonProperty("geometry")
	public Geometry getGeometry() {
		return this.geometry;
	}

	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}

	Geometry geometry;

	@Override
	public String toString() {
		return "Result [geometry=" + geometry + "]";
	}
}