package com.jacekg.teamfinder.geocoding;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.ToString;

@ToString
public class Result {

	private Geometry geometry;

	@JsonProperty("geometry")
	public Geometry getGeometry() {
		return this.geometry;
	}

	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}
}