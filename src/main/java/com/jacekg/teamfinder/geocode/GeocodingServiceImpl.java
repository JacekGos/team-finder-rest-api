package com.jacekg.teamfinder.geocode;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class GeocodingServiceImpl implements GeocodingService {
	
private WebClient.Builder webClientBuilder;
	
	@Value("${geocoding.api.key}")
	private final String GEOCODING_API_KEY;
	
	@Value("${geocoding.api.url}")
	private final String GEOCODING_API_URL;
	
	@Override
	public Location findLocationByAddress(String address) {
		
		String uriString = UriComponentsBuilder
				.fromUriString(GEOCODING_API_URL)
				.queryParam("address", address)
				.queryParam("key", GEOCODING_API_KEY)
				.build()
				.encode()
				.toUriString();
		
		Location location = webClientBuilder.build()
			.get()
			.uri(uriString)
			.retrieve()
			.bodyToMono(Location.class)
			.block();
		
		return location;
	}

}
