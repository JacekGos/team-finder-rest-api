package com.jacekg.teamfinder.venue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VenueResponse {
	
	private String name;
	
	private String address;
	
	private String venueTypeName;
}
