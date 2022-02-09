package com.jacekg.teamfinder.geocoding;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.ToString;

@ToString
public class Geocode {

	private ArrayList<Result> results;
	
	private String status;
	
	@JsonProperty("results")
	public ArrayList<Result> getResults() {
		return this.results;
	}

	public void setResults(ArrayList<Result> results) {
		this.results = results;
	}

	@JsonProperty("status")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
