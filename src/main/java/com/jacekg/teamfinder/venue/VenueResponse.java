package com.jacekg.teamfinder.venue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
	
	private Long id;
	
	private String name;
	
	private String address;
	
	private String venueTypeName;
	
	private List<LocalDateTime> busyTerms = new ArrayList<>();
}
