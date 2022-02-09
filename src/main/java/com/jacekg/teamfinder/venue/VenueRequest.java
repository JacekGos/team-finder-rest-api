package com.jacekg.teamfinder.venue;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
public class VenueRequest {
	
	@NotNull(message = "required")
	@Size(min = 1, max = 50, message = "too long name")
	private String name;
	
	@NotNull(message = "required")
	@Size(min = 1, max = 200, message = "too long address")
	private String address;
	
	@NotNull(message = "required")
	@Size(min = 1, max = 200, message = "too long venue type name")
	private String venueTypeName;
}
