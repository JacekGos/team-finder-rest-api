package com.jacekg.teamfinder.venue;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.GeometryFactory;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.jacekg.teamfinder.geocoding.GeocodingService;
import com.jacekg.teamfinder.geocoding.model.AddressComponent;
import com.jacekg.teamfinder.geocoding.model.GeocodeGeometry;
import com.jacekg.teamfinder.geocoding.model.GeocodeObject;

@ExtendWith(MockitoExtension.class)
class VenueServiceImplTest {
	
	@InjectMocks
	VenueServiceImpl serviceUnderTest;
	
	@Mock
	private GeocodingService geocodingService;
	
	@Mock
	private VenueRepository venueRepository;
	
	@Mock
	private VenueTypeRepository venueTypeRepository;
	
	@Mock
	private ModelMapper modelMapper;
	
	@Mock
	private GeometryFactory geometryFactory;
	
	private VenueRequest venueRequest;
	
	@BeforeEach
	void setUp() {
		
		venueRequest = new VenueRequest("sport venue", "address 1", "sports hall");
		
		GeocodeObject geocodeObject = new GeocodeObject();
		
		List<AddressComponent> addressComponents = new ArrayList<>();
		
		GeocodeGeometry geometry = new GeocodeGeometry();
	}
	
	@Test
	void save_ShouldReturn_Venue() throws IOException {
		
		when(geocodingService.findLocationByAddress(Mockito.anyString())).thenReturn(null);
		
	}

}







