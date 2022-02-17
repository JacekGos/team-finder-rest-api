package com.jacekg.teamfinder.geocoding.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AddressComponent {
	
	@JsonProperty("long_name")
	String longName;
	
	@JsonProperty("short_name")
	String shortName;
	
	List<String> types;
}