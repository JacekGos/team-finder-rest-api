package com.jacekg.teamfinder.geocode;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

//@JsonIgnoreProperties(ignoreUnknown = true)
public class Geocode {
	
	@JsonProperty("results")
	public ArrayList<Result> getResults() {
		return this.results;
	}

	public void setResults(ArrayList<Result> results) {
		this.results = results;
	}

	ArrayList<Result> results;

	@JsonProperty("status")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	String status;

	@Override
	public String toString() {
		return "Root [results=" + results + ", status=" + status + "]";
	}
}
