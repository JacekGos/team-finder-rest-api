package com.jacekg.teamfinder.game;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import com.jacekg.teamfinder.venue.VenueService;

@ExtendWith(MockitoExtension.class)
class GameSpecificationTest {
	
	@InjectMocks
	GameSpecification gameSpecification;
	
	@Mock
	private VenueService venueService;
	
	@Test
	@Disabled
	void getGames_ShouldReturn_Specification() throws IOException {
		
		Map<String, String> filterParams = new HashMap<>();
		
		List<Long> venuesId = new ArrayList<>();
		venuesId.add(1L);
		venuesId.add(2L);
		
		when(venueService.getAllIdsBySportDysciplineAndAddress(anyString(), anyString(), any(Double.class)))
			.thenReturn(venuesId);
		
		Specification<Game> specifications = gameSpecification.getGames(filterParams);
	}

}
