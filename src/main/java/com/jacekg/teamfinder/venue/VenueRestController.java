package com.jacekg.teamfinder.venue;

import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.status;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/v1")
@AllArgsConstructor
public class VenueRestController {
	
	private VenueService venueService;
	
	@PostMapping("/venues")
	public ResponseEntity<VenueResponse> createVenue
		(@Valid @RequestBody VenueRequest venueRequest) throws IOException {
		
		return status(HttpStatus.CREATED).body(venueService.save(venueRequest));
	}
	
	@GetMapping("/venues/{sportDiscipline}/{address}")
	public ResponseEntity<List<VenueResponse>> 
		findBySportDyscipline(@PathVariable String sportDiscipline, @PathVariable String address) throws IOException {
		
		return status(HttpStatus.OK).body(venueService.findBySportDiscipline(sportDiscipline, address));
	}
}





