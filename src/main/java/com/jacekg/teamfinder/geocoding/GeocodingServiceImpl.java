package com.jacekg.teamfinder.geocoding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import org.springframework.web.util.UriComponentsBuilder;

import com.jacekg.teamfinder.venue.VenueServiceImpl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

//@AllArgsConstructor
//@RequiredArgsConstructor
@Service
public class GeocodingServiceImpl implements GeocodingService {
	
	private static final Logger logger = LoggerFactory.getLogger(GeocodingServiceImpl.class);

	private final WebClient.Builder webClientBuilder;
	
	private String GEOCODING_API_KEY;
	
	private String GEOCODING_API_URL;
	
	@Autowired
	public GeocodingServiceImpl(
			Builder webClientBuilder,
			@Value("${geocoding.api.key}") String gEOCODING_API_KEY, 
			@Value("${geocoding.api.url}") String gEOCODING_API_URL) {
	
		this.webClientBuilder = webClientBuilder;
		GEOCODING_API_KEY = gEOCODING_API_KEY;
		GEOCODING_API_URL = gEOCODING_API_URL;
	}

	@Override
	public Location findLocationByAddress(String address) {

		String uriString = UriComponentsBuilder
				.fromUriString(GEOCODING_API_URL)
				.queryParam("address", address)
				.queryParam("key", GEOCODING_API_KEY)
				.build()
				.encode()
				.toUriString();
		
		Geocode geocode = webClientBuilder.build()
			.get()
			.uri(uriString)
			.retrieve()
			.bodyToMono(Geocode.class)
			.block();
		
		logger.info("uri: " + uriString);
		logger.info("geocode result: " + geocode.getResults().get(0).getGeometry().getLocation());
		
		return geocode.getStatus().equals("OK") ? geocode.getResults().get(0).getGeometry().getLocation() : null;
	}

}
