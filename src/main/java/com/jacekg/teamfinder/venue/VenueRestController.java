package com.jacekg.teamfinder.venue;

import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.status;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/v1")
@AllArgsConstructor
public class VenueRestController {
	
	private VenueService venueService;
	
	private static final Logger logger = LoggerFactory.getLogger(VenueRestController.class);
	
	@PostMapping("/venues")
	public ResponseEntity<VenueResponse> createVenue
		(@Valid @RequestBody VenueRequest venueRequest) throws IOException {
		
		return status(HttpStatus.CREATED).body(venueService.save(venueRequest));
	}
	
	@GetMapping("/venues")
	public ResponseEntity<List<VenueResponse>> findBySportDysciplineAndAddress
			(@RequestParam String sportDiscipline, @RequestParam String address) throws IOException {
		
		return status(HttpStatus.OK)
				.body(venueService.getAllBySportDysciplineAndAddress(sportDiscipline, address));
	}
}





